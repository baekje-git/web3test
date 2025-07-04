package ever.login.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ever.cmmn.service.CmmnService;
import ever.login.service.LoginService;
import ever.support.CmnUtil;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.jwt.JwtUtil;
import ever.support.security.module.sha.SHACipher;
import ever.support.session.PassPermission;
import kada.k99.service.RoleService;
import kada.k99.service.UserRegistService;
import kada.util.Util;

@CrossOrigin(origins = "http://localhost", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class LoginController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LoginService loginService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	CmmnService cmmnService;

	@Autowired
	private UserRegistService userRegistService;

	@Autowired
	private RoleService roleService;

	@PostMapping("/login2")
	@ResponseBody
	public Object procLogin(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("로그인 처리");

		String pwdSha256 = String.valueOf(hMap.get("USER_PWD"));
		hMap.put("USER_PWD", (new SHACipher()).encryptSHA256(pwdSha256));
		logger.debug("UserInfo==>{}", hMap.toString());

		HashMap<String, Object> loginInfo = (HashMap<String, Object>) cmmnService.select("login.selectLogin", hMap);

		if (loginInfo != null) {
			String token = jwtUtil.createToken(String.valueOf(hMap.get("USER_ID"))); // 사용자 정보로 토큰 생성
			response.setHeader(HttpHeaders.AUTHORIZATION, token); // client에 token 전달
			return CmmnUtil.resultMsg(Gv.SUCC, Gv.SUCCMSG, loginInfo);
		} else {
			return CmmnUtil.resultMsg(Gv.FAIL, Gv.LOGIN_FAIL_MSG);
		}

	}

	@PostMapping("/login/token")
	@ResponseBody
	public Object generateToken(@RequestBody String userId) {
		String jwtToken = jwtUtil.createToken(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("accessToken", jwtToken);
		return result;
	}

	@PostMapping("/login/user")
	@ResponseBody
	public Object getUserId(@RequestBody String token) {
		String userId = jwtUtil.getUserId(token);
		Map<String, Object> result = new HashMap<>();
		result.put("userId", userId);
		return result;
	}

	@PostMapping("/jwt/login")
	@ResponseBody
	public ResponseEntity<Object> jwtLogin(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("jwt-로그인 처리");

		return loginService.login(hMap, request);
	}
	
	@RequestMapping(value = "/jwt/login-cust", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<Object> jwtLoginCust(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("jwt-login-cust 처리");

		return loginService.loginCust(hMap, request);
	}
	
	@RequestMapping(value = "/onuriLogin.act", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public RedirectView jwtLoginCust2(
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {

	    CmmnUtil.methodLog("onuriLogin 처리");

	    // ✅ 직접 파라미터 받기
	    String onuriCust = request.getParameter("onuriCust");
	    String onuriKey = request.getParameter("onuriKey");
	    String frontendUrl = request.getParameter("frontendUrl");


	    // ✅ fallback 처리
	    if (frontendUrl == null || frontendUrl.isEmpty()) {
	        frontendUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	    }
	    String redirectUrl = frontendUrl + "/dist/onuriLogin"
	            + "?onuriCust=" + onuriCust
	            + "&onuriKey=" + onuriKey;
	    return new RedirectView(redirectUrl);
	}
	// 1. JSP 렌더링용 - 초기 진입 (값 없음)
	@RequestMapping("/onuriLoginJsp.act")
	public void onuriLoginTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 초기엔 값 세팅하지 않음
	    request.getRequestDispatcher("/onuriLoginTest.jsp").forward(request, response);
	}

	// 2. 버튼 클릭 시 Ajax로 호출 → Java 값 전달
	@RequestMapping(value = "/getOnuriInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getOnuriInfo() {
	    Map<String, String> result = new HashMap<>();
	    result.put("onuriCust", "4401048");      // 여기서 원하는 서버 값 세팅
	    result.put("onuriKey", "120e5ca1e41ec87b0135df3f49bbf15cf");    // 실제 로직 적용해도 됨
	    return result;
	}
	
	@RequestMapping(value = "/shinhanLogin.act", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public RedirectView jwtLoginCust3(
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {

	    CmmnUtil.methodLog("shinhanLogin 처리");
	    
	    HashMap<String, Object> hMap = CmnUtil.getParameters(request);
	    //String shinhanCheck = (String) cmmnService.select("login.selectLoginShinhan", hMap);
	    
	    // ✅ 직접 파라미터 받기
	    String frontendUrl = request.getParameter("frontendUrl");

	    // ✅ fallback 처리
	    if (frontendUrl == null || frontendUrl.isEmpty()) {
	        frontendUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	    }
	    String redirectUrl = frontendUrl + "/dist/shinhanLogin"
	            + "?shinhanCust=" + hMap.get("shinhanCust").toString()
	            + "&shinhanKey=" + hMap.get("shinhanKey").toString();
	            //+ "&shinhanCheck=" + shinhanCheck;
	    return new RedirectView(redirectUrl);
	}
	// 1. JSP 렌더링용 - 초기 진입 (값 없음)
	@RequestMapping("/shinhanLoginJsp.act")
	public void shinhanLoginTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    System.out.println("🔥🔥 shinhanLoginJsp.act 컨트롤러 진입!");
	    request.getRequestDispatcher("/shinhanLoginTest.jsp").forward(request, response);
	}
	// 2. 버튼 클릭 시 Ajax로 호출 → Java 값 전달
	@RequestMapping(value = "/getShinhanInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getShinhanInfo() {
	    Map<String, String> result = new HashMap<>();
	    result.put("shinhanCust", "4610001");      // 여기서 원하는 서버 값 세팅
	    result.put("shinhanKey", "8d969eef6ecad3c29a3a629280e686cf0");    // 실제 로직 적용해도 됨
	    return result;
	}

	
	@RequestMapping(value = "coldChain.act")
	@PassPermission
	public ModelAndView coldChainEndReg(HttpServletRequest request, ModelMap model) throws Exception {

	    ModelAndView mv = new ModelAndView("forward:/popupColdChainEndReg.jsp");

	    HashMap<String, Object> hm = CmnUtil.getParameters(request);

	    HashMap<String, Object> resultMap = (HashMap<String, Object>) cmmnService.select("login.selectCdcnSalesDlvEndCheck", hm);

	    if (resultMap != null) {
	        String strEndChk = String.valueOf(resultMap.get("END_CHK"));

	        if ("2".equals(strEndChk)) {

	            model.addAttribute("CUST_NM", resultMap.get("CUST_NM"));
	            model.addAttribute("THERMO_ID", resultMap.get("THERMO_ID"));
	            model.addAttribute("SN", resultMap.get("SN"));
	            model.addAttribute("BOX_ID", resultMap.get("BOX_ID"));
	            model.addAttribute("MSG", "거래처 배송종료가 <b>이미</b> 처리되었습니다.");
	        } else if ("1".equals(strEndChk)) {
	            int retcnt = cmmnService.update("login.updateCdcnSalesDlvEndProc", hm);

	            model.addAttribute("CUST_NM", resultMap.get("CUST_NM"));
	            model.addAttribute("THERMO_ID", resultMap.get("THERMO_ID"));
	            model.addAttribute("SN", resultMap.get("SN"));
	            model.addAttribute("BOX_ID", resultMap.get("BOX_ID"));
	            model.addAttribute("MSG", "거래처 배송종료가 <b>정상</> 처리되었습니다.");
	        }
	    } else {

	        model.addAttribute("CUST_NM", "");
	        model.addAttribute("THERMO_ID", "");
	        model.addAttribute("SN", "");
	        model.addAttribute("BOX_ID", "");
	        model.addAttribute("MSG", "배송종료 처리할 내용이 조회되지 않습니다. 관리자에게 확인해주세요.");
	    }

	    return mv;
	}
	
	@PostMapping("/jwt/refresh-token")
	@ResponseBody
	public ResponseEntity<Object> jwtRefreshToken(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("/jwt/refresh-token 처리");
		logger.debug("Refresh-Token==>{}", hMap.toString());

		/***
		 * 이전에 생성된 refreshToken으로 체크해서
		 * 적합한 경우에만 신규 accessToken,refreshToken발급처리
		 */
		HashMap<String, Object> loginInfo = (HashMap<String, Object>) cmmnService.select("login.selectLogin", hMap);
		
		
		if (loginInfo != null) { /* db에 저장된 토큰과 같은경우 */
			String clientRefreshToken = Util.getValue("refreshToken", hMap);

			if(jwtUtil.isRefreshTokenExpired(clientRefreshToken)) { /* refreshToken 만료 */
				return new ResponseEntity<>(Gv.EXPIRED_REFRESH_TOKEN_MSG, HttpStatus.BAD_REQUEST);
			}else {
				/* acessToken 재발급 */
				String userId = Util.getValue("userId", hMap);
				String token = jwtUtil.createToken(userId); // 사용자 정보로 토큰 생성
				//String refreshToken = jwtUtil.createRefreshToken(userId);
				response.setHeader(HttpHeaders.AUTHORIZATION, token); // client에 token 전달
				HashMap<String, Object> pMap = new HashMap<>();
				pMap.put("userData", loginInfo);
				pMap.put("accessToken", token);
				//pMap.put("refreshToken", refreshToken);

				/* 로그인 유저의 refreshToken 저장 */
				//hMap.put("loginId", userId);
				//hMap.put("refreshToken", refreshToken);
				//cmmnService.update("login.updateRefreshTokenForUser", hMap);

				return new ResponseEntity<>(pMap, HttpStatus.OK);
			}

		} else { /* db에 저장된 토큰과 다른경우 : 다른 device에서 로그인이 일어난경우이므로 exception */
			return new ResponseEntity<>(Gv.EXPIRED_TOKEN_MSG, HttpStatus.BAD_REQUEST);
		}
	}

	//삭제대상
	@PostMapping("/login/passworddReset")
	@ResponseBody
	public ResponseEntity<Object> passworddReset(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("비밀번호 초기화 요청");
		logger.debug("입력값==>{}", hMap.toString());

		HashMap<String, Object> loginInfo = (HashMap<String, Object>) cmmnService.select("login.selectUserCheck", hMap);

		if (loginInfo != null) {
			hMap.put("USER_ID", loginInfo.get("USER_ID"));
			userRegistService.initPasswordUser(hMap);
			return new ResponseEntity<>("메일로 초기화된 비밀번호가 전송되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("요청하신 사용자가 존재하지않습니다.", HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping("/jwt/register")
	@ResponseBody
	public ResponseEntity<Object> jwtRegister(@RequestBody HashMap<String, Object> hMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CmmnUtil.methodLog("회원가입 처리");
		//logger.debug("회원가입 처리", hMap.toString());

		/***
		 * 회원정보로 등록된 선수인지 체크
		 *
		 */
		List<Object> loginInfo = cmmnService.selectList("login.selectRegisterCheck", hMap);

		if (loginInfo != null && loginInfo.size() == 1) {
			HashMap<String, Object> userData = (HashMap<String, Object>) loginInfo.get(0);

			String regYn = Util.getValue("PLAYER_REG_YN", userData);
			if("Y".equals(regYn))return new ResponseEntity<>(Gv.LOGIN_REGPLAYER_MSG, HttpStatus.INTERNAL_SERVER_ERROR);

			String userId = Util.getValue("USER_ID", userData);
			String token = jwtUtil.createToken(userId); // 사용자 정보로 토큰 생성
			String refreshToken = jwtUtil.createRefreshToken(userId);
			response.setHeader(HttpHeaders.AUTHORIZATION, token); // client에 token 전달


			/* 로그인 유저의 refreshToken 저장 */
			hMap.put("REFRESH_TOKEN", refreshToken);
			cmmnService.update("login.updateRefreshToken", hMap);

			/* 비밀번호 암호화 */
			hMap.put("USER_ID", userId);
			hMap.put("USER_PWD", Util.SHA256(Util.getValue("USER_PWD", hMap)));
			cmmnService.update("login.registerUser", hMap);

            HashMap<String, Object> userInfo = (HashMap<String, Object>) cmmnService.select("login.selectLogin", hMap);

			HashMap<String, Object> pMap = new HashMap<>();
			pMap.put("userData", userInfo);
			pMap.put("accessToken", token);
			pMap.put("refreshToken", refreshToken);

			//권한생성
			hMap.put("USER_FLAG", "P");
			roleService.triggerSaveRole(hMap);

			return new ResponseEntity<>(pMap, HttpStatus.OK);
		} else if (loginInfo != null && loginInfo.size() > 1) {
			return new ResponseEntity<>(Gv.LOGIN_DUPLE_PLAYER_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(Gv.LOGIN_NOPLAYER_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/userList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getUserList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {

    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("chem.T_USER", hMap));
    		returnMap.put("cnt", cmmnService.select("chem.T_USER_CNT", hMap));

			return new ResponseEntity<>(returnMap, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@RequestMapping(value="/useHisList",  method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> useHisList(@RequestBody HashMap<String, Object> hMap) throws Exception {
    	try {

    		HashMap<String, Object> returnMap = new HashMap<String, Object>();
    		returnMap.put("data", cmmnService.selectList("chem.selectUseHisList", hMap));
    		returnMap.put("cnt", cmmnService.select("chem.selectUseHisCnt", hMap));

			return new ResponseEntity<>(returnMap, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("[오류]: ", e);
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	
}
