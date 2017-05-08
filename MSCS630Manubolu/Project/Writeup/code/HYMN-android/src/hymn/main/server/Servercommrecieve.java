package hymn.main.server;

import hymn.main.parser.Jsonmapper;
import hymn.main.parser.MessageParser;
import hymn.main.ui.chat.ChatView;
import hymn.main.ui.chat.ChatViewFrag;
import hymn.main.ui.chat.Messageobject;
import hymn.main.ui.main.UsersList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import android.os.AsyncTask;

public class Servercommrecieve extends AsyncTask<String, String, String> {

	MessageParser mp;
	Socket socket;
	UsersList ul;
	static Map<String, ChatViewFrag> usernumber = new HashMap<String, ChatViewFrag>();
	Messageobject mo;
	Jsonmapper jmap = new Jsonmapper();
	private JSONObject jobj = new JSONObject();

	public Servercommrecieve(MessageParser mp, Socket socket, UsersList ul) {

		this.mp = mp;
		this.socket = socket;
		this.ul = ul;
	}

	@Override
	protected String doInBackground(String... string) {
		// TODO Auto-generated method stub

		try {

			while (true) {

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				String mess;
				while ((mess = bufferedReader.readLine()) != null) {
					publishProgress(mess);

				}

			}

			// bufferedWriter.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {

		// TODO Auto-generated method stub

		jobj.clear();
		jobj = (JSONObject) JSONValue.parse(values[0]);
		System.out.println("recieved from server" + values[0].toString());
		switch (jobj.get("type").toString()) {
		case "Validated":
			ul.updateuserinfo(jobj);
			break;
		case "UserList":
			ul.updatelist((JSONArray) jobj.get("list"));
			break;
		case "loginfail":
			mainloop: while (true) {
				if (ul.isAdded()) {
					ul.faillogin();
					break mainloop;
				}
			}
			break;
		case "message":
			mo = new Messageobject();
			//mo.setMessage(jobj.get("text").toString());
			mo.setPosition(0);
			if (usernumber.containsKey(jobj.get("from")))
				usernumber.get(jobj.get("from").toString()).updatechat(mo,jobj.get("text").toString(),jobj.get("hash").toString());
			break;
		case "Secure":
			ul.updateKey(jobj.get("from").toString(),jobj.get("AES").toString());
			break;

		default:
			break;
		}
		

	}

	public synchronized static void adduseractivity(String touser,
			ChatViewFrag chatView) {
		usernumber.put(touser, chatView);

	}

	public synchronized static void removeuseractivity(String touser) {
		// TODO Auto-generated method stub
		usernumber.remove(touser);

	}

}
