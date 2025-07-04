package ever.login.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.jwt.JwtUtil;
import ever.support.security.module.sha.SHACipher;

@Service
public class LoginServiceImpl implements LoginService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	CmmnService cmmnService;

	@Transactional
	@Override
	public ResponseEntity<Object> login(HashMap<String, Object> hMap, HttpServletRequest request) throws Exception {

		HashMap<String, Object> reqMap = new HashMap<>();

		String loginIdSha256 = String.valueOf(hMap.get("loginId"));
		String pwdSha256 = String.valueOf(hMap.get("pwd"));

		reqMap.put("loginId", hMap.get("loginId"));
		reqMap.put("encLoginId", (new SHACipher()).encryptSHA256(loginIdSha256));
		reqMap.put("pwd", (new SHACipher()).encryptSHA256(pwdSha256));
		reqMap.put("companyYn", hMap.get("companyYn"));
		reqMap.put("userId", hMap.get("userId"));
		reqMap.put("refreshToken", hMap.get("refreshToken"));
		// hMap.remove("refreshToken");

		logger.debug("UserInfo==>{}", reqMap.toString());

		// TODO: AS-IS상에 접속 URL별 분기처리 로직 코드화 하여 처리하는 로직 추가


		List<Object> loginInfos = new ArrayList<>();
		
		if ("Y".equals(reqMap.get("companyYn"))) { // 제약회사 로그인
			loginInfos = cmmnService.selectList("login.selectLoginForMaker", reqMap);
			HashMap<String, Object> loginInfo = (HashMap<String, Object>) loginInfos.get(0);
		
			//제약사 로그인 사용여부
			String useYn = (String) loginInfo.get("SUPPLY_USE_YN");
			if (!ObjectUtils.isEmpty(loginInfos)) {				
				if (useYn.equals("Y")) {
					String token = jwtUtil.createToken(String.valueOf(reqMap.get("loginId"))); // 사용자 정보로 토큰 생성
					String refreshToken = jwtUtil.createRefreshToken(String.valueOf(reqMap.get("loginId")));

					HashMap<String, Object> pMap = new HashMap<>();

					pMap.put("userData", loginInfo);
					pMap.put("accessToken", token);
					pMap.put("refreshToken", refreshToken);

					/* 로그인 유저의 refreshToken 저장 */
					reqMap.put("refreshToken", refreshToken);

					// 제약회사 로그인 토큰
					cmmnService.update("login.updateRefreshTokenForMaker", reqMap);
					
					/* 로그인 성공시 히스토리 테이블 저장 */
					reqMap.put("ipAddr", request.getHeader("Proxy-Client-IP"));
					reqMap.put("accessToken", token);
					reqMap.put("userData", pMap.get("userData"));
					cmmnService.insert("login.insertLoginHistory", reqMap);

					return new ResponseEntity<>(pMap, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("제약사 사용권한", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
			}
		
		} else if ("N".equals(reqMap.get("companyYn"))) { // 일반 로그인
			loginInfos = cmmnService.selectList("login.selectLogin", reqMap);				
			if (!ObjectUtils.isEmpty(loginInfos)) {
				HashMap<String, Object> loginInfo = (HashMap<String, Object>) loginInfos.get(0);
				String useYn = (String) loginInfo.get("USE_YN");
				
				if (useYn.equals("Y")) {
					String token = jwtUtil.createToken(String.valueOf(reqMap.get("loginId"))); // 사용자 정보로 토큰 생성
					String refreshToken = jwtUtil.createRefreshToken(String.valueOf(reqMap.get("loginId")));

					HashMap<String, Object> pMap = new HashMap<>();

					pMap.put("userData", loginInfo);
					pMap.put("accessToken", token);
					pMap.put("refreshToken", refreshToken);

					/* 로그인 유저의 refreshToken 저장 */
					reqMap.put("refreshToken", refreshToken);

					// 일반 로그인 토큰
					cmmnService.update("login.updateRefreshTokenForUser", reqMap);
					
					/* 로그인 성공시 히스토리 테이블 저장 */
					reqMap.put("ipAddr", request.getHeader("Proxy-Client-IP"));
					reqMap.put("accessToken", token);
					reqMap.put("userData", pMap.get("userData"));
					cmmnService.insert("login.insertLoginHistory", reqMap);

					return new ResponseEntity<>(pMap, HttpStatus.OK);
					
				} else {
					return new ResponseEntity<>("미사용처리", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
			}
						
		} else {
			return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
		}
		
		
	}

	@Transactional
	@Override
	public ResponseEntity<Object> loginCust(HashMap<String, Object> hMap, HttpServletRequest request) throws Exception {
		HashMap<String, Object> reqMap = new HashMap<>();

		String loginIdSha256 = String.valueOf(hMap.get("loginId"));
		String pwdSha256 = String.valueOf(hMap.get("pwd"));

		reqMap.put("loginId", hMap.get("loginId"));
		reqMap.put("encLoginId", (new SHACipher()).encryptSHA256(loginIdSha256));
		reqMap.put("pwd", (new SHACipher()).encryptSHA256(pwdSha256));
		reqMap.put("companyYn", hMap.get("companyYn"));
		reqMap.put("userId", hMap.get("userId"));
		reqMap.put("refreshToken", hMap.get("refreshToken"));
		// hMap.remove("refreshToken");

		logger.debug("UserInfo==>{}", reqMap.toString());

		// TODO: AS-IS상에 접속 URL별 분기처리 로직 코드화 하여 처리하는 로직 추가


		List<Object> loginInfos = new ArrayList<>();
		
		if ("Y".equals(reqMap.get("companyYn"))) { // 제약회사 로그인
			loginInfos = cmmnService.selectList("login.selectLoginForMaker", reqMap);
			HashMap<String, Object> loginInfo = (HashMap<String, Object>) loginInfos.get(0);
		
			//제약사 로그인 사용여부
			String useYn = (String) loginInfo.get("SUPPLY_USE_YN");
			if (!ObjectUtils.isEmpty(loginInfos)) {				
				if (useYn.equals("Y")) {
					String token = jwtUtil.createToken(String.valueOf(reqMap.get("loginId"))); // 사용자 정보로 토큰 생성
					String refreshToken = jwtUtil.createRefreshToken(String.valueOf(reqMap.get("loginId")));

					HashMap<String, Object> pMap = new HashMap<>();

					pMap.put("userData", loginInfo);
					pMap.put("accessToken", token);
					pMap.put("refreshToken", refreshToken);

					/* 로그인 유저의 refreshToken 저장 */
					reqMap.put("refreshToken", refreshToken);

					// 제약회사 로그인 토큰
					cmmnService.update("login.updateRefreshTokenForMaker", reqMap);
					
					/* 로그인 성공시 히스토리 테이블 저장 */
					reqMap.put("ipAddr", request.getHeader("Proxy-Client-IP"));
					reqMap.put("accessToken", token);
					reqMap.put("userData", pMap.get("userData"));
					cmmnService.insert("login.insertLoginHistory", reqMap);

					return new ResponseEntity<>(pMap, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("제약사 사용권한", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
			}
		
		} else if ("N".equals(reqMap.get("companyYn"))) { // 일반 로그인
			int loginChk = (int)cmmnService.select("login.selectLoginOnuri", reqMap);
			if(loginChk == 0) {
				return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
			}
			loginInfos = cmmnService.selectList("login.selectLoginCust", reqMap);				
			if (!ObjectUtils.isEmpty(loginInfos)) {
				HashMap<String, Object> loginInfo = (HashMap<String, Object>) loginInfos.get(0);
				String useYn = (String) loginInfo.get("USE_YN");
				
				if (useYn.equals("Y")) {
					String token = jwtUtil.createToken(String.valueOf(reqMap.get("loginId"))); // 사용자 정보로 토큰 생성
					String refreshToken = jwtUtil.createRefreshToken(String.valueOf(reqMap.get("loginId")));

					HashMap<String, Object> pMap = new HashMap<>();

					pMap.put("userData", loginInfo);
					pMap.put("accessToken", token);
					pMap.put("refreshToken", refreshToken);

					/* 로그인 유저의 refreshToken 저장 */
					reqMap.put("refreshToken", refreshToken);

					// 일반 로그인 토큰
					cmmnService.update("login.updateRefreshTokenForUser", reqMap);
					
					/* 로그인 성공시 히스토리 테이블 저장 */
					reqMap.put("ipAddr", request.getHeader("Proxy-Client-IP"));
					reqMap.put("accessToken", token);
					reqMap.put("userData", pMap.get("userData"));
					cmmnService.insert("login.insertLoginHistory", reqMap);

					return new ResponseEntity<>(pMap, HttpStatus.OK);
					
				} else {
					return new ResponseEntity<>("미사용처리", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
			}
						
		} else {
			return new ResponseEntity<>(Gv.LOGIN_FAIL_MSG, HttpStatus.BAD_REQUEST);
		}
	}

}
