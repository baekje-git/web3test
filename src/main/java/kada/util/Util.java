package kada.util;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ever.support.session.SessionUtil;


public class Util {
	
	public static String getUserId() throws Exception{
        HashMap<String, Object> userInfo = SessionUtil.getUserInfo();
        return userInfo==null ? "":getValue("USER_ID",userInfo);
    }
	
	public static String jsonFormatter(String uglyJsonString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		JsonElement je = JsonParser.parseString(uglyJsonString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	
	public static boolean isDateValid(String dateStr, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	public static String getValue(String key, HashMap<String, Object> pMap) {
		return StringUtils.isEmpty(pMap.get(key)) ? "": String.valueOf(pMap.get(key));		        
    }

	public static String SHA256(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }

        return SHA;
    }
	
	public static String newRandomPassword(int len) {
        StringBuffer sbPwd = new StringBuffer();
        int passNumber = 1; // 생성할 패스워드 개수
        int[] password = new int[len];
        for (int i = 0; i < passNumber; i++) {
            int number = 0;
            int special = 0;
            int lower = 0;
            int upper = 0;
            for (int j = 0; j < password.length; j++) {
                int ranNum;
                ranNum = (int) ((Math.random() * 94) + 33);
                password[j] = ranNum;
                if (ranNum < 48) {
                    special = 1;
                } else if (ranNum < 58) {
                    number = 1;
                } else if (ranNum < 65) {
                    special = 1;
                } else if (ranNum < 91) {
                    upper = 1;
                } else if (ranNum < 97) {
                    special = 1;
                } else if (ranNum < 123) {
                    lower = 1;
                } else {
                    special = 1;
                }
            }
            if (number + special + lower + upper >= 4) {
                for (int n : password) {
                    sbPwd.append((char) n);
                }
            } else {
                i--;
            }
        }

        String str = sbPwd.toString();
        str = str.replaceAll("%","");
        str = str.replaceAll("\"",""); //&quot;
        str = str.replaceAll("|","");
        str = str.replaceAll("&","");
        str = str.replaceAll("%","");
        str = str.replaceAll("<","");
        str = str.replaceAll(">","");
        str = str.replaceAll("-","");
        str = str.replaceAll("'","′");
        return str;
    }
	
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) return true;
        
        if (o instanceof String) {
            if (((String)o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection)o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            return true;
        } else {
            return false;
        }

        return false;
    }
	
	public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
	
	private static boolean checkIP(String ip) {
        return isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }
	
	public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (checkIP(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	protected static double d2r = Math.PI / 180;
    public static double gfn_getDistance(String startPointLon, String startPointLat, String endPointLon, String endPointLat) throws Exception {

        double dStartPointLon = Double.parseDouble(Util.nullreplace(startPointLon, "0"));
        double dStartPointLat = Double.parseDouble(Util.nullreplace(startPointLat, "0"));
        double dEndPointLon = Double.parseDouble(Util.nullreplace(endPointLon, "0"));
        double dEndPointLat = Double.parseDouble(Util.nullreplace(endPointLat, "0"));
    	double theta = dStartPointLon - dEndPointLon;
    	double dist = Math.sin(dStartPointLat * Math.PI / 180) * Math.sin(dEndPointLat * Math.PI / 180) +
    				  Math.cos(dStartPointLat * Math.PI / 180) * Math.cos(dEndPointLat * Math.PI / 180) *
    				  Math.cos(theta * Math.PI / 180);
    	dist = Math.acos(dist);
    	dist = dist / Math.PI * 180;
    	dist = dist * 60 * 1.1515;
    	dist = dist * 1.609344;
    	
    	//가중치 10% (직선거리 계산식인데 도로는 곡선이니 가중치를 10%로 해서 계산해준다.
    	dist += (dist * 0.2);
    	
    	return dist;
    }

    public static String nullreplace(String strData, String strReplace) {
        String rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = strReplace;
        }
        return rtnData;
    }

    public static String nullreplace(Object strData, String strReplace) {
        Object rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = strReplace;
        }
        return String.valueOf(rtnData);
    }
    
    public static String date2split(String strData) {
        String rtnData = null;
        if (strData.length() == 8) {
            rtnData = strData.substring(0, 4) + "-" + strData.substring(4, 6) + "-" + strData.substring(6, 8);
        } else {
            rtnData = strData;
        }
        return rtnData;
    }
    
    public static String date2split2(String strData) {
        String rtnData = null;
        if (strData.length() == 8) {
            rtnData = strData.substring(0, 4) + "년 " + strData.substring(4, 6) + "월 " + strData.substring(6, 8) + "일";
        } else {
            rtnData = strData;
        }
        return rtnData;
    }
    
    public static String date2split3(String strData) {
        String rtnData = null;
        if (strData.length() == 8) {
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(FastDateFormat.getInstance("yyyyMMdd").parse(strData));
            } catch (Exception e) {}
            String[] arrWorkday = {"(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"};
            rtnData = strData.substring(0, 4) + "년 " + strData.substring(4, 6) + "월 " + strData.substring(6, 8) + "일" + arrWorkday[cal.get(cal.DAY_OF_WEEK) - 1];
        } else {
            rtnData = strData;
        }
        return rtnData;
    }

    public static String time2split(String strData) {
        String rtnData = null;
        if (strData.length() == 4) {
            rtnData = strData.substring(0, 2) + ":" + strData.substring(2, 4);
        } else {
            rtnData = strData;
        }
        return rtnData;
    }
    
    public static String time2split2(String strData) {
        String rtnData = null;
        if (strData.length() == 4) {
            rtnData = strData.substring(0, 2) + "시 " + strData.substring(2, 4) + "분";
        } else if (strData.length() == 3) {
        	rtnData = "0"+strData.substring(0, 1) + "시 " + strData.substring(1, 3) + "분";
        } else if (strData.length() == 2) {
            rtnData = strData + "시";
        } else {
            rtnData = strData;
        }
        return rtnData;
    }
    
    /**
     * 문자인증에 사용될 6자리 숫자생성
     * 
     */
    public static String certSms() {
    	Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
    }
}
