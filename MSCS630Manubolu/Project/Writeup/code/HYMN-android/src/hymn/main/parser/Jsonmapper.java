package hymn.main.parser;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class Jsonmapper {

	Map<String, String> map = new HashMap<String, String>();
	private JSONObject jobj = new JSONObject();

	public JSONObject splitter(String message) {
		jobj.clear();
		jobj = (JSONObject) JSONValue.parse(message);

		return jobj;

	}

	public JSONObject merger() {
		jobj.clear();
		jobj.put("fail", "loginfail");
		return jobj;
	}

	public JSONObject merger(Map<String, String> map) {
		this.map = map;
		jobj.clear();
		if(map.get("type").equals("validate")){
			jobj.put("type",map.get("type"));
			jobj.put("PH",map.get("PH"));
			jobj.put("user", map.get("user"));
			jobj.put("Pass", map.get("Pass"));
			jobj.put("RSA", map.get("RSA"));
			jobj.put("exponent", map.get("exponent"));
		}
		if(map.get("type").equals("message")){
			jobj.put("type",map.get("type"));
			jobj.put("to",map.get("to"));
			jobj.put("from", map.get("from"));
			jobj.put("text", map.get("text"));
			jobj.put("hash", map.get("hash"));
		}
		if(map.get("type").equals("Validated")){
			jobj.put("to",map.get("to"));
			jobj.put("username",map.get("username"));
		}
		if(map.get("type").equals("Secure")){
			jobj.put("type",map.get("type"));
			jobj.put("from",map.get("from"));
			jobj.put("to",map.get("to"));
			jobj.put("AES",map.get("AES"));
		}
		return jobj;
	}
	
	public JSONObject merger(String string, JSONArray jarray) {
		// TODO Auto-generated method stub
		jobj.clear();
		jobj.put("type",string);
		jobj.put("list",jarray);
		
		return jobj;
	}

}
