package org.kada.kadam.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.util.Nullable;

public class StreamTest {

	public static void main(String[] args) {
		List list = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> row = new HashMap<>();
		row.put("aaa", "bbb");
		list.add(row);
		
		List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) list.stream().map(item -> (HashMap<String,Object>)item).collect(Collectors.toList());
		System.out.println(list2);
		
		ArrayList list3 = null;
		
	}

}
