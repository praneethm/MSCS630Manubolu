package main.parser.main;

import java.util.HashMap;
import java.util.Map;

public class Stringmapper {
	
	public Map<String,String> splitter(String message){
		System.out.println("in splitter");
		Map<String, String> map;
		String[] pairs = message.split(" ");
		map = new HashMap<String, String>();
		System.out.println("in replier.process");
		System.out.println(message);
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			if(keyValue.length>1){
			map.put(keyValue[0], keyValue[1]);
			}
		}
		System.out.println(map.toString());
		return map;
		
		
	}
	

}
