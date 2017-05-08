package hymn.main.parser;

import hymn.main.server.Servercommsend;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;


public class MessageParser extends AsyncTask<String, String, String>{

	Map<String, String> map = new HashMap<String, String>();
	StringBuilder message=new StringBuilder();
	Servercommsend scs=null;
	boolean scsvalue=false;
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		System.out.println("parser in action before sending raw data");
		String[] pairs = params[0].split(" ");
		System.out.println("login length"+pairs.length);
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			if(keyValue.length>1){
			map.put(keyValue[0], keyValue[1]);
			}
		}
		if(params[0].startsWith("login")){
			message.append("validate "+"PH:"+map.get("PH")+" user:"+map.get("user")+" Pass:"+map.get("pass"));	
		}
	
		
		return message.toString();
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		System.out.println("before condition sending to scs");
			if(scsvalue){
				System.out.println("sending to scs");
				System.out.println("sending values to scs " +result.toString());
			//scs.execute(result);
			}
	
	}
	public void setscs(Servercommsend scs){
		this.scs=scs;
		scsvalue=true;
	}

	
	

}
