package org.kada.kadam.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileCreate {

	public static void main(String[] args) throws Exception {
		File file = new File("c:/temp/test.json");
		String sendBtnName = "바로가기";
		String sendImageLink = "http://www.onnuridrugstore.co.kr/";
		String sendImageUrl = "https://mud-kage.kakao.com/dn/0RbTV/btrgoqCLOxq/2DjOIXAPwT1jSJ2Zsr77vK/img_l.jpg";

		String lines = "";
		lines += "{ \n";
		lines += " \"button\":[ \n";
		lines += "         {  \n";
		lines += "         \"name\": \"" + sendBtnName + "\", \n";
		lines += "         \"type\":\"WL\", \n";
		lines += "         \"url_mobile\": \"" + sendImageLink + "\"   \n";
		lines += "          } \n";
		lines += "            ],\n";
		lines += " \"image\":{ \n";
		lines += " \"img_url\": \"" + sendImageUrl + "\", \n";
		lines += " \"img_link\":  \"" + sendImageLink + "\"  \n";
		lines += " }  \n";
		lines += "} \n";
		Writer out = null;

		try {
			//out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "cp939")); // 파일타입이 ansi로 되어야함
			byte[] euckrStrBuffer = lines.getBytes("cp939");
			//out.write(euckrStrBuffer);
			//out.flush();			
	        FileOutputStream lFileOutputStream = new FileOutputStream(file);
	        lFileOutputStream.write(euckrStrBuffer);
	        lFileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
