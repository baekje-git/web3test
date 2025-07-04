package org.kada.kadam.test;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonTest {

	public static void main(String[] args) throws Exception {
		Reader reader = new FileReader("C:\\temp\\aaa.txt");
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(reader);
		JSONArray jsonArray = (JSONArray)obj;
		if(jsonArray.size()>0) {
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject jsonObj = (JSONObject)jsonArray.get(i);
				//System.out.println(jsonObj.toJSONString());
				System.out.println(jsonObj.get("Data"));
			}
		}
	}

}
