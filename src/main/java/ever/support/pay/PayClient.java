package ever.support.pay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ever.support.ConstantProp;
import ever.support.net.RestUtil;

public class PayClient{
	
	private Logger log = LogManager.getLogger(this.getClass());
 
public static void main(String[] args) {
		
	PayClient runSocket = new PayClient();
		//runSocket.runProcess();	
		
		// memmory detroy
	}	
	
	// default Constructor
	public PayClient(){
			
	}
	
	
	// run Process
	
	public String runProcess(String sendData){
		
		String returnData="";
		
		try {
			
			//log.info("runProcess sendData:"+sendData);
			
			String[] sendArray = sendData.split("\\|");
			
			String cardNo = sendArray[0];
			String cardYm = sendArray[1];
			String amt = sendArray[2].replaceAll(",", "");
			String custServiceNo = sendArray[3];
			String custNm = sendArray[4];
			String ceoNm = sendArray[5];
			String serviceNo = sendArray[6];
			String supNo = sendArray[7];
			String ins = sendArray[8];
			String empId = sendArray[9];
			
			/*
			for (String wo : sendArray ){
	            System.out.println(wo);
	        }
			
			log.info("runProcess cardNo:"+cardNo);
			log.info("runProcess cardYm:"+cardYm);
			log.info("runProcess amt:"+amt);
			log.info("runProcess custServiceNo:"+custServiceNo);
			log.info("runProcess custNm:"+custNm);
			log.info("runProcess ceoNm:"+ceoNm);
			log.info("runProcess serviceNo:"+serviceNo);
			log.info("runProcess supNo:"+supNo);
			*/
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");

			String sysTime = dayTime.format(new Date(time)); //주문번호 가맹점에서 생성한 유일한 번호

			String params =  "_mode="     + URLEncoder.encode("approve", "UTF-8")            + "&" +
							 "_real="     + URLEncoder.encode("1", "UTF-8")                  + "&" +
							 "_utf8="     + URLEncoder.encode("1", "UTF-8")                  + "&" +
							 "_staxno="   + URLEncoder.encode(serviceNo, "UTF-8")         + "&" +
							 "_supcode="   + URLEncoder.encode(supNo, "UTF-8")         + "&" +
							 "_semp="     + URLEncoder.encode(empId, "UTF-8")                   + "&" +
							 "_ptaxno="   + URLEncoder.encode(custServiceNo, "UTF-8")         + "&" +
							 "_pname="    + URLEncoder.encode(custNm, "UTF-8")           + "&" +
							 "_pmasname=" + URLEncoder.encode(ceoNm, "UTF-8")                   + "&" +
							 "_ptel="     + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_pemail="   + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_orderno="  + URLEncoder.encode(sysTime, "UTF-8") + "&" +
							 "_goods="    + URLEncoder.encode("온라인상품", "UTF-8")         + "&" +
							 "_amt="      + URLEncoder.encode(amt, "UTF-8")          + "&" +
							 "_cardno="   + URLEncoder.encode(cardNo, "UTF-8")    + "&" +
							 "_cardym="   + URLEncoder.encode(cardYm, "UTF-8")               + "&" +
							 "_cardins="  + URLEncoder.encode(ins, "UTF-8")                 + "&" +
							 "_enctype="  + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_encitem="  + URLEncoder.encode("_cardno,_cardym", "UTF-8")    + "&" +
							 "_etc01="    + URLEncoder.encode("백제약품", "UTF-8")             + "&" +
							 "_etc02="    + URLEncoder.encode("결제", "UTF-8")               + "&" +
							 "_etc03="    + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_etc04="    + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_etc05="    + URLEncoder.encode("", "UTF-8");
			
			
			//log.info("runProcess params.toString():"+params.toString());
			
			
			StringBuilder putSb = new StringBuilder();
			//System.out.println(params);
			//System.out.println(params.toString());
			//System.out.println(params.length());
			
			String hostname = ConstantProp.GV_paymentCresotyIp;// "125.141.199.80";//
			int port = 8001; //운영포트
			//int port = 8002; //테스트포트
			
			
			Socket socket = openSocket(hostname, port);
			
			String path = "/pay.run";
	
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			
			// Send headers & parameters
			putSb.append("POST " + path + " HTTP/1.0\r\n");
			putSb.append("Host: " + hostname + "\r\n");
			putSb.append("User-Agent: " + InetAddress.getLocalHost() + "\r\n");
			putSb.append("Content-type: application/x-www-form-urlencoded\r\n");
			putSb.append("Content-length: " + params.length() + "\r\n");
			putSb.append("Connection: Close\r\n\r\n");
			putSb.append(params.toString() + "\r\n");

			bufferedWriter.write(putSb.toString());
			bufferedWriter.flush();
	
			
			// Get response
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			StringBuffer sb = new StringBuffer();		
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
			    //System.out.println(str);
				if(str.startsWith("_STX")){
					sb.append(str + "\n");
				}
			}
			
			returnData = sb.toString();
			//log.info("receive DATA sb:"+sb);
			
			
			bufferedWriter.close();
			bufferedReader.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnData;
	}
	
	
	public HashMap<String, Object> runInicis(HashMap<String, Object> sendMap, HashMap<String, Object> infoMap) throws Exception{
		
		String ip;
		try {
			InetAddress local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e1) {
			ip = "127.0.0.1";
		}
		
		EncryptInicis.KEY = infoMap.get("KEY").toString();
		EncryptInicis.mid = infoMap.get("mid").toString();
		EncryptInicis.IV = infoMap.get("IV").toString();

		sendMap.put("type","Pay");
		sendMap.put("paymethod","Card");
		sendMap.put("timestamp",sendMap.get("sysTime").toString());
		sendMap.put("clientIp",ip);
		sendMap.put("url","www.baekje.net");
		sendMap.put("moid",sendMap.get("moid").toString());
		sendMap.put("userId",sendMap.get("userId").toString());
		//sendMap.put("authentification","99"); //기존에는 본인인증을 사용안했기때문에 "99"
		sendMap.put("authentification","00"); //본인인증사용할땐 "00"
		log.info(sendMap.toString());
	    log.info(sendMap.get("cardNumber").toString());
//	    try {
//	    	log.info(EncryptInicis.encrypt_AES("abcd"));	
//	    }catch(Exception ex) {
//	    	log.error(ex.getMessage(),ex);
//	    }
	    
		sendMap.put("cardNumber",EncryptInicis.encrypt_AES(sendMap.get("cardNumber").toString()));
		sendMap.put("cardExpire",EncryptInicis.encrypt_AES(sendMap.get("cardExpire").toString()));
		sendMap.put("regNo",EncryptInicis.encrypt_AES(sendMap.get("regNo").toString()));
		sendMap.put("hashData",EncryptInicis.encryptSHA512(EncryptInicis.KEY + sendMap.get("type").toString()
				             + sendMap.get("paymethod").toString() + sendMap.get("timestamp").toString()
				             + sendMap.get("clientIp").toString() + sendMap.get("mid").toString()
				             + sendMap.get("moid").toString() + sendMap.get("price").toString()
				             + sendMap.get("cardNumber").toString()
				   ));

		
		log.info("infoMap : "+infoMap.toString());
		log.info("sendData ORG: "+sendMap.toString());
		String sendData = RestUtil.mapToParamUrlEncode(sendMap,"hashData");
		log.info("sendData ENC: "+sendData);
		
		String rspStr =  HTTPClientByPost(infoMap.get("URL").toString(), sendData, "application/x-www-form-urlencoded", "utf-8");
		log.info("HTTPClientByPost result : "+rspStr);
		return RestUtil.jsonStrToMap(rspStr);
	}


	public String runCancelProcess(String sendData){
		
		String returnData="";
		
		try {
			
			//log.info("runProcess sendData:"+sendData);
			
			String[] sendArray = sendData.split("\\|");
			
			String serviceNo = sendArray[0];
			String appDate = sendArray[1];
			String appTime = sendArray[2];
			String appNo = sendArray[3];
			
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");

			String sysTime = dayTime.format(new Date(time)); //주문번호 가맹점에서 생성한 유일한 번호

			String params =  "_mode="     + URLEncoder.encode("cancel", "UTF-8")            + "&" +
							 "_real="     + URLEncoder.encode("1", "UTF-8")                  + "&" +
							 "_utf8="     + URLEncoder.encode("1", "UTF-8")                  + "&" +
							 "_staxno="   + URLEncoder.encode(serviceNo, "UTF-8")         + "&" +
							 
							 "_appdate="   + URLEncoder.encode(appDate, "UTF-8")         + "&" +
							 "_apptime="   + URLEncoder.encode(appTime, "UTF-8")         + "&" +
							 "_apptno="   + URLEncoder.encode(appNo, "UTF-8")         + "&" +
							 
							 "_etc01="    + URLEncoder.encode("백제약품", "UTF-8")             + "&" +
							 "_etc02="    + URLEncoder.encode("결제취소", "UTF-8")               + "&" +
							 "_etc03="    + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_etc04="    + URLEncoder.encode("", "UTF-8")                   + "&" +
							 "_etc05="    + URLEncoder.encode("", "UTF-8");
			
			
			//log.info("runProcess params.toString():"+params.toString());
			
			
			StringBuilder putSb = new StringBuilder();
			//System.out.println(params);
			//System.out.println(params.toString());
			//System.out.println(params.length());
			
			String hostname = ConstantProp.GV_paymentCresotyIp;// "125.141.199.80";//
			int port = 8001; //운영포트
			//int port = 8002; //테스트포트
			
			
			Socket socket = openSocket(hostname, port);
			
			String path = "/pay.run";
	
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			
			// Send headers & parameters
			putSb.append("POST " + path + " HTTP/1.0\r\n");
			putSb.append("Host: " + hostname + "\r\n");
			putSb.append("User-Agent: " + InetAddress.getLocalHost() + "\r\n");
			putSb.append("Content-type: application/x-www-form-urlencoded\r\n");
			putSb.append("Content-length: " + params.length() + "\r\n");
			putSb.append("Connection: Close\r\n\r\n");
			putSb.append(params.toString() + "\r\n");

			bufferedWriter.write(putSb.toString());
			bufferedWriter.flush();
	
			
			// Get response
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			StringBuffer sb = new StringBuffer();		
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
			    //System.out.println(str);
				if(str.startsWith("_STX")){
					sb.append(str + "\n");
				}
			}
			
			returnData = sb.toString();
			//log.info("receive DATA sb:"+sb);
			
			
			bufferedWriter.close();
			bufferedReader.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnData;
	}
	
	// open Socket Part
	public Socket openSocket(String server, int port) throws Exception {
		Socket socket;
	
		// create a socket with a timeout
		try {
			InetAddress inteAddress = InetAddress.getByName(server);
			SocketAddress socketAddress = new InetSocketAddress(inteAddress,
					port);
	
			socket = new Socket();
	
			int timeoutInMs = 1000 * 1000; // 1000 seconds
			socket.connect(socketAddress, timeoutInMs);
	
			return socket;
		} catch (SocketTimeoutException ste) {
			System.err.println("Timed out waiting for the socket.");
			ste.printStackTrace();
			throw ste;
		}
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
				System.out.println(e.getMessage());
			} finally {
	            if (bufferdWriter != null) {
	                try {
	                    bufferdWriter.close();
	                } catch (IOException e) {
	                	System.out.println(e.getMessage());
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
					System.out.println(e.getMessage());
				}catch(IOException e) {
					System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
			result = "";
		}
		return result;
	}
}


