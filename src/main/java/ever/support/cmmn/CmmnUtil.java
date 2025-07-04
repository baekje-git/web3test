package ever.support.cmmn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ever.support.jackson.CustomObjectMapper;
import ever.support.security.module.bcrypt.BcryptCipher;
import ever.support.security.module.hmac.HMACCipher;
import ever.support.security.module.pbkdf2.PBKDF2Cipher;
import ever.support.security.module.sha.SHACipher;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmmnUtil {
	private static final Logger logger = LoggerFactory.getLogger(CmmnUtil.class);

	public static final Pattern SCRIPTS = Pattern.compile("<(no)?SCRIPT[^>]*>.*?</(no)?SCRIPT>", 2);

	public static final Pattern STYLE = Pattern.compile("<STYLE[^>]*>.*</STYLE>", 2);

	public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");

	public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");

	public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");

	public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");

	public static void methodLog(String text) {
		logger.debug(
				"\n:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n>>> " +

						text + "\n"
						+ ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}

	public static String removeHtmlTag(String s) {
		if (s == null)
			return null;
		Matcher m = SCRIPTS.matcher(s);
		s = m.replaceAll("");
		m = STYLE.matcher(s);
		s = m.replaceAll("");
		m = ENTITY_REFS.matcher(s);
		s = m.replaceAll("");
		return s;
	}

	public static HashMap<String, Object> resultMsg(String Code, String Message) {
		HashMap<String, Object> codeMap = new HashMap<>();
		codeMap.put("CODE", Code);
		codeMap.put("MESSAGE", Message);
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("RESULT", codeMap);
		return resultMap;
	}
	
	public static HashMap<String, Object> resultMsg(String Code, String Message, Object Data) {
		HashMap<String, Object> codeMap = new HashMap<>();
		codeMap.put("CODE", Code);
		codeMap.put("MESSAGE", Message);
		codeMap.put("DATA", Data);
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("RESULT", codeMap);
		return resultMap;
	}
	
	public static HashMap<String, Object> resultErrMsg(String key, Object Message) {		
		HashMap<String, Object> errMap = new HashMap<>();		
		errMap.put(key, Message);		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("error", errMap);
		return resultMap;
	}

	public static HashMap<String, Object> getParameters(HttpServletRequest request) {
		//session객체를 사용안함.
        //HashMap<String, Object> pMap = new GSSMap<String, Object>();
    	
        HashMap<String, Object> pMap = new HashMap<String, Object>();
        Map<String, String[]> map    = request.getParameterMap();
        Iterator<String> iterator    = map.keySet().iterator();
        String key = "";
        if(!map.isEmpty()) {
            while(iterator.hasNext()) {
                key = (String) iterator.next();
                pMap.put(key, removeHtmlTag(request.getParameter(key)));
            }
        }
        
        return pMap;
	}

	public static HashMap<String, Object> parsingToMap(String pCode) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> resultData = new HashMap<>();
		try {
			if ("".equals(StringUtils.defaultString(pCode)))
				return resultData;
			HashMap<String, Object> mapperData = (HashMap<String, Object>) mapper.readValue(pCode,
					new TypeReference<GSSMap<String, Object>>() {

					});
			if (mapperData != null && !mapperData.isEmpty()) {
				Iterator<?> iterator = mapperData.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
					if (entry.getValue() instanceof List) {
						resultData.put(entry.getKey().toString(), mapper.convertValue(entry.getValue(),
								new TypeReference<List<HashMap<String, Object>>>() {

								}));
						continue;
					}
					if (entry.getValue() instanceof Map) {
						resultData.put(entry.getKey().toString(),
								mapper.convertValue(entry.getValue(), new TypeReference<HashMap<String, Object>>() {

								}));
						continue;
					}
					resultData.put(entry.getKey().toString(), entry.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}

	public static List<HashMap<String, Object>> getParamList(HttpServletRequest request, boolean pIsCopy,
			boolean pIsIncSession) {
		List<HashMap<String, Object>> list = new ArrayList<>();
		try {
			int rowCnt = 0;
			int colCnt = 0;
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String[] values = request.getParameterValues(name);
				if (rowCnt < values.length)
					rowCnt = values.length;
				colCnt++;
			}
			int colIdx = 0;
			String[] arrColumns = new String[colCnt];
			String[][] arrParams = new String[colCnt][rowCnt];
			e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String[] values = request.getParameterValues(name);
				for (int i = 0; i < rowCnt; i++) {
					if (i == 0)
						arrColumns[colIdx] = name;
					arrParams[colIdx][i] = (values.length - 1 < i) ? (pIsCopy ? values[values.length - 1] : null)
							: values[i];
				}
				colIdx++;
			}
			HttpSession session = request.getSession(false);
			HashMap<String, Object> smap = new HashMap<>();
			if (pIsIncSession && session != null) {
				Enumeration enu = session.getAttributeNames();
				String key = "";
				while (enu.hasMoreElements()) {
					key = enu.nextElement().toString();
					if ("SS_TOP_MENU".equals(key))
						continue;
					smap.put(key, session.getAttribute(key));
				}
			}
			for (int r = 0; r < rowCnt; r++) {
				HashMap<String, Object> map = new HashMap<>();
				for (int c = 0; c < colCnt; c++)
					map.put(arrColumns[c], arrParams[c][r]);
				if (pIsIncSession)
					map.putAll(smap);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String toJsonString(HttpServletRequest request) {
		CustomObjectMapper mapper = new CustomObjectMapper();
		String paramStr = "";
		try {
			paramStr = mapper.writeValueAsString(request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramStr;
	}

	public static String getEncryptSha(String Id) throws Exception {
		if (Id == null || Id.length() == 0)
			return "";
		String outputStr1 = "";
		try {
			SHACipher shaCipher = new SHACipher();
			outputStr1 = shaCipher.encryptSHA256(Id);
		} catch (Exception e) {
			outputStr1 = Id;
		}
		return outputStr1;
	}

	public static String getEncryptSha(String Id, String Type) throws Exception {
		if (Id == null || Id.length() == 0)
			return "";
		String outputStr1 = "";
		try {
			SHACipher shaCipher = new SHACipher();
			outputStr1 = shaCipher.encryptSHA(Id, Type);
		} catch (Exception e) {
			outputStr1 = Id;
		}
		return outputStr1;
	}

	public static String bycriptHashPw(String Id) throws Exception {
		if (Id == null || Id.length() == 0)
			return "";
		String outputStr1 = "";
		try {
			BcryptCipher bcryptCipher = new BcryptCipher();
			outputStr1 = bcryptCipher.HashPw(Id);
		} catch (Exception e) {
			outputStr1 = Id;
		}
		return outputStr1;
	}

	public static boolean bycriptCheckPw(String Id, String hash) throws Exception {
		if (Id == null || Id.length() == 0)
			return false;
		boolean outputbool = false;
		try {
			BcryptCipher bcryptCipher = new BcryptCipher();
			outputbool = bcryptCipher.checkPw(Id, hash);
		} catch (Exception e) {
			outputbool = false;
		}
		return outputbool;
	}

	public static String getPBKDF2Hash(String Id) throws Exception {
		if (Id == null || Id.length() == 0)
			return "";
		String outputStr1 = "";
		try {
			PBKDF2Cipher pbkdf2Cipher = new PBKDF2Cipher();
			outputStr1 = pbkdf2Cipher.getPBKDF2Hash(Id);
		} catch (Exception e) {
			outputStr1 = Id;
		}
		return outputStr1;
	}

	public static String getRandomString(String type, int length) throws Exception {
		String outputStr1 = "";
		if ("num".equals(type)) {
			outputStr1 = RandomStringUtils.randomNumeric(length);
		} else if ("alpha".equals(type)) {
			outputStr1 = RandomStringUtils.randomAlphabetic(length);
		} else if ("alpha-num".equals(type)) {
			outputStr1 = RandomStringUtils.randomAlphanumeric(length);
		}
		return outputStr1;
	}

	public static String getHmacHash(String value, String sha) throws Exception {
		if (value == null || value.length() == 0)
			return "";
		String outputStr1 = "";
		try {
			HMACCipher hmacCipher = new HMACCipher();
			outputStr1 = hmacCipher.asHex(hmacCipher.hmacSha(value, "27s39r10ix548t4z23639qf38k566zzo", sha));
		} catch (Exception e) {
			outputStr1 = "";
		}
		return outputStr1;
	}

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "uneverwn".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "uneverwn".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "uneverwn".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip == null || ip.length() == 0 || "uneverwn".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip == null || ip.length() == 0 || "uneverwn".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	public static Object mapToObject(Map<String, Object> map, Object obj) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			keyAttribute = itr.next();
			methodString = String.valueOf(setMethodString) + keyAttribute.substring(0, 1).toUpperCase()
					+ keyAttribute.substring(1).toLowerCase();
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methodString.equals(methods[i].getName()))
					try {
						methods[i].invoke(obj, new Object[] { map.get(keyAttribute) });
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		return obj;
	}
}
