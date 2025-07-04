package baekje.web.payment.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import baekje.web.payment.service.PaymentService;
import baekje.web.payment.wpaystd.WpayStdSample;
import ever.cmmn.service.CmmnService;
import ever.support.Gv;
import ever.support.cmmn.CmmnUtil;
import ever.support.pay.PayClient;

@RequestMapping("/payment")
@RestController
public class PaymentController {

	@Autowired
	CmmnService cmmnService;

	@Autowired
	PaymentService paymentService;

	private Logger log = LogManager.getLogger(this.getClass());
	
	/**
	 * 신용카드 결제금액 조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/paymentAmount")
	@ResponseBody
	public ResponseEntity<Object> selectCreditcardList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		List<Object> list = cmmnService.selectList("payment.selectCreditcardList", hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	/**
	 * 카드결제현황 목록조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/listSearch")
	@ResponseBody
	public ResponseEntity<Object> selectOrdLedgerSearchList(@RequestParam HashMap<String, Object> hMap)
			throws Exception {
		
		

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 카드결제현황 결제건수, 결제총액 조회
		List<Object> masterData = cmmnService.selectList("payment.selectPaymentmMaster", hMap);

		// 카드결제현황 목록 조회
		List<Object> list = cmmnService.selectList("payment.selectPaymentSearchList", hMap);

		rtnMap.put("masterData", masterData.get(0));
		rtnMap.put("list", list);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}

	/**
	 * 카드결제현황 목록조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/dpstList")
	@ResponseBody
	public ResponseEntity<Object> selectDepositStatusList(@RequestParam HashMap<String, Object> hMap) throws Exception {

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 카드결제현황 결제건수, 결제총액 조회
		List<Object> masterData = cmmnService.selectList("payment.selectDepositStatusMaster", hMap);

		// 카드결제현황 목록 조회
		List<Object> list = cmmnService.selectList("payment.selectDepositStatusList", hMap);

		rtnMap.put("masterData", masterData.get(0));
		rtnMap.put("list", list);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);

	}

	/**
	 * 나의카드 등록(신규, 수정)
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveMyCard")
	@ResponseBody
	public ResponseEntity<Object> saveMyCard(@RequestBody HashMap<String, Object> hMap) throws Exception {
		paymentService.saveMyCard(hMap);

		return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
	}

	/**
	 * 나의카드 목록 조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/myCard")
	@ResponseBody
	public ResponseEntity<Object> selectMyCardList(@RequestParam HashMap<String, Object> hMap) throws Exception {
		List<Object> list = paymentService.selectMyCardList(hMap);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 나의카드 상세 조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/myCardDtl")
	@ResponseBody
	public ResponseEntity<Object> selectMyCardDtl(@RequestParam HashMap<String, Object> hMap) throws Exception {
		HashMap<String, Object> rtnMap = paymentService.selectMyCardDtl(hMap);

		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}

	/**
	 * 나의카드 삭제
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/removeMyCard")
	@ResponseBody
	public ResponseEntity<Object> deleteMyCard(@RequestParam HashMap<String, Object> hMap) throws Exception {
		try {
			paymentService.deleteMyCard(hMap);
			return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Gv.FAILMSG, HttpStatus.OK);
		}
	}

	/**
	 * 결제 전 비밀번호 체크
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pwdChk")
	@ResponseBody
	public ResponseEntity<Object> selectPwdChk(@RequestParam HashMap<String, Object> hMap) throws Exception {

		int cnt = paymentService.selectPwdChk(hMap);

		if (cnt > 0) {
			return new ResponseEntity<>(hMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Gv.LOGIN_NOPW_MSG, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * 결제
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reqPayment")
	@ResponseBody
	public ResponseEntity<Object> insertPayment(@RequestBody HashMap<String, Object> hMap) throws Exception {
		long time = System.currentTimeMillis();

		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String sysTime = dayTime.format(new Date(time)); // 주문번호 가맹점에서 생성한 유일한 번호
		SimpleDateFormat dayTimeSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String moid = dayTimeSSS.format(new Date(time)); // 주문번호 가맹점에서 생성한 유일한 번호

		// HashMap<String, Object> hm = CmnUtil.getParameters(request);
		String sendData = "";

		String cardGb = String.valueOf(hMap.get("cardGb"));

		String cardNo1 = String.valueOf(hMap.get("cardNo1"));
		String cardNo2 = String.valueOf(hMap.get("cardNo2"));
		String cardNo3 = String.valueOf(hMap.get("cardNo3"));
		String cardNo4 = String.valueOf(hMap.get("cardNo4"));

		String cardY = String.valueOf(hMap.get("cardY"));
		String cardM = String.valueOf(hMap.get("cardM"));
		if (cardM.length() == 1) {
			cardM = "0" + cardM;
		}
		
		String birthDt = String.valueOf(hMap.get("birthDt"));
		String cardQuota = String.valueOf(hMap.get("ins"));

		String CardNo = cardNo1 + cardNo2 + cardNo3 + cardNo4;
		String cardYM = cardY + cardM;
		String payAmt = String.valueOf(hMap.get("paymentAmountTi")).replace(",", "");
		String custServiceNo = "";
		String brchCd = "";
		String custCd = "";
		String custNm = "";
		String ceoNm = "";
		String telNo = "";
		String email = "";
		String serviceNo = "";
		String supCode = "";
		String empId = "";

		String[] dataArray = new String[0];
		HashMap<String, Object> sendMap = new HashMap<String, Object>();
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		HashMap<String, Object> dataHm = new HashMap<String, Object>();

		List<Object> custData = new ArrayList<>();

		PayClient runSocket = new PayClient();

		custData = cmmnService.selectList("payment.selectPaymentCustInfo", hMap);

		if (custData.size() > 0) {

			HashMap<String, Object> custHm = (HashMap<String, Object>) custData.get(0);

			// 팜페이
			custServiceNo = String.valueOf(custHm.get("CUST_SERVICE_NO"));
			brchCd = String.valueOf(custHm.get("BRCH_CD"));
			custCd = String.valueOf(custHm.get("CUST_CD"));
			custNm = String.valueOf(custHm.get("CUST_NM"));
			ceoNm = String.valueOf(custHm.get("CEO_NM"));
			serviceNo = String.valueOf(custHm.get("SERVICE_NO"));
			supCode = String.valueOf(custHm.get("SUPCODE"));
			empId = String.valueOf(custHm.get("EMP_ID"));

			// 이니시스
			sendMap.put("sysTime", sysTime);
			sendMap.put("moid", moid);
			sendMap.put("mid", String.valueOf(custHm.get("MID")));
			sendMap.put("goodName", String.valueOf(String.valueOf(custHm.get("EMP_NM")) + "_"
					+ String.valueOf(custHm.get("CUST_CD")) + "_" + String.valueOf(custHm.get("CUST_NM"))));
			sendMap.put("price", payAmt);
			sendMap.put("buyerName", String.valueOf(custHm.get("CEO_NM")));
			sendMap.put("buyerEmail", String.valueOf(custHm.get("EMAIL")));
			sendMap.put("cardQuota", cardQuota);
			sendMap.put("cardNumber", CardNo);
			sendMap.put("cardExpire", cardYM);
			sendMap.put("regNo", birthDt);
			sendMap.put("userId", String.valueOf(custHm.get("EMP_LOGIN_ID")));

			infoMap.put("URL", String.valueOf(custHm.get("URL")));
			infoMap.put("mid", String.valueOf(custHm.get("MID")));
			infoMap.put("KEY", String.valueOf(custHm.get("KEY")));
			infoMap.put("IV", String.valueOf(custHm.get("IV")));

		}

		if (cardGb.equals("INICIS")) {

			HashMap<String, Object> recvMap = new HashMap<String, Object>();
			recvMap = runSocket.runInicis(sendMap, infoMap);

			dataHm = new HashMap<String, Object>();
			dataHm.put("CUST_CD", custCd);
			dataHm.put("BRCH_CD", brchCd);
			dataHm.put("USER_ID", hMap.get("userId"));

			if (recvMap.get("resultCode").equals("00")) {
				dataHm.put("RETCODE", "0000");
				dataHm.put("RETMSG", recvMap.get("resultMsg"));
				dataHm.put("TID", recvMap.get("tid"));
				dataHm.put("ORDERNO", sysTime);
				dataHm.put("ORDERDATE", sysTime.substring(0, 8));
				dataHm.put("ORDERTIME", sysTime.substring(8));
				dataHm.put("TRANAMT", recvMap.get("price"));
				dataHm.put("CARDNO", "APRV");
				dataHm.put("INS", cardQuota);
				dataHm.put("TRADENO", recvMap.get("payDate").toString() + recvMap.get("payTime").toString());
				dataHm.put("APPROVENO", recvMap.get("payAuthCode"));
				dataHm.put("APRV_INS", recvMap.get("payAuthQuota"));
				dataHm.put("APPROVEDATE", recvMap.get("payDate").toString() + recvMap.get("payTime").toString());
				dataHm.put("CARDCODE", recvMap.get("cardCode"));
				dataHm.put("CARDNAME", recvMap.get("cardName"));
				dataHm.put("CARDCODEM", recvMap.get("cardCode"));
				dataHm.put("CARDNAMEM", recvMap.get("cardName"));

				paymentService.insertResultPayment(dataHm);
				return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);

				// model.addAttribute("RETCODE", "0000");
				// model.addAttribute("RETMSG", "정상처리 되었습니다.");
			} else {
				dataHm.put("RETCODE", recvMap.get("resultCode"));
				dataHm.put("RETMSG", recvMap.get("resultMsg"));
				dataHm.put("ORDERNO", sysTime);
				dataHm.put("ORDERDATE", sysTime.substring(0, 8));
				dataHm.put("ORDERTIME", sysTime.substring(8));

				paymentService.insertResultPayment(dataHm);
				return new ResponseEntity<>(String.valueOf(recvMap.get("resultMsg")), HttpStatus.BAD_REQUEST);

				// model.addAttribute("RETCODE", recvMap.get("resultCode"));
				// model.addAttribute("RETMSG", recvMap.get("resultMsg"));
			}

		} else {

			sendData = CardNo + "|" + cardYM + "|" + payAmt + "|" + custServiceNo + "|" + custNm + "|" + ceoNm + "|"
					+ serviceNo + "|" + supCode + "|" + cardQuota + "|" + empId;

			 log.info("sendData:"+sendData);

			String receiveData = runSocket.runProcess(sendData);

			// log.info("receiveData:"+receiveData);

			receiveData = receiveData.replaceAll("\\=", " ");
			receiveData = receiveData.replaceAll("\\?", " ");
			dataArray = receiveData.split("\\^");
			dataHm = new HashMap<String, Object>();

			dataHm.put("CUST_CD", custCd);
			dataHm.put("BRCH_CD", brchCd);
			dataHm.put("USER_ID", hMap.get("userId"));

			for (int i = 0; i < dataArray.length; i++) {
				switch (i + 2) {

				case 2:
					dataHm.put("RETCODE", dataArray[i].substring(4, 8));
					break;
				case 3:
					dataHm.put("RETMSG", dataArray[i]);
					break;
				case 4:
					// dataHm.put("", dataArray[i]);
					break;
				case 5:
					// dataHm.put("", dataArray[i]);
					break;
				case 6:
					// dataHm.put("", dataArray[i]);
					break;
				case 7:
					// dataHm.put("", dataArray[i]);
					break;
				case 8:
					// dataHm.put("", dataArray[i]);
					break;
				case 9:
					dataHm.put("ORDERNO", dataArray[i]);
					break;
				case 10:
					// dataHm.put("", dataArray[i]);
					break;
				case 11:
					dataHm.put("TRANAMT", dataArray[i]);
					break;
				case 12:
					dataHm.put("CARDNO", "APRV");
					break;
				case 13:
					dataHm.put("INS", dataArray[i]);
					break;
				case 14:
					dataHm.put("TRADENO", dataArray[i]);
					break;
				case 15:
					dataHm.put("APPROVENO", dataArray[i]);
					break;
				case 16:
					dataHm.put("APRV_INS", dataArray[i]);
					break;
				case 17:
					// dataHm.put("APPROVEDATE", dataArray[i]);
					break;
				case 18:
					dataHm.put("APPROVEDATE", dataArray[i - 1] + dataArray[i]);
					break;
				case 19:
					dataHm.put("CARDCODE", dataArray[i]);
					break;
				case 20:
					dataHm.put("CARDNAME", dataArray[i]);
					break;
				case 21:
					dataHm.put("CARDCODEM", dataArray[i]);
					break;
				case 22:
					dataHm.put("CARDNAMEM", dataArray[i]);
					break;
				case 23:
					// dataHm.put("", dataArray[i]);
					break;
				case 24:
					// dataHm.put("", dataArray[i]);
					break;
				}

			}
			paymentService.insertResultPayment(dataHm);

			// model.addAttribute("RETCODE", dataArray[0].substring(4, 8));
			if ("0000".equals(dataArray[0].substring(4, 8))) {
				// model.addAttribute("RETMSG", "정상처리 되었습니다.");
				return new ResponseEntity<>(Gv.SUCCMSG, HttpStatus.OK);
			} else {
				// model.addAttribute("RETMSG", dataArray[1]);
				return new ResponseEntity<>(dataArray[1], HttpStatus.BAD_REQUEST);
			}

		}

	}

	/**
	 * Wpay 유저 키 조회
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/wPayUserKey")
	@ResponseBody
	public ResponseEntity<Object> getWpayUserKey(HttpServletRequest request) throws Exception {
		HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
		log.info("wPayUserKey reqMap:"+reqMap);
 		String CARD_GB = String.valueOf(reqMap.get("cardGb"));
		if (CARD_GB.equals("INICIS_SHINHAN")) {
			// 공통코드에서 g_HASHKEY, g_SEEDKEY, g_SEEDIV 불러오기
			reqMap.put("cardGb","01");
		} else if (CARD_GB.equals("INICIS_ETC")) {
			// 공통코드에서 g_HASHKEY, g_SEEDKEY, g_SEEDIV 불러오기
			reqMap.put("cardGb","02");
		}
		HashMap<String, Object> rtnMap = (HashMap<String, Object>) cmmnService.select("payment.selectWpayUserKey", reqMap);
		return new ResponseEntity<>(rtnMap, HttpStatus.OK);
	}

	/**
	 * 화면에서 간편결제 popup 최초 호출 (시작점)
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wpaystdPayAuthCardRequest")
	public ModelAndView wpaystdPayAuthCardRequest(HttpServletRequest request, ModelMap model) throws Exception {

		WpayStdSample wpayStdSample = new WpayStdSample();

		// log.info("\n *********** 간편결제 POPUP (WEB ORDER SYSTEM) ***********");
		ModelAndView mv;
		
		//HttpSession session = request.getSession();

		HashMap<String, Object> dataHm = new HashMap<String, Object>();

		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String sysTime = dayTime.format(new Date(time)); // 주문번호 가맹점에서 생성한 유일한 번호
		SimpleDateFormat dayTimeSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String moid = dayTimeSSS.format(new Date(time)); // 주문번호 가맹점에서 생성한 유일한 번호

		HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
		log.info("wpaystdPayAuthCardRequest reqMap:"+reqMap);
		HashMap<String, Object> resMap = new HashMap<>();

		String requestDomain = "";
		String g_HASHKEY = "";
		String g_SEEDKEY = "";
		String g_SEEDIV = "";
		String targetUrl = "";
//		String g_CARD_GB = String.valueOf(session.getAttribute("P_CARD_GB"));
//		String g_AMT = String.valueOf(session.getAttribute("P_AMT"));

		targetUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		targetUrl = targetUrl + "/payment";

		String PROCTYPE = ""; // 1:가입,2:카드등록,3:요청,4:승인

		String CARD_GB = String.valueOf(reqMap.get("cardGb"));
		if (CARD_GB.equals("null")) {
			CARD_GB = String.valueOf(reqMap.get("CARD_GB"));
		}
		if (CARD_GB.equals("INICIS_SHINHAN")) {
			reqMap.put("cardGb","01");
		} else if (CARD_GB.equals("INICIS_ETC")) {
			reqMap.put("cardGb","02");
		}

		String param_mid = ""; // [필수] 가맹점 ID
		String param_userId = "";
		String param_wpayUserKey = ""; // [필수] 이니시스에서 발행한 wpayUserKey - (SEED 암호화 대상필드)wpayUserKey
		String param_wpayToken = "1"; // 카드등록 미사용:1(사용시공백으로)
		//String param_SignKey = "";
		String goodName = "";
		String buyerName = "";
		String buyerEmail = "";
		String amountTi = String.valueOf(reqMap.get("PAYMENT_AMOUNT_TI")).replace(",", "");
		String cardCode = "";

		if ("null".equals(amountTi)) {
			amountTi = String.valueOf(reqMap.get("amountTi")).replace(",", "");
		}
		reqMap.put("amountTi",amountTi);
		

		// wpayUserKey 조회하기
		List<Object> getWpayUserKey = new ArrayList<>();
		getWpayUserKey = cmmnService.selectList("payment.selectWpayUserKey", reqMap);
		
		if (getWpayUserKey.size() > 0) {
			HashMap<String, String> sNdvo = (HashMap<String, String>) getWpayUserKey.get(0);

			// log.info("\n CUST_CD : " + sNdvo.get("CUST_CD"));
			// log.info("\n CUST_NM : " + sNdvo.get("CUST_NM"));
			// log.info("\n BRCH_CD : " + sNdvo.get("BRCH_CD"));
			// log.info("\n MID : " + sNdvo.get("MID"));
			// log.info("\n SIGNKEY : " + sNdvo.get("SIGNKEY"));
			// log.info("\n WPAY_USER_KEY : " + sNdvo.get("WPAY_USER_KEY"));
			// log.info("\n REQUEST_DOMAIN : " + sNdvo.get("REQUEST_DOMAIN"));
			// log.info("\n SEED_KEY : " + sNdvo.get("SEED_KEY"));
			// log.info("\n SEED_IV : " + sNdvo.get("SEED_IV"));
			// log.info("\n HASH_KEY : " + sNdvo.get("HASH_KEY"));

			param_mid = sNdvo.get("MID");
			//param_SignKey = sNdvo.get("SIGNKEY");
			param_wpayUserKey = sNdvo.get("WPAY_USER_KEY");
			requestDomain = sNdvo.get("REQUEST_DOMAIN");
			g_SEEDKEY = sNdvo.get("SEED_KEY");
			g_SEEDIV = sNdvo.get("SEED_IV");
			g_HASHKEY = sNdvo.get("HASH_KEY");
			goodName = String.valueOf(String.valueOf(sNdvo.get("EMP_NM")) + "_" + String.valueOf(sNdvo.get("CUST_CD"))
					+ "_" + String.valueOf(sNdvo.get("CUST_NM")));
			buyerName = String.valueOf(sNdvo.get("CEO_NM"));
			buyerEmail = String.valueOf(sNdvo.get("EMAIL"));
			cardCode = String.valueOf(sNdvo.get("CARDCODE")); // 간편결제(신한):01,간편결재(삼성,현대):02

			// 세션값 정보
//			HashMap<String,Object> mst = new com.util.GSSMap<String, Object>();
//			HashMap<String,Object> gss = (HashMap<String,Object>)mst.get("GSS");
			resMap.put("g_HASHKEY" , g_HASHKEY);
			resMap.put("g_SEEDKEY" , g_SEEDKEY);
			resMap.put("g_SEEDIV" , g_SEEDIV);
			resMap.put("g_MID" , param_mid);
			resMap.put("g_WPAYUSERKEY" , param_wpayUserKey);
			resMap.put("g_requestDomain" , requestDomain);
			resMap.put("g_strCurrentDomain" , targetUrl);
			resMap.put("g_amountTi" , amountTi);
			resMap.put("g_CARDCODE" , cardCode);
			resMap.put("CUST_CD" , sNdvo.get("CUST_CD"));
			resMap.put("BRCH_CD" , sNdvo.get("BRCH_CD"));
			resMap.put("USER_ID" , sNdvo.get("CUST_CD"));
			
			// 테스트시 확인 20241130
			// session.setAttribute("P_BRCH_CD", sNdvo.get("BRCH_CD"));

			// 변경된 정보 갱신
			// javax.servlet.http.HttpSession session = request.getSession();
			// session.setAttribute("GSS", gss);
			// session.setMaxInactiveInterval(30*60);

		}
		String requestURL = requestDomain + "/stdwpay/std/u/v1/payauth/stdMain";
		
		// 회원여부 체크(DB조회)하여 비회원일때 회원가입으로 처리
		if (param_wpayUserKey == null || param_wpayUserKey.length() == 0) {
			requestURL = requestDomain + "/stdwpay/std/u/v1/memreg";

			param_userId = reqMap.get("custCd").toString(); // 회원가입시에는 임의의 값(거래처코드 = 유저아이디)
			PROCTYPE = "1";// 1:가입,2:카드등록,3:요청,4:승인
			mv = new ModelAndView("/payment/wpaystdMemRegForm");
			
			model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
			model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
			model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));
			
			model.addAttribute("CARD_GB", CARD_GB);
			model.addAttribute("requestDomain", requestDomain);
			model.addAttribute("g_HASHKEY", g_HASHKEY);
			model.addAttribute("g_SEEDKEY", g_SEEDKEY);
			model.addAttribute("g_SEEDIV", g_SEEDIV);
			model.addAttribute("g_MID", param_mid);
			model.addAttribute("param_userId", param_userId);
			model.addAttribute("strCurrentDomain", targetUrl);
			model.addAttribute("requestURL", requestURL);
		} else if (param_wpayToken == null || param_wpayToken.length() == 0) { // 카드등록 미사용
			// 신용카드 미 등록시(미사용) 작동안함 (삭제대상)
			log.info("/payment/wpaystdCardRegRequest 작동안해야함");
			mv = new ModelAndView("/payment/wpaystdCardRegRequest");
			wpaystdCardRegRequest(request, model, resMap);
		} else {
			PROCTYPE = "3";// 1:가입,2:카드등록,3:요청,4:승인
			mv = new ModelAndView("/payment/wpaystdPayAuthCardRequest");
			
			// param_mid = String.valueOf(hm.get("mid")); // [필수] 가맹점 ID
			// param_wpayUserKey = String.valueOf(hm.get("wpayUserKey")); // [필수]
			// wpayUserKey - (SEED 암호화 대상필드)
			String param_ci = String.valueOf(reqMap.get("ci")); // [옵션] 고객의 CI
			String param_oid = moid; // [필수] 주문번호
			String param_goodsName = goodName; // [필수] 상품명 - (URL Encoding 대상필드)
			String param_goodsPrice = amountTi; // [필수] 결제금액
			String param_buyerName = buyerName; // [필수] 구매자명 - (URL Encoding 대상필드)

			String param_buyerTel = String.valueOf(reqMap.get("buyerTel")); // [필수] 구매자연락처
			String param_buyerEmail = buyerEmail; // [필수] 구매자이메일

			// String param_flagPin = String.valueOf(hm.get("flagPin")); // [필수] 핀인증생략여부
			String param_flagPin = "Y"; // [필수] 핀인증생략여부
			String param_returnUrl = String.valueOf(reqMap.get("returnUrl")); // [필수] return URL - (URL Encoding 대상필드)
			// String param_resetCssUrl = request.getParameter("resetCssUrl"); // [옵션] CSS
			// URL - (URL Encoding 대상필드)
			// signature 파라미터
			String param_signature = "";

			// Seed 암호화
			param_wpayUserKey = wpayStdSample.seedEncrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);
			param_returnUrl = targetUrl + "/wpaystdPayAuthCardReturn"
				            + "?custCd="+String.valueOf(reqMap.get("custCd"))
				            + "&cardGb="+String.valueOf(reqMap.get("cardGb"))
				            + "&amountTi="+String.valueOf(reqMap.get("amountTi"))
				            ;

			// URL Encoding
			param_goodsName = java.net.URLEncoder.encode(param_goodsName, "UTF-8");
			param_buyerName = java.net.URLEncoder.encode(param_buyerName, "UTF-8");
			param_returnUrl = java.net.URLEncoder.encode(param_returnUrl, "UTF-8");

			// -------------------------------------------------------
			// 3. 위변조 방지체크를 위한 signature 생성
			// (순서주의:연동규약서 참고)
			// * 간편결재시 아래의 param_signature 정보로 반드시
			// -------------------------------------------------------
			String srcStr = "";
			srcStr = "";
			srcStr = srcStr + "mid=" + param_mid;
			srcStr = srcStr + "&wpayUserKey=" + param_wpayUserKey;
			srcStr = srcStr + "&ci=";
			srcStr = srcStr + "&oid=" + param_oid;
			srcStr = srcStr + "&goodsName=" + param_goodsName;
			srcStr = srcStr + "&goodsPrice=" + param_goodsPrice;
			srcStr = srcStr + "&hppType=";
			srcStr = srcStr + "&buyerName=" + param_buyerName;
			srcStr = srcStr + "&buyerTel=" + param_buyerTel;
			srcStr = srcStr + "&buyerEmail=" + param_buyerEmail;
			srcStr = srcStr + "&flagPin=" + param_flagPin;
			srcStr = srcStr + "&returnUrl=" + param_returnUrl;
			srcStr = srcStr + "&hashKey=" + g_HASHKEY;
			param_signature = wpayStdSample.encrypteSHA256(srcStr);

			model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
			model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
			model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));
			
			model.addAttribute("param_mid", param_mid);
			model.addAttribute("param_wpayUserKey", param_wpayUserKey);
			model.addAttribute("param_ci", "");
			model.addAttribute("param_oid", param_oid);
			model.addAttribute("param_goodsName", param_goodsName);
			model.addAttribute("param_goodsPrice", param_goodsPrice);
			model.addAttribute("param_hppType", "");
			model.addAttribute("param_buyerName", param_buyerName);
			model.addAttribute("param_buyerTel", param_buyerTel);
			model.addAttribute("param_buyerEmail", param_buyerEmail);
			model.addAttribute("param_flagPin", param_flagPin);
			model.addAttribute("param_returnUrl", param_returnUrl);
			model.addAttribute("param_signature", param_signature);
			model.addAttribute("requestURL", requestURL);

			//String strURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			//		+ request.getContextPath();
			//strURL = strURL + request.getRequestURI();
			// log.info("\n PROCTYPE(PROCTYPE) : " + PROCTYPE);
			// log.info("\n strURL : " + strURL);
			// log.info("\n CARD_GB_CD(CARD_GB_CD) : " + CARD_GB_CD);
			// log.info("\n amountTi(TRANAMT) : " + amountTi);
			//dataHm = new HashMap<String, Object>();
			//dataHm.put("PROCTYPE", PROCTYPE);
			//dataHm.put("REQUESTURL", strURL);
			//dataHm.put("FORWARDURL", requestURL);
			//dataHm.put("CARDTYPE", CARD_GB_CD);
			//dataHm.put("TRANAMT", amountTi);
			//dataHm.put("BRCH_CD",resMap.get("BRCH_CD"));
			//dataHm.put("CUST_CD",resMap.get("CUST_CD"));
			//dataHm.put("USER_ID",resMap.get("USER_ID"));
			//log.info("간편결제 결과 저장 로그(위변조 방지체크를 위한 signature 생성)"+dataHm);
			//paymentService.insertWorkIngPayment(dataHm);
			
		}

		return mv;
	}
	
	   /**
	    * 간편결재 POPUP 호출(회원가입 처리 후 응답 처리)
	    * <strong>작성자 : </strong>abj1<br>
	    * <strong>작성일 : </strong>2023. 02. 20.오전 9:37:07<br>
	    * 설명 : 직접입력 <br>
	    *
	    * @version 1.0
	    * @author  abj1
	    * @since   2017. 11. 9.
	    * @param   파라미터타입_직접입력  파라미터설명_직접입력
	    * @return  ModelAndView 리턴에대한설명_직접입력
	    */
	   @RequestMapping(value = "wpaystdMemRegRequest")
	   public ModelAndView wpaystdMemRegRequest(HttpServletRequest request, ModelMap model, HashMap<String, Object> resMap) throws Exception {	   
		   WpayStdSample wpayStdSample = new WpayStdSample();
		   	   
	       log.info("\n *********** 간편결재 POPUP [wpaystdMemRegRequest] (WEB ORDER SYSTEM) ***********");
	       ModelAndView mv = new ModelAndView("/payment/wpaystdMemRegRequest");
	       
	       //HttpSession httpSession = request.getSession();
	       //httpSession.setMaxInactiveInterval(30*60);
	       
	       //HashMap<String, Object> dataHm = new HashMap<String, Object> ();
	       
	       HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
	       log.info("wpaystdMemRegRequest request:"+reqMap);
	       String requestDomain     = String.valueOf(reqMap.get("requestDomain"));
	       String requestURL        = requestDomain + "/stdwpay/std/u/v1/memreg";
	       String g_HASHKEY         = String.valueOf(reqMap.get("g_HASHKEY"));
	       String g_SEEDKEY         = String.valueOf(reqMap.get("g_SEEDKEY"));
	       String g_SEEDIV          = String.valueOf(reqMap.get("g_SEEDIV"));
	       
	       //System.out.println("g_HASHKEY : " + g_HASHKEY);
	       //System.out.println("wpaystdMemRegRequest --------------------------------------------");
	       //System.out.println("g_SEEDKEY : " + g_SEEDKEY);
	       //System.out.println("g_SEEDIV : " + g_SEEDIV);
	       
	       //log.info("\n requestDomain  : "+requestDomain);
	       //log.info("\n g_HASHKEY  : "+g_HASHKEY);
	       //log.info("\n g_SEEDKEY  : "+g_SEEDKEY);
	       //log.info("\n g_SEEDIV   : "+g_SEEDIV);
	              
	       String param_mid         = String.valueOf(reqMap.get("mid"));	    // [필수] 가맹점 ID
	       String param_userId 	    = String.valueOf(reqMap.get("userId"));		// [필수] 가맹점 고객 ID - (SEED 암호화 대상필드)
	       String param_ci 		    = String.valueOf(reqMap.get("ci"));			// [옵션] 고객의 CI - (SEED 암호화 대상필드)
	       String param_userNm 	    = String.valueOf(reqMap.get("userNm"));		// [옵션] 고객실명 - (URL Encoding 대상필드)
	       String param_hNum        = String.valueOf(reqMap.get("hNum"));       // [옵션] 고객 휴대폰번호 - (SEED 암호화 대상필드)
	       String param_hCorp 		= String.valueOf(reqMap.get("hCorp"));		// [옵션] 휴대폰 통신사
	       String param_birthDay    = String.valueOf(reqMap.get("birthDay"));   // [옵션] 고객 생년월일(yyyymmdd) - (SEED 암호화 대상필드)
	       String param_socialNo2 	= String.valueOf(reqMap.get("socialNo2"));	// [옵션] 주민번호 뒤 첫자리
	       String param_frnrYn 	    = String.valueOf(reqMap.get("frnrYn"));		// [옵션] 외국인 여부
	       String param_returnUrl 	= String.valueOf(reqMap.get("returnUrl"));	// [필수] 회원가입 결과전달 URL - (URL Encoding 대상필드)
	       // signature 파라미터
	   	   String param_signature	= "";
	       
	       // Seed  암호화
	       param_userId 	= wpayStdSample.seedEncrypt(param_userId, g_SEEDKEY, g_SEEDIV);
	       param_hNum 		= wpayStdSample.seedEncrypt(param_hNum, g_SEEDKEY, g_SEEDIV);
	       param_birthDay 	= wpayStdSample.seedEncrypt(param_birthDay, g_SEEDKEY, g_SEEDIV);
	       
	       // URL Encoding
	       param_userNm 	= java.net.URLEncoder.encode(param_userNm, "UTF-8");
	       param_returnUrl  = java.net.URLEncoder.encode(param_returnUrl, "UTF-8");
	       
	       //-------------------------------------------------------
	   	   // 3. 위변조 방지체크를 위한 signature 생성
	   	   //   (순서주의:연동규약서 참고)
	   	   //-------------------------------------------------------
	   	   String srcStr = "";
	   		
	   	   srcStr = "mid="+param_mid;
	   	   srcStr = srcStr + "&userId="+param_userId;
	   	   srcStr = srcStr + "&ci="+param_ci;
	   	   srcStr = srcStr + "&userNm="+param_userNm;
	   	   srcStr = srcStr + "&hNum="+param_hNum;
	   	   srcStr = srcStr + "&hCorp="+param_hCorp;
	   	   srcStr = srcStr + "&birthDay="+param_birthDay;
	   	   srcStr = srcStr + "&socialNo2="+param_socialNo2;
	   	   srcStr = srcStr + "&frnrYn="+param_frnrYn;
	   	   srcStr = srcStr + "&returnUrl="+param_returnUrl;
	   	   srcStr = srcStr + "&hashKey="+g_HASHKEY;
	   	   //log.info("srcStr:"+srcStr);
	   	   param_signature = wpayStdSample.encrypteSHA256(srcStr);

			model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
			model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
			model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));
			
	   	   model.addAttribute("param_mid", param_mid);
	       model.addAttribute("param_userId", param_userId);
	       model.addAttribute("param_ci", param_ci);
	       model.addAttribute("param_userNm", param_userNm);
	       model.addAttribute("param_hNum", param_hNum);
	       model.addAttribute("param_hCorp", param_hCorp);
	       model.addAttribute("param_birthDay", param_birthDay);
	       model.addAttribute("param_socialNo2", param_socialNo2);
	       model.addAttribute("param_frnrYn", param_frnrYn);
	       model.addAttribute("param_signature", param_signature);
	       model.addAttribute("param_returnUrl", param_returnUrl);
	       model.addAttribute("requestURL", requestURL);
	       
	       model.addAttribute("g_HASHKEY", g_HASHKEY);
	   	   model.addAttribute("g_SEEDKEY", g_SEEDKEY);
	   	   model.addAttribute("g_SEEDIV", g_SEEDIV);
	   	   
	   	   //String strURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	   	   //strURL = strURL + request.getRequestURI();
		   //dataHm = new HashMap<String, Object>();
		   //dataHm.put("PROCTYPE", "1");//1:가입,2:가입return,3:요청,4:승인
		   //dataHm.put("REQUESTURL", strURL);
		   //dataHm.put("FORWARDURL", requestURL);
		   //HttpSession session = request.getSession();
		   //dataHm.put("CARDTYPE", String.valueOf(session.getAttribute("P_CARD_GB")));
		   //dataHm.put("TRANAMT", String.valueOf(session.getAttribute("P_AMT")));
		   //dataHm.put("CUST_CD", String.valueOf(session.getAttribute("P_CUST_CD")));
		   //dataHm.put("BRCH_CD", String.valueOf(session.getAttribute("P_BRCH_CD")));
		   //dataHm.put("USER_ID", String.valueOf(session.getAttribute("P_CUST_CD")));
		   //dataHm.put("param_mid", param_mid);
		   //log.info("회원가입 처리 후 응답 처리"+dataHm);
		   //cmmnService.update("payment.updateWpayUserKeyTemporary", dataHm);
		   //paymentService.insertWorkIngPayment(dataHm);

	       return mv;
	   }
	   
	   /**
	    * 간편결재 POPUP 호출(회원가입호출 return)
	    * <strong>작성자 : </strong>abj1<br>
	    * <strong>작성일 : </strong>2023. 02. 20.오전 9:37:07<br>
	    * 설명 : 직접입력 <br>
	    *
	    * @version 1.0
	    * @author  abj1
	    * @since   2017. 11. 9.
	    * @param   파라미터타입_직접입력  파라미터설명_직접입력
	    * @return  ModelAndView 리턴에대한설명_직접입력
	    */
	   @RequestMapping(value = "wpaystdMemRegReturn")
	   public ModelAndView wpaystdMemRegReturn(HttpServletRequest request, ModelMap model) throws Exception {
		   //HttpSession session = request.getSession();
		   log.info("\n *********** 간편결재 POPUP [wpaystdMemRegReturn] (WEB ORDER SYSTEM) ***********");
	       ModelAndView mv = new ModelAndView("/payment/wpaystdMemRegReturn");

	       HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
	       log.info("wpaystdMemRegReturn request:"+reqMap);

	       //HashMap<String, Object> dataHm = new HashMap<String, Object> ();
	       //HashMap<String, Object> dataHm2 = new HashMap<String, Object> ();

//	       String brchCd    = String.valueOf(session.getAttribute("P_BRCH_CD"));
//	       String custCd    = String.valueOf(session.getAttribute("P_CUST_CD"));
//
//	       if(custCd == "null") {
//	    	   List<Object> list = cmmnService.selectList("payment.selectWpayUserKeyTemporary", reqMap);
//	   			if (list.size() > 0) {
//					HashMap<String, String> custInfo = (HashMap<String, String>) list.get(0);
//
//					reqMap.put("custCd",  custInfo.get("CUST_CD"));
//					reqMap.put("CUST_CD", custInfo.get("CUST_CD"));
//					reqMap.put("BRCH_CD", custInfo.get("BRCH_CD"));
//				}else {
//					throw new Exception("회원가입중인 거래처정보가 존재하지않습니다.");
//				}
//	       }else {
//	    	   reqMap.put("custCd",  custCd);
//	    	   reqMap.put("CUST_CD", custCd);
//	    	   reqMap.put("BRCH_CD", brchCd);
//	       }

	       
	       //System.out.println("hm>>>>>20241202 : " + reqMap);
	       //System.out.println("SESSION>>>>>>>>20241202"+session.getId() + ", " + session.getAttribute("loginMember"));
	       //System.out.println("CUST_CD : "+ session.getAttribute("CUST_CD"));
	       //System.out.println("P_CUST_CD : "+ session.getAttribute("P_CUST_CD"));
	       //System.out.println("BRCH_CD : "+ session.getAttribute("BRCH_CD"));
	       //System.out.println("P_BRCH_CD : "+ session.getAttribute("P_BRCH_CD"));
	       //System.out.println("TEST_ID : "+ session.getAttribute("TEST_ID"));

	       HashMap<String, Object> payMap = getPayMap(reqMap);
	       HashMap<String, Object> payInfo = (HashMap<String, Object>) payMap.get("payInfo");

	       //이니시스 백제약품 KEY
//	       String g_HASHKEY = String.valueOf(((HashMap<String,Object>)hm.get("GSS")).get("g_HASHKEY"));
//	       String g_SEEDKEY = String.valueOf(((HashMap<String,Object>)hm.get("GSS")).get("g_SEEDKEY"));
//	       String g_SEEDIV  = String.valueOf(((HashMap<String,Object>)hm.get("GSS")).get("g_SEEDIV"));
	       String g_HASHKEY   = String.valueOf(payMap.get("g_HASHKEY"));
	       String g_SEEDKEY   = String.valueOf(payMap.get("g_SEEDKEY"));
	       String g_SEEDIV    = String.valueOf(payMap.get("g_SEEDIV"));
	       
	       //System.out.println("wpaystdMemRegReturn --------------------------------------------");
	       //System.out.println("g_HASHKEY : " + g_HASHKEY);
	       //System.out.println("g_SEEDKEY : " + g_SEEDKEY);
	       //System.out.println("g_SEEDIV : " + g_SEEDIV);
	       
	       //String g_CARD_GB = String.valueOf(payMap.get("g_CARDCODE"));
	       //String g_AMT     = String.valueOf(session.getAttribute("P_AMT"));
	       //log.info("회원가입쪽 session-20241202 g_CARD_GB >>>>>>>>>>>>>>>>>>>>",g_CARD_GB);
	       //log.info("회원가입쪽 session-20241202 g_AMT >>>>>>>>>>>>>>>>>>>>",g_AMT);
//	       log.info("회원가입쪽 session-20241202 brchCd >>>>>>>>>>>>>>>>>>>>",brchCd);
//	       log.info("회원가입쪽 session-20241202 custCd >>>>>>>>>>>>>>>>>>>>",custCd);
	       java.util.Map<String,String> paramMap = new java.util.Hashtable<String,String>();
	   	   java.util.Enumeration elems = request.getParameterNames();
	   	   
	   	   String temp = "";
	   	
	   	   while(elems.hasMoreElements())
	   	   {
	   	       temp = (String) elems.nextElement();
	   	       paramMap.put(temp, request.getParameter(temp));
	   	       //System.out.println("temp>>>>>>>>>>@@@@@@@@/"+temp + "/"+ request.getParameter(temp));
	   	
	   	   }
	   	   
	   	   String param_resultCode 	    = paramMap.get("resultCode");	// 결과코드
	   	   String param_resultMsg 		= paramMap.get("resultMsg");	// 결과메세지 - (URL Encoding 대상필드)
	   	   String param_mid 			= paramMap.get("mid");			// 가맹점 ID
	   	   String param_wtid 			= paramMap.get("wtid");			// WPAY 트랜잭션 ID(이니시스에서 생성)
	   	   String param_userId 		    = paramMap.get("userId");		// 가맹점 유저ID(Request userId 필드 데이터) - (SEED 암호화 대상필드)
	   	   String param_wpayUserKey 	= paramMap.get("wpayUserKey");	// 이니시스에서 발행한 wpayUserKey  - (SEED 암호화 대상필드)
	   	   String param_ci 			    = paramMap.get("ci");			// 가맹점 고객의 ci  - (SEED 암호화 대상필드)
	   	   String param_signature 		= paramMap.get("signature");	// Hash Value
	   	   //System.out.println("param_userId@@@@@@@@@@@@@@@@@@@20241202"+ " /// " + param_userId);
	       //-------------------------------------------------------------
	   	   // 3. 결과 처리
	   	   //-------------------------------------------------------------
	   	   WpayStdSample wpayStdSample = new WpayStdSample();
	   	   
	   	   String srcStr = "";
	   	   String signature = "";
	   	   
	   	   // URL Decoding 처리
		   param_resultMsg = java.net.URLDecoder.decode(param_resultMsg, "UTF-8");

			model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
			model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
			model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));

	       //param_wtid(테스트해서 입력할지 구분), param_wpayUserKey DB 입력
	   	   if("0000".equals(param_resultCode) || "2006".equals(param_resultCode)) {
	   	   //if("2006".equals(param_resultCode)) {
	   	       //dataHm = new HashMap<String, Object>();
	   	       param_wpayUserKey     = wpayStdSample.seedDecrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);
	   	       model.addAttribute("param_mid", param_mid);
	   	       model.addAttribute("param_wpayUserKey", param_wpayUserKey);
	   	       model.addAttribute("param_ci", param_ci);
	   	       model.addAttribute("param_resultCode", "0000");
	   	       model.addAttribute("param_resultMsg", param_resultMsg);
//	   	       if(custCd == "null") {
//	   	    	   dataHm.put("param_mid",param_mid);
//	   	    	   List<Object> list = cmmnService.selectList("payment.selectWpayUserKeyTemporary", dataHm);
//		   			if (list.size() > 0) {
//						HashMap<String, String> custInfo = (HashMap<String, String>) list.get(0);
//	
//						dataHm.put("CUST_CD", custInfo.get("CUST_CD"));
//						dataHm.put("BRCH_CD", custInfo.get("BRCH_CD"));
//					}else {
//						throw new Exception("회원가입중인 거래처정보가 존재하지않습니다.");
//					}
//	   	       }else {
//	   	    	dataHm.put("CUST_CD", custCd);
//				dataHm.put("BRCH_CD", brchCd);
//	   	       }
	   	       reqMap.put("WPAY_USER_KEY", param_wpayUserKey);
	   	     
	   	       paymentService.updateWpayUserKeyPayment(reqMap);
            
	   	   } else {
	   	       model.addAttribute("param_resultCode", param_resultCode);   
	   	       model.addAttribute("param_resultMsg", param_resultMsg); 
	   	   }
	   	   //String strURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	   	   //strURL = strURL + request.getRequestURI();
//	   	   dataHm2 = new HashMap<String, Object>();
//	   	   dataHm2.put("PROCTYPE", "2");//1:가입,2:가입return,3:요청,4:승인
//	   	   dataHm2.put("REQUESTURL", strURL);
//	   	   dataHm2.put("FORWARDURL", "");
//	   	   dataHm2.put("CARDTYPE", g_CARD_GB);
//		   dataHm2.put("TRANAMT", g_AMT);
//		   dataHm2.put("BRCH_CD",dataHm.get("BRCH_CD"));
//		   dataHm2.put("CUST_CD",dataHm.get("CUST_CD"));
//		   dataHm2.put("USER_ID",dataHm.get("CUST_CD"));
		   //log.info("간편결제 결과저장 (회원가입 호출 결과 처리 : T_CREDITCARD_LOG)"+dataHm2);
		   //paymentService.insertWorkIngPayment(dataHm2);
	 
	       return mv;
	   }
	

	/**
	 * 간편결제 카드등록 POPUP 호출(wpaystdPayAuthCardRequest에서 회원가입 이후 카드등록화면 호출을 위한 Service)
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wpaystdCardRegRequest")
	public ModelAndView wpaystdCardRegRequest(HttpServletRequest request, ModelMap model, HashMap<String, Object> resMap) throws Exception {

		WpayStdSample wpayStdSample = new WpayStdSample();

		
		// log.info("\n *********** 간편결재 POPUP [WPAY 표준 결제정보(신용카드) 등록] (WEB ORDER
		// SYSTEM) ***********");
		ModelAndView mv = new ModelAndView("/payment/wpaystdCardRegRequest");

		HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
		log.info("wpaystdCardRegRequest request:"+reqMap);
		
		// wpayUserKey 조회하기		
		List<Object> getWpayUserKey = cmmnService.selectList("payment.selectWpayUserKey", reqMap);
		if (getWpayUserKey.size() > 0) {
			HashMap<String, String> sNdvo = (HashMap<String, String>) getWpayUserKey.get(0);

			resMap.put("g_requestDomain" , sNdvo.get("REQUEST_DOMAIN"));
			resMap.put("CUST_CD" , sNdvo.get("CUST_CD"));
			resMap.put("BRCH_CD" , sNdvo.get("BRCH_CD"));
			resMap.put("USER_ID" , sNdvo.get("CUST_CD"));
			resMap.put("g_HASHKEY" , sNdvo.get("HASH_KEY"));
			resMap.put("g_SEEDKEY" , sNdvo.get("SEED_KEY"));
			resMap.put("g_SEEDIV" , sNdvo.get("SEED_IV"));
			resMap.put("g_MID" , sNdvo.get("MID"));
			resMap.put("g_WPAYUSERKEY" , sNdvo.get("WPAY_USER_KEY"));
			
		}
		
		String targetUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		targetUrl = targetUrl + "/payment";

		
		String requestDomain = String.valueOf(resMap.get("g_requestDomain")); // 
		String requestURL = requestDomain + "/stdwpay/std/u/v1/payreg/card";
		String g_HASHKEY = String.valueOf(resMap.get("g_HASHKEY"));
		String g_SEEDKEY = String.valueOf(resMap.get("g_SEEDKEY"));
		String g_SEEDIV = String.valueOf(resMap.get("g_SEEDIV"));
		String param_mid = String.valueOf(resMap.get("g_MID")); // [필수] 가맹점 ID
		String param_wpayUserKey = String.valueOf(resMap.get("g_WPAYUSERKEY"));// [필수] 이니시스에서 발행한 wpayUserKey - (SEED 암호화 대상필드)
		String strCurrentDomain = targetUrl;
		String param_ci = String.valueOf(resMap.get("ci")); // [옵션] 고객의 CI - (SEED 암호화 대상필드)
		String param_returnUrl = String.valueOf(resMap.get("returnUrl")); // [필수] 회원가입 결과전달 URL - (URL Encoding 대상필드)

		// signature 파라미터
		String param_signature = "";

		param_returnUrl = strCurrentDomain + "/wpaystdCardRegReturn"
		                + "?custCd="+String.valueOf(reqMap.get("custCd"))
		                + "&cardGb="+String.valueOf(reqMap.get("cardGb"))
		                + "&amountTi="+String.valueOf(reqMap.get("amountTi"))
		                ;
		
		
		log.info("wpaystdCardRegRequest param_returnUrl:"+param_returnUrl);
		log.info("wpaystdCardRegRequest reqMap:"+resMap);

		// Seed 암호화
		param_wpayUserKey = wpayStdSample.seedEncrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);

		// URL Encoding
		param_returnUrl = java.net.URLEncoder.encode(param_returnUrl, "UTF-8");

		param_ci = "";
		// -------------------------------------------------------
		// 3. 위변조 방지체크를 위한 signature 생성
		// (순서주의:연동규약서 참고)
		// -------------------------------------------------------
		String srcStr = "";

		srcStr = "mid=" + param_mid;
		srcStr = srcStr + "&wpayUserKey=" + param_wpayUserKey;
		srcStr = srcStr + "&ci=" + param_ci;
		srcStr = srcStr + "&returnUrl=" + param_returnUrl;
		srcStr = srcStr + "&hashKey=" + g_HASHKEY;
		// log.info("srcStr:"+srcStr);
		param_signature = wpayStdSample.encrypteSHA256(srcStr);
		// log.info("param_signature:"+param_signature);

		model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
		model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
		model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));

		model.addAttribute("param_mid", param_mid);
		model.addAttribute("param_wpayUserKey", param_wpayUserKey);
		model.addAttribute("param_ci", param_ci);
		model.addAttribute("param_returnUrl", param_returnUrl);
		model.addAttribute("param_signature", param_signature);
		model.addAttribute("requestURL", requestURL);

		model.addAttribute("param_mid", param_mid);
		model.addAttribute("param_wpayUserKey", param_wpayUserKey);
		model.addAttribute("param_ci", param_ci);
		model.addAttribute("param_returnUrl", param_returnUrl);
		model.addAttribute("param_signature", param_signature);
		model.addAttribute("requestURL", requestURL);

		return mv;
	}
	
	/**
	 * 간편결재 POPUP 호출(WPAY 표준 결제정보(신용카드) 등록) return
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wpaystdCardRegReturn")
	public ModelAndView wpaystdCardRegReturn(HttpServletRequest request, ModelMap model) throws Exception {

		WpayStdSample wpayStdSample = new WpayStdSample();
		
		ModelAndView mv = new ModelAndView("/payment/wpaystdCardRegReturn");
		
		HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
		log.info("wpaystdCardRegReturn reqMap:"+reqMap);
		java.util.Map<String,String> paramMap = new java.util.Hashtable<String,String>();
		java.util.Enumeration elems = request.getParameterNames();
		
		HashMap<String, Object> payMap = getPayMap(reqMap);
		
		//이니시스 백제약품 KEY
		String g_HASHKEY = String.valueOf(payMap.get("g_HASHKEY"));
		String g_SEEDKEY = String.valueOf(payMap.get("g_SEEDKEY"));
		String g_SEEDIV  = String.valueOf(payMap.get("g_SEEDIV"));

		String temp = "";
		
		while(elems.hasMoreElements()) {
			temp = (String) elems.nextElement();
			paramMap.put(temp, request.getParameter(temp));
		}
	       
		String param_resultCode 	= paramMap.get("resultCode");		// 결과코드
		String param_resultMsg 		= paramMap.get("resultMsg");		// 결과메세지 - (URL Encoding 대상필드)
		String param_mid 			= paramMap.get("mid");				// WPAY 트랜잭션 ID(이니시스에서 생성)
		String param_wtid 			= paramMap.get("wtid");				// WPAY 트랜잭션 ID(이니시스에서 생성)
		String param_wpayUserKey 	= paramMap.get("wpayUserKey");		// 이니시스에서 발행한 wpayUserKey  - (SEED 암호화 대상필드)
		String param_wpayToken 		= paramMap.get("wpayToken");		// 이니시스에서 발행한 wpayToken  - (SEED 암호화 대상필드)
		String param_payMethod 		= paramMap.get("payMethod");		// 이니시스에서 발행한 payMethod
		String param_bankCardCode	= paramMap.get("bankCardCode");		// 이니시스에서 발행한 bankCardCode
		String param_bankCardNo		= paramMap.get("bankCardNo");		// 이니시스에서 발행한 bankCardNo
		String param_cardName		= paramMap.get("cardName");			// 이니시스에서 발행한 cardName - (URL Encoding 대상필드)
		String param_cardIsscoCode	= paramMap.get("cardIsscoCode");	// 이니시스에서 발행한 cardIsscoCode
		String param_checkFlg		= paramMap.get("checkFlg");			// 이니시스에서 발행한 checkFlg
		String param_cardTypeFlg	= paramMap.get("cardTypeFlg");		// 이니시스에서 발행한 cardTypeFlg
		String param_cardPdNum		= paramMap.get("cardPdNum");		// 이니시스에서 발행한 cardPdNum
		String param_cardBImgUrl  	= paramMap.get("cardBImgUrl");		// 이니시스에서 발행한 cardBImgUrl - (URL Encoding 대상필드)
		String param_wpayTokenNcnm	= paramMap.get("wpayTokenNcnm");	// 이니시스에서 발행한 wpayTokenNcnm - (URL Encoding 대상필드)
		String param_signature 		= paramMap.get("signature");		// Hash Value
		   
		// URL Decoding 처리
		param_resultMsg = java.net.URLDecoder.decode(param_resultMsg, "UTF-8");
		   
		log.info("param_resultMsg:"+param_resultMsg);
//		log.info("paramMap.toString():"+paramMap.toString());

		param_wpayUserKey 	= wpayStdSample.seedDecrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);

		model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
		model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
		model.addAttribute("amountTi", String.valueOf(reqMap.get("amountTi")));

		model.addAttribute("param_resultCode", param_resultCode);		
		model.addAttribute("param_resultMsg", param_resultMsg);		

		//param_wtid(테스트해서 입력할지 구분), param_wpayUserKey DB 입력
		if("0000".equals(param_resultCode) || "7028".equals(param_resultCode)) {
	   	       model.addAttribute("param_resultCode", "0000");
	   	       model.addAttribute("param_resultMsg", param_resultMsg);
		} else {
	   	       model.addAttribute("param_resultCode", param_resultCode);
	   	       model.addAttribute("param_resultMsg", param_resultMsg);
		}
		
		return mv;
	}
	
	/**
	 * 간편결재 POPUP에 결제요청 Return
	 * 
	 * @param hMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wpaystdPayAuthCardReturn")
	public ModelAndView wpaystdPayAuthCardReturn(HttpServletRequest request, ModelMap model) throws Exception {
		   
//		log.info("\n *********** 간편결제 POPUP [wpaystdPayAuthCardReturn] (WEB ORDER SYSTEM) ***********");
		ModelAndView mv = new ModelAndView("/payment/wpaystdPayAuthCardReturn");
	       
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String sysTime = dayTime.format(new Date(time)); //주문번호 가맹점에서 생성한 유일한 번호
		SimpleDateFormat dayTimeSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String moid = dayTimeSSS.format(new Date(time)); //주문번호 가맹점에서 생성한 유일한 번호
	 	   
		HashMap<String, Object> reqMap = CmmnUtil.getParameters(request);
		log.info("wpaystdPayAuthCardReturn reqMap:"+reqMap);
		java.util.Map<String,String> paramMap = new java.util.Hashtable<String,String>();
		java.util.Enumeration elems = request.getParameterNames();   
		String temp = "";
	   	
		while(elems.hasMoreElements()) {
			temp = (String) elems.nextElement();
			paramMap.put(temp, request.getParameter(temp));
		}
	   	   
		String param_resultCode 	= paramMap.get("resultCode");		// 결과코드
		String param_resultMsg	= paramMap.get("resultMsg");		// 결과메세지 - (URL Encoding 대상필드)
		String param_mid 		= paramMap.get("mid");				// WPAY 트랜잭션 ID(이니시스에서 생성)
		String param_wtid 		= paramMap.get("wtid");				// WPAY 트랜잭션 ID(이니시스에서 생성)
		String param_oid 		= paramMap.get("oid");				// WPAY 트랜잭션 ID(이니시스에서 생성)
		String param_wpayUserKey = paramMap.get("wpayUserKey");		// 이니시스에서 발행한 wpayUserKey  - (SEED 암호화 대상필드)
		String param_wpayToken 	= paramMap.get("wpayToken");		// 이니시스에서 발행한 wpayToken  - (SEED 암호화 대상필드)
		String param_wpayDomain 	= paramMap.get("wpayDomain");		// 이니시스에서 발행한 wpayToken  - (SEED 암호화 대상필드)
		String param_signature 	= paramMap.get("signature");		// Hash Value
		   
		String param_payMethod 	= paramMap.get("payMethod");		// 결제수단(01:신용카드)
	       
		//-------------------------------------------------------------
		// 3. 결과 처리
		//-------------------------------------------------------------
		WpayStdSample wpayStdSample = new WpayStdSample();

		String srcStr = "";
	   	   
		// URL Decoding 처리
		param_resultMsg = java.net.URLDecoder.decode(param_resultMsg, "UTF-8");
	   	   
		String TRANAMT = "";
		String FORWORDURL = "";
		   
		HashMap<String, Object> dataHm = new HashMap<String, Object> ();
		   
		//T_CREDITCARD_LOG 입력
		//HashMap<String, Object> dataHm2 = new HashMap<String, Object> ();
		   
	       
		//return 처리
		if("0000".equals(param_resultCode)) {
			
			HashMap<String, Object> payMap = getPayMap(reqMap);
			
			//이니시스 백제약품 KEY
			String g_HASHKEY = String.valueOf(payMap.get("g_HASHKEY"));
			String g_SEEDKEY = String.valueOf(payMap.get("g_SEEDKEY"));
			String g_SEEDIV  = String.valueOf(payMap.get("g_SEEDIV"));
			//String g_amountTi = String.valueOf(payMap.get("g_amountTi"));
			String g_CARDCODE = String.valueOf(payMap.get("g_CARDCODE"));
			
			dataHm.put("CUST_CD",String.valueOf(payMap.get("CUST_CD")));
			dataHm.put("BRCH_CD",String.valueOf(payMap.get("BRCH_CD")));
			dataHm.put("USER_ID",String.valueOf(payMap.get("CUST_CD")));
			
			//param_wpayUserKey 	= wpayStdSample.seedEncrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);
			srcStr = "";
			srcStr = srcStr + "mid="+param_mid;
			srcStr = srcStr + "&wpayUserKey="+param_wpayUserKey;
			srcStr = srcStr + "&ci=";
			srcStr = srcStr + "&wtid="+param_wtid;
			srcStr = srcStr + "&hashKey=" + g_HASHKEY;   		   
			param_signature = wpayStdSample.encrypteSHA256(srcStr);
			   
//			log.info("srcStr:"+srcStr);
			   
			String sendParam = "mid="+param_mid;
			sendParam += "&wpayUserKey="+java.net.URLEncoder.encode(param_wpayUserKey, "UTF-8");
			sendParam += "&ci=";
			sendParam += "&wtid="+param_wtid;
			sendParam += "&tax=";
			sendParam += "&taxFree=";
			sendParam += "&signature="+param_signature;

//			log.info("sendParam:"+sendParam);

			String requestDomain     = String.valueOf(payMap.get("g_requestDomain")); //상용 Domain
			String requestURL = requestDomain + "/stdwpay/std/rest/v1/payappl/card";
			FORWORDURL = requestURL;

			String rspStr =  HTTPClientByPost(requestURL, sendParam, "application/x-www-form-urlencoded", "utf-8");
//			log.info("HTTPClientByPost result : "+rspStr);
			HashMap<String, Object> recvMap = new HashMap<String, Object> ();
			recvMap = ever.support.net.RestUtil.jsonStrToMap(rspStr);
		       
			String resultCode2      = String.valueOf(recvMap.get("resultCode"));
			String resultMsg2       = String.valueOf(recvMap.get("resultMsg"));
			resultMsg2              = java.net.URLDecoder.decode(resultMsg2, "UTF-8");
			String CARDNO           = String.valueOf(recvMap.get("applCardNum"));      //승인카드번호
		    TRANAMT                 = String.valueOf(recvMap.get("applPrice"));        //승인금액
		    String CARDCODE         = String.valueOf(recvMap.get("bankCardCode"));     //카드사코드
		    String APPROVEDATE      = String.valueOf(recvMap.get("applDate"));         //승인일시
		    String APPROVENO        = String.valueOf(recvMap.get("applNum"));          //승인번호
		    String APPROVEINS       = String.valueOf(recvMap.get("cardQuota"));        //할부개월수
		       
			if (!("0000".equals(resultCode2))) {
				CARDNO = "";
				TRANAMT = "0";
				CARDCODE = "";
				APPROVEDATE = "";
				APPROVENO = "";
		       }
		       
			param_wpayUserKey     = wpayStdSample.seedDecrypt(param_wpayUserKey, g_SEEDKEY, g_SEEDIV);
//			log.info("param_wpayUserKey:"+param_wpayUserKey);
	   	       
			model.addAttribute("custCd", String.valueOf(reqMap.get("custCd")));
			model.addAttribute("cardGb", String.valueOf(reqMap.get("cardGb")));
			model.addAttribute("param_mid", param_mid);
			model.addAttribute("param_wpayUserKey", param_wpayUserKey);
			model.addAttribute("param_wtid", param_wtid); 
			model.addAttribute("param_resultCode", "0000");   
			model.addAttribute("param_resultMsg", param_resultMsg);

			dataHm.put("RETCODE", "0000");
			dataHm.put("RETMSG", param_resultMsg);
			dataHm.put("TID", param_wtid);
			dataHm.put("ORDERNO", param_oid.substring(0,14));
			dataHm.put("ORDERDATE", sysTime.substring(0,8));
			dataHm.put("ORDERTIME", sysTime.substring(8));
			dataHm.put("TRANAMT", TRANAMT);
			dataHm.put("CARDNO", CARDNO);
			dataHm.put("INS", "");
			dataHm.put("TRADENO", "");
			dataHm.put("APPROVENO", APPROVENO);
			dataHm.put("APRV_INS", APPROVEINS); //할부개월수
			dataHm.put("APPROVEDATE", APPROVEDATE);
			dataHm.put("CARDCODE", CARDCODE);
			dataHm.put("CARDCODEM", g_CARDCODE);
			if ("01".equals(g_CARDCODE)) {
				dataHm.put("CARDNAME", "간편결제(신한)");
				dataHm.put("CARDNAMEM", "간편결제(신한)");	   
			} else if ("02".equals(g_CARDCODE)) {
				dataHm.put("CARDNAME", "간편결제(삼성,현대)");
				dataHm.put("CARDNAMEM", "간편결제(삼성,현대)");
			}
			   
			//T_CREDITCARD_LOG 입력
			String strURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			strURL = strURL + request.getRequestURI();
			//dataHm2.put("PROCTYPE", "4");//1:가입,2:가입return,3:요청,4:승인
			//dataHm2.put("REQUESTURL", strURL);
			//dataHm2.put("FORWARDURL", FORWORDURL);
			//dataHm2.put("CARDTYPE", g_CARDCODE);
			//dataHm2.put("TRANAMT", TRANAMT);
			
			//HttpSession session = request.getSession();
			//dataHm.put("BRCH_CD",String.valueOf(session.getAttribute("P_BRCH_CD")));  
			//dataHm.put("CUST_CD",String.valueOf(session.getAttribute("P_CUST_CD")));
			//dataHm.put("USER_ID",String.valueOf(session.getAttribute("P_CUST_CD")));
			//dataHm2.put("BRCH_CD",String.valueOf(session.getAttribute("P_BRCH_CD")));
			//dataHm2.put("CUST_CD",String.valueOf(session.getAttribute("P_CUST_CD")));
			//dataHm2.put("USER_ID",String.valueOf(session.getAttribute("P_CUST_CD")));
			
			
			//if ("null".equals(dataHm2.get("CUST_CD").toString())) {
			//	log.info("세션 정보 없어서 (WpayUserKey)로 거래처 정보 조회 할 경우 ");
			//	dataHm2.put("wpayUserKey",param_wpayUserKey);
			//	List<Object> getWpayUserKey = cmmnService.selectList("payment.selectByWpayUserKey", dataHm2);
			//	if (getWpayUserKey.size() > 0) {
			//		HashMap<String, String> sNdvo = (HashMap<String, String>) getWpayUserKey.get(0);
			//		dataHm.put("CUST_CD" , sNdvo.get("CUST_CD"));
			//		dataHm.put("BRCH_CD" , sNdvo.get("BRCH_CD"));
			//		dataHm.put("USER_ID" , sNdvo.get("CUST_CD"));
					//dataHm2.put("CUST_CD" , sNdvo.get("CUST_CD"));
					//dataHm2.put("BRCH_CD" , sNdvo.get("BRCH_CD"));
					//dataHm2.put("USER_ID" , sNdvo.get("CUST_CD"));
			//	}
			//}
			log.info("최종 결제 시 저장 로그 (T_CREDITCARD에 저장):"+dataHm);
			paymentService.insertResultPayment(dataHm);
		   	//paymentService.insertWorkIngPayment(dataHm2);
			   
		} else {
			model.addAttribute("param_resultCode", param_resultCode);   
			model.addAttribute("param_resultMsg", param_resultMsg);
	   	     
			//dataHm2.put("MID", param_mid);
			//dataHm2.put("WTID", param_wtid);
			//dataHm2.put("RESULT_CODE", param_resultCode);
			//dataHm2.put("RESULT_MSG", param_resultMsg);
			//paymentService.insertWebWpayError(dataHm2);
		}
	   	return mv;
	}
	


		@ResponseBody
		private HashMap<String, Object> getPayMap(HashMap<String, Object> hMap) throws Exception {
			HashMap<String, Object> payMap = new HashMap<>();
			// wpayUserKey 조회하기
			List<Object> getPayData = new ArrayList<>();
			if (hMap.get("custCd") != null && !"".equals(hMap.get("custCd"))) {
				getPayData = cmmnService.selectList("payment.selectWpayUserKey", hMap);
			} else {
				HashMap<String, String> getPayInfo = (HashMap<String, String>)cmmnService.select("payment.selectWpayInfoByMid", hMap);
				payMap.put("payInfo",getPayInfo);
				WpayStdSample wpayStdSample = new WpayStdSample();
				String wpayUserKey 	= wpayStdSample.seedDecrypt(hMap.get("wpayUserKey").toString(), getPayInfo.get("SEED_KEY"), getPayInfo.get("SEED_IV"));
				hMap.put("wpayUserKey",wpayUserKey);
				hMap.put("IN9_CODE",getPayInfo.get("CARDCODE"));
				getPayData = cmmnService.selectList("payment.selectByWpayUserKey", hMap);
			}
			
			if (getPayData.size() > 0) {
				HashMap<String, String> sNdvo = (HashMap<String, String>) getPayData.get(0);

				// as-is GSS 정보
				payMap.put("g_HASHKEY" , sNdvo.get("HASH_KEY"));
				payMap.put("g_SEEDKEY" , sNdvo.get("SEED_KEY"));
				payMap.put("g_SEEDIV" , sNdvo.get("SEED_IV"));
				payMap.put("g_MID" , sNdvo.get("MID"));
				payMap.put("g_WPAYUSERKEY" , sNdvo.get("WPAY_USER_KEY"));
				payMap.put("g_requestDomain" , sNdvo.get("REQUEST_DOMAIN"));
				payMap.put("g_CARDCODE" , sNdvo.get("CARDCODE"));// 간편결제(신한):01,간편결재(삼성,현대):02
				payMap.put("CUST_CD" , sNdvo.get("CUST_CD"));
				payMap.put("BRCH_CD" , sNdvo.get("BRCH_CD"));
				payMap.put("USER_ID" , sNdvo.get("CUST_CD"));
				payMap.put("EMP_ID" , sNdvo.get("EMP_ID"));
			}

			return payMap;
		}

		public String HTTPClientByPost(String address, String senData, String contentType, String encoding) {
			String result = "";
			String encodingType = "UTF-8";
			if(StringUtils.isNotEmpty(encoding)) {
				encodingType = encoding;
			}
			HttpURLConnection httpurlconnection = null;
			try {
				URL url = new URL(address);
				StringBuffer stringbuffer = new StringBuffer();
				httpurlconnection = (HttpURLConnection) url.openConnection();
				httpurlconnection.setDoOutput(true);
				httpurlconnection.setRequestMethod("POST");
				httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0");
				if(StringUtils.isNotEmpty(contentType)) {
					httpurlconnection.setRequestProperty("Content-Type", contentType+"; charset="+encodingType);
				}
				BufferedWriter bufferdWriter = null;
				try {
					if(StringUtils.isNotEmpty(encoding)) {
						bufferdWriter = new BufferedWriter(new OutputStreamWriter(httpurlconnection.getOutputStream(), encodingType));
					}else {
						bufferdWriter = new BufferedWriter(new OutputStreamWriter(httpurlconnection.getOutputStream()));
					}
					bufferdWriter.write(senData);
					bufferdWriter.flush();
				} catch (Exception e) {
					log.error(e.getMessage());
				} finally {
		            if (bufferdWriter != null) {
		                try {
		                    bufferdWriter.close();
		                } catch (IOException e) {
		                	log.error(e.getMessage());
		                }
		            }
				}
				if (httpurlconnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader bufferedreader = null;
					try {
						if(StringUtils.isNotEmpty(encoding)) {
							bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), encodingType));
						}else {
							bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
						}
						int i;
						while ((i = bufferedreader.read()) != -1) {
							stringbuffer.append((char) i);
						}
						result = stringbuffer.toString();
					}catch(UnsupportedEncodingException e) {
						log.error(e.getMessage());
					}catch(IOException e) {
						log.error(e.getMessage());
					}finally {
						if(bufferedreader != null) {
							bufferedreader.close();
						}
					}
				} else {
					result = "";
				}
				httpurlconnection.disconnect();
			} catch (Exception e) {
				log.error(e.getMessage());
				if (httpurlconnection != null) {
					httpurlconnection.disconnect();
				}
				result = "";
			}
			return result;
		}

}
