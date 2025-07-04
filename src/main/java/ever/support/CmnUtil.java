package ever.support;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CmnUtil {

	private static Logger log = LogManager.getLogger(CmnUtil.class);

	public static String checkNullPoint(String nullString) {
		if(nullString == null || nullString.trim().equals("null")) return "";
		else return nullString.trim();
	}

	/**
	 *@brief null값을 0값으로 반환한다.
	 *@param nullString
	 *@return 입력 문자
	 *@exception 
	 *@see 
	 */
	public static String checkNullToZero(String nullString) {
		if(nullString == null || nullString.trim().equals("null")) return "0";
		else return nullString.trim();
	}

	@SuppressWarnings("rawtypes")
	public static String searchProperties(String propfile, String propName) {
		String strProp = "";
		ResourceBundle resourcebundle = null;
		try {
			resourcebundle = ResourceBundle.getBundle(propfile);
			Enumeration enumeration = resourcebundle.getKeys();
			while(enumeration.hasMoreElements()) {
				String bkey = (String) enumeration.nextElement();
				if(bkey.equals(propName)) {
					strProp = new String(resourcebundle.getString(bkey).getBytes("ISO-8859-1"), "UTF-8");
					break;
				}
			}
		} catch(Exception ex) {
			log.debug("CommUtil.searchProperties Error>>>>>>>\n" + ex.getMessage());
		}
		return strProp;
	}

	@SuppressWarnings("rawtypes")
	public static int searchPropertiesInt(String propfile, String propName, String delim) {
		String strProp = "";
		int nNum = 1;
		ResourceBundle resourcebundle = null;
		try {
			resourcebundle = ResourceBundle.getBundle(propfile);
			Enumeration enumeration = resourcebundle.getKeys();
			while(enumeration.hasMoreElements()) {
				String bkey = (String) enumeration.nextElement();
				if(bkey.equals(propName)) {
					strProp = new String(resourcebundle.getString(bkey).getBytes("ISO-8859-1"), "UTF-8");

					String[] arrProp = strProp.split("\\" + delim);
					for(int i = 0; i < arrProp.length; i++) {
						nNum = nNum * Integer.parseInt(arrProp[i]);
					}

					break;
				}
			}
		} catch(Exception ex) {
			log.debug("CommUtil.searchProperties Error>>>>>>>\n" + ex.getMessage());
		}

		return nNum;
	}

	

	

	

	

	// //파일다운로드 정보 저장
	// public static void saveFileDownCount(HttpServletRequest request, commFm param) throws Exception {
	// ServletContext sc;
	// HttpSession hs;
	// WebApplicationContext wac;
	//
	// try {
	// hs = request.getSession();
	// sc = hs.getServletContext();
	//
	// // ContextLoaderListener으로 로딩된 context를 가져 온다.
	// wac = WebApplicationContextUtils.getWebApplicationContext(sc);
	//
	// commDao comDao = (commDao)wac.getBean("commDao");
	//
	// comDao.saveFileDownCount(request,param);
	//
	// } catch (Exception e) {
	// }
	// }

	// 검색어 중간 처리
	public static String chgSearchWord(String sWord) {
		if(sWord == null) return sWord;

		String re_str = sWord.replaceAll("\"", "\\\\\\\"").replaceAll("\r\n", "\\\\n");

		return re_str;
	}

	public static String chgSearchWordAll(String sWord) {
		if(sWord == null) return sWord;

		String re_str = sWord.replaceAll("\"", "\\\"").replaceAll("\r\n", "\\\\n").replaceAll("(?i) and ", " ^[AND ").replaceAll(" & ", " %26 ")
		    .replaceAll("(?i) or ", " ^[OR ").replaceAll("(?i) not ", " ^[NOT ").replaceAll("(?i) near", " ^[NEAR").replaceAll("(?i) order", " ^[ORDER");

		return re_str;
	}

	/**
	 * <B>History</B>
	 * <ul>
	 * <li>Date : Apr 21, 2013
	 * <li>Developer : pks
	 * <li>Request에서 파라미터를 추출하여 List로 반환
	 * <li>- Request에서 동명의 파라미터가 있는 경우 처리를 위함.
	 * <li>- b항목의 사이즈가 a항목의 사이즈보다 적을 때 b항목의 마지막 값을 a항목 사이즈 만큼 증분되는 레코드에 복사할 지 여부 지정
	 * <li>- 세션값을 리스트에 포함시킬지 여부 지정
	 * </ul>
	 * 
	 * @param request
	 * @param pIsCopy
	 * @param pIsIncSession
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<HashMap<String, Object>> getParamList(HttpServletRequest request, boolean pIsCopy, boolean pIsIncSession) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		try {
			int rowCnt = 0;
			int colCnt = 0;
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String[] values = request.getParameterValues(name);

				if(rowCnt < values.length) rowCnt = values.length;
				colCnt++;
			}

			int colIdx = 0;
			String[] arrColumns = new String[colCnt];
			String[][] arrParams = new String[colCnt][rowCnt];
			e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String[] values = request.getParameterValues(name);

				// log.debug("@@@@@@ " + name + " : " + rowCnt + " = " + values.length);
				for(int i = 0; i < rowCnt; i++) {
					log.debug(">>>> " + i + " = " + name + " : " + ((values.length - 1 < i) ? values[values.length - 1] : values[i]));
					if(0 == i) arrColumns[colIdx] = name;
					arrParams[colIdx][i] = (values.length - 1 < i) ? (pIsCopy ? values[values.length - 1] : null) : values[i];
				}

				colIdx++;
			}

			// 세션값 획득
			HttpSession session = request.getSession(false);
			HashMap<String, Object> smap = new HashMap<String, Object>();
			if(pIsIncSession && null != session) {
				Enumeration enu = session.getAttributeNames();
				String key = "";
				while(enu.hasMoreElements()) {
					key = enu.nextElement().toString();
					if("SS_TOP_MENU".equals(key)) continue;	// top menu는 추출하지 않음
					smap.put(key, session.getAttribute(key));
				}
			}

			// 최종 리턴 리스트에 적재
			for(int r = 0; r < rowCnt; r++) {
				HashMap<String, Object> map = new HashMap<String, Object>();

				for(int c = 0; c < colCnt; c++) {
					map.put(arrColumns[c], arrParams[c][r]);
				}

				// 세션값을 리스트의 각 row(?)마다 복사
				if(pIsIncSession) {
					map.putAll(smap);
				}

				list.add(map);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Object> getParameters(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		Iterator iterator = map.keySet().iterator();
		String key = "";

		if(!map.isEmpty()) {
			while(iterator.hasNext()) {							
				key = (String) iterator.next();
				//CmnUtil.menuLog("KEY:"+key);
				paramMap.put(key, removeHtmlTag(request.getParameter(key)));
			}
		}		
		
		/* 개발 로깅용
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n================================[PARAM-START] \n");
		sb.append("URL ::" + request.getRequestURI() +"\n\n");
		for(Entry<String, Object> e : paramMap.entrySet()) {
		    sb.append(">> param[" + e.getKey() + "] : " + e.getValue() + "\n");
        }
		sb.append("================================[PARAM-END] \n\n");
		log.debug(sb.toString());
		*/
		
/*
		HttpSession session = request.getSession(false);
		if(null != session) {
			Enumeration enu = session.getAttributeNames();

			while(enu.hasMoreElements()) {
				key = enu.nextElement().toString();
				if("SS_TOP_MENU".equals(key)) continue;	// top menu는 추출하지 않음
				paramMap.put(key, session.getAttribute(key));
			}

			for(Entry<String, Object> e : paramMap.entrySet()) {
				log.debug(">>>>> param[" + e.getKey() + "] : " + e.getValue());
			}
		}*/

		return paramMap;
	}

	public static String encodeStr(String str) {
		String returnStr = "";

		try {
			returnStr = new String(str.getBytes("UTF-8"), "8859_1");
		} catch(UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnStr;

	}

	public static String getClientIp(HttpServletRequest request) {
		String clientIp = request.getHeader("Proxy-Client-IP");
		log.debug("clientIP 1 : " + clientIp);
		if(null == clientIp) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		log.debug("clientIP 2 : " + clientIp);
		if(null == clientIp) {
			clientIp = request.getHeader("x-forwarded-for");
		}
		log.debug("clientIP 3 : " + clientIp);
		if(null == clientIp) {
			clientIp = request.getRemoteAddr();
		}
		log.debug("clientIP 4 : " + clientIp);
		return clientIp;
	}

	/**
	 * Html태크 제거
	 */
	public static final Pattern SCRIPTS = Pattern.compile("<(no)?SCRIPT[^>]*>.*?</(no)?SCRIPT>", Pattern.CASE_INSENSITIVE); // Pattern.CASE_INSENSITIVE:대소문자 모두 찾음
	public static final Pattern STYLE = Pattern.compile("<STYLE[^>]*>.*</STYLE>", Pattern.CASE_INSENSITIVE);
	public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
	public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
	public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
	public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");

	public static String removeHtmlTag(String s) {

		if(s == null) return null;
		Matcher m;

		m = SCRIPTS.matcher(s);
		s = m.replaceAll("");
		m = STYLE.matcher(s);
		s = m.replaceAll("");
		// m = TAGS.matcher(s);
		// s = m.replaceAll("");
		// m = nTAGS.matcher(s);
		// s = m.replaceAll("");
		m = ENTITY_REFS.matcher(s);
		s = m.replaceAll("");
		// m = WHITESPACE.matcher(s);
		// s = m.replaceAll(" ");

		return s;

	}
	
	/**
	 *@brief 현재년도를 기준으로 과거 년도 범위를 지정한다. (콤보용도)
	 *@param iCurrYear 현재년도
	 *@param range 년도 범위
	 *@return
	 *@exception 
	 *@see 
	 */
	public static List getYearList(int iCurrYear, int range) {
		List ls = new ArrayList<>();
		int iEndYear = iCurrYear-range;
		for ( int i=iCurrYear; iEndYear<i; i--) {
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("YEAR", i);
			ls.add(hm);
		}
		return ls;
	}
	
	/**
	 * 리턴메시지를 설정한다.
	 * <strong>작성자 : </strong>kitae kim<br>
	 * <strong>작성일 : </strong>2017. 6. 15.오전 11:02:17<br>
	 * 설명 : 직접입력 <br>
	 *
	 * @version 1.0
	 * @author  kitae kim
	 * @since   2017. 6. 15.
	 * @param   String Code , String Message 
	 * @return  HashMap<String,String> 리턴에대한설명_직접입력
	 */
	public static HashMap<String,String> resultMsg(String Code , String Message){
	    HashMap<String,String> hm = new HashMap<String,String>();
	    hm.put("Code", Code);
	    hm.put("Message", Message);
	    return hm;
	}
	
	/**
	 * 메뉴명을 로그로 출력한다.
	 * <strong>작성자 : </strong>kitae kim<br>
	 * <strong>작성일 : </strong>2017. 6. 15.오전 11:24:37<br>
	 * 설명 : 직접입력 <br>
	 *
	 * @version 1.0
	 * @author  kitae kim
	 * @since   2017. 6. 15.
	 * @param   파라미터타입_직접입력  파라미터설명_직접입력 
	 * @return  void 리턴에대한설명_직접입력
	 */
    public static void menuLog(String menuNm){
        log.debug(
                "\n"
                +"☆━━━━━━━━━━━━━━━━━━━━━━━━━━━━━☆\n"
                +" "+menuNm+"                                               \n"
                +"☆━━━━━━━━━━━━━━━━━━━━━━━━━━━━━☆");
        /*
        log.debug(
                "\n"
                +"+++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"
                +" "+menuNm+"                                            \n"
                +"+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
         */
    }

	/**
	 * 두 LIST 비교후 merge 한다.
	 * <strong>작성자 : </strong>JUNGHYUN KO<br>
	 * <strong>작성일 : </strong>2017. 8. 04<br>
	 * 설명 : 파라메터로 전달받은 2 ArrayList 에 대해 연결 값을 확인후 Mapping 결과를 List 로 반환한다. <br>
	 *
	 * @version 1.0
	 * @author  JUNGHYUN KO
	 * @since   2017. 8. 04.
	 * @param   List<?> 메인List 
	 * @param   List<?> 합쳐질List 
	 * @param   String 비교할키컬럼 
	 * @param   String 메인List 로 추가될컬럼명
	 * @param   String 합쳐질List 에서 추가될 특정컬럼명(option)  
	 * @return  List
	 */
    public static List<HashMap<String, Object>> addJsonCompairMList(List<?> pList, List<?> pJoinList, String pKeyStr, String pAddKeyStr, String pAddValueStr) {
    	System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","8");
        ObjectMapper mapper = new ObjectMapper();
    	List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
	    	rList = pList.stream().map(m -> {
	    		String sJson = "";
				HashMap<String, Object> hm = (HashMap)m;
				List<Object> pTmpList;
				if(!"".equals(StringUtils.defaultString(pAddValueStr))) {
					pTmpList = pJoinList.parallelStream().filter(p -> ((HashMap)p).get(pKeyStr).equals(hm.get(pKeyStr))).map(mp -> {return ((HashMap)mp).get(pAddValueStr);}).collect(Collectors.toList());
				}else {
					pTmpList = pJoinList.parallelStream().filter(p -> ((HashMap)p).get(pKeyStr).equals(hm.get(pKeyStr))).collect(Collectors.toList());
				}
	    		if(pTmpList != null) {
	    	    	try { 
		    			if(pTmpList.size() > 1) {
		    				sJson = mapper.writeValueAsString(pTmpList);
		    			}
		    			else if(pTmpList.size() == 1 && pTmpList.get(0) instanceof List<?>) {
		    				sJson = mapper.writeValueAsString(pTmpList.get(0));
		    			}
		    			else if(pTmpList.size() == 1 && pTmpList.get(0) instanceof Map<?,?>) {
		    				sJson = new JSONObject((HashMap)pTmpList.get(0)).toJSONString();
		    			}else if(pTmpList.size() == 1) {
		    				sJson = pTmpList.get(0).toString();
		    			}
	    	    	}  catch (Exception e) {   
	    	    	    e.printStackTrace();
	    	    	}     			
	    		}
	    		hm.put(pAddKeyStr, sJson);
	    		return hm;
	    	}).collect(Collectors.toList()); 
    	return rList;
    }

    /**
     * 
     * <strong>작성자 : </strong>junghyunko<br>
     * <strong>작성일 : </strong>2017. 8. 22.오후 5:21:24<br>
     * 설명 : multi ArrayList 를 특정key 값을 비교후 신규 entry 로 list 를 merge 한다. <br>
     *
     * @version 1.0
     * @author  junghyunko
     * @since   2017. 8. 22.
     * @param   파라미터타입_직접입력  파라미터설명_직접입력 
     * @return  List<HashMap<String,Object>> 리턴에대한설명_직접입력
     */
    public static List<HashMap<String, Object>> addMergeMList(List<?> pList, List<?> pJoinList, String pKeyStr, String pAddKeyStr) {
    	System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","8");
    	List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
	    	rList = pList.stream().map(m -> {
				HashMap<String, Object> hm = (HashMap)m;
	    		hm.put(pAddKeyStr, pJoinList.parallelStream().filter(p -> ((HashMap)p).get(pKeyStr).equals(hm.get(pKeyStr))).collect(Collectors.toList()));
	    		return hm;
	    	}).collect(Collectors.toList()); 
    	return rList;
    }

    /**
     * 
     * <strong>작성자 : </strong>junghyunko<br>
     * <strong>작성일 : </strong>2017. 8. 22.오후 5:21:24<br>
     * 설명 : ArrayList 내 map 에  전달받은 map 정보를 put 한다. <br>
     *
     * @version 1.0
     * @author  junghyunko
     * @since   2017. 8. 22.
     * @param   파라미터타입_직접입력  파라미터설명_직접입력 
     * @return  List<HashMap<String,Object>> 리턴에대한설명_직접입력
     */
    public static List<HashMap<String, Object>> putMapToMList(List<?> pList, HashMap<String, Object> pMap) {
    	System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","8");
    	List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
	    	rList = pList.stream().map(m -> {
				HashMap<String, Object> hm = (HashMap)m;
	    		hm.putAll(pMap);
	    		return hm;
	    	}).collect(Collectors.toList()); 
    	return rList;
    }
	/**
	 * Map 형태의 object 를 json object 형태의 문자열로 반환.
	 * <strong>작성자 : </strong>JUNGHYUN KO<br>
	 * <strong>작성일 : </strong>2017. 8. 04<br>
	 * 설명 : Map 형태의 object 를 json object 형태의 문자열로 반환 한다. <br>
	 *
	 * @version 1.0
	 * @author  JUNGHYUN KO
	 * @since   2017. 8. 04.
	 * @param   Object Map object  
	 * @return  String jsonstring
	 */
    public static String toJsonString(Object pObj){
    	ObjectMapper mapper = new ObjectMapper();
    	String rStr= "";
    	
    	if(pObj == null)
    		return "";
    	try {
	    	rStr = mapper.writeValueAsString(pObj);
		}  catch (Exception e) {   
		    e.printStackTrace();
		}  
        return rStr;
     }

    public static String toJsonString(HttpServletRequest request){
    	ObjectMapper mapper = new ObjectMapper();
    	String rStr= "";
    	
    	try {
	    	rStr = mapper.writeValueAsString(request.getParameterMap());
		}  catch (Exception e) {   
		    e.printStackTrace();
		}  
        return rStr;
     }

    /**
     * Map → JSONObject
     */
    public static JSONObject getJsonStringFromMap( HashMap<String, Object> map ) {

        JSONObject json = new JSONObject();
        for( HashMap.Entry<String, Object> entry : map.entrySet() ) {
            String key   = entry.getKey();
            Object value = entry.getValue();
            json.put(key, value);
        }
        
        return json;
    }
    
    /**
     * List<Map>을 json으로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return JSONArray.
     */
    public static JSONArray getJsonArrayFromList( List<HashMap<String, Object>> list ) {

        JSONArray jsonArray = new JSONArray();
        for( HashMap<String, Object> map : list ) {
            jsonArray.add( getJsonStringFromMap( map ) );
        }
        
        return jsonArray;
    }
    
    /**
     * List<Map>을 jsonString으로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return String.
     */
    public static String getJsonStringFromList( List<HashMap<String, Object>> list ) {

        JSONArray jsonArray = new JSONArray();
        for( HashMap<String, Object> map : list ) {
            jsonArray.add( getJsonStringFromMap( map ) );
        }
        
        return jsonArray.toJSONString();
    }
    
    public static String URLEncoderReplace(String text) throws Exception {
        String [] charsets = {"UTF-8","EUC-KR","ISO-8859-1", "CP1251", "KSC5601"};
        
        String encode = "";
        String decode = "";
        
        for ( String charset: charsets ) {
            encode = URLEncoder.encode(text, charset);
            System.out.println("ORIGIN["+text+"], "+"ENCODED["+encode+"], CHARSET["+charset+"]" );
        }
        
        System.out.println("");
        
        for ( String charset: charsets ) {
            decode = URLDecoder.decode(encode.replace("%1D", ""), charset);
            System.out.println("ORIGIN["+encode+"], "+"DECODED["+decode+"], CHARSET["+charset+"]" );
        }
        
        return decode;
    }
    
}
