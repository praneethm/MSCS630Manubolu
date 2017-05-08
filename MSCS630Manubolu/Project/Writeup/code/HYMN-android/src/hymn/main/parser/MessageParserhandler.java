package hymn.main.parser;

import hymn.main.server.Servercommsend;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import main.security.init.KeyGen;
import net.minidev.json.JSONObject;

public class MessageParserhandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private Socket socket;
	static boolean socketvalue = false;
	static StringBuilder message;
	static Map<String, String> map = new HashMap<String, String>();
	static Jsonmapper jmap = new Jsonmapper();
	static JSONObject jobj = new JSONObject();

	static synchronized public void parse(String... params) {
		message = new StringBuilder();
		System.out.println("parser in action before sending raw data " );
		String[] pairs = params[0].split(" ");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			if (keyValue.length > 1) {
				System.out.println("after split "+keyValue[1]+" "+keyValue[0]);
				map.put(keyValue[0], keyValue[1]);
			}
		}
		//KeyGen.GenerateKeys();
		//map.put("RSAPub", KeyGen.getPKA().toString());
		System.out.println(map.toString());
		jobj = jmap.merger(map);

		System.out.println("sending to scs");
		System.out.println("sending values to scs " + jobj.toString());

		loop: while (true) {
			if (socketvalue) {
				new Servercommsend(socket).sendmessagetoserver(jobj.toString()
						+ "$");
				System.out.println("sent values to server");
				break loop;
			}
		}
	}

	static synchronized public void parse(Map<String, String> map) {
		message = new StringBuilder();
		System.out.println("parser in action before sending raw data");

		jobj = jmap.merger(map);

		System.out.println("sending to scs");
		System.out.println("sending values to scs " + jobj.toString());

		loop: while (true) {
			if (socketvalue) {
				new Servercommsend(socket).sendmessagetoserver(jobj.toString()
						+ "$");
				System.out.println("sent values to server");
				break loop;
			}
		}
	}

	public static void setsocket(Socket socket) {
		MessageParserhandler.socket = socket;
		socketvalue = true;
	}

}
