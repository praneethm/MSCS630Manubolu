package main.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.database.communication.Validations;
import main.parser.main.Jsonmapper;
import main.parser.main.Stringmapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class Replier {

	IoSession session;
	boolean validated = false;
	public static ArrayList<User> userslist = new ArrayList<User>();

	User create;
	Clientsession cs;
	Validations val = new Validations();
	String name;
	StringBuilder sb = new StringBuilder();
	static Map<String, String> map;
	JSONObject jobj = new JSONObject();
	Stringmapper splitter = new Stringmapper();
	Jsonmapper jmapper = new Jsonmapper();

	Replier(IoSession session, Clientsession cs) {

		this.session = session;
		this.cs = cs;

	}

	public void Process(String message) {

		// map=(Map<String, String>)splitter.splitter(message);
		jobj = jmapper.splitter(message);

		/*
		 * String[] pairs = message.split(" "); map = new HashMap<String,
		 * String>(); System.out.println("in replier.process");
		 * System.out.println(message); for (int i = 0; i < pairs.length; i++) {
		 * String pair = pairs[i]; String[] keyValue = pair.split(":");
		 * if(keyValue.length>1){ map.put(keyValue[0], keyValue[1]); } }
		 */
		System.out.println(jobj.toString());
		System.out.println("recieved message, processing through conditions");

		if (!validated) {
			/*
			 * if (Arrays.asList(users).contains(message.toString()) &&
			 * created==false) { createsession(message.toString());
			 * validated=true; cs.addsession(message.toString(),session);
			 * session.write("welcome :"+message.toString()+"/n");
			 * session.write("enter the username to chat with");
			 * onlineusers=cs.getusers(); for(String i : onlineusers){
			 * session.write(i); } }
			 * else{session.write("invalid password or already loggedin"); }
			 */

			if (jobj.get("type").equals("validate")) {

				if (val.checkforavailability(jobj.get("user").toString())) {
					System.out.println("User is available in database");
					if (userstatus(jobj.get("user").toString())) {
						session.write(jmapper.merger());
						// session.write("loginfail");

					}

					// user is not logged in
					// create client session
					// add user to list of online users
					else {
						System.out
								.println("user in not logged in, creating new login");
						// code for validating the password and mac address and
						// verification of previous passcode
						if (val.validateuser(jobj.get("PH").toString(), jobj
								.get("user").toString(), jobj.get("Pass")
								.toString())) {
							System.out.println("user has inputed correct credentials");
							createsession(jobj.get("user").toString(),jobj.get("RSA").toString(),jobj.get("exponent").toString());
							cs.addsession(jobj.get("user").toString(), session);
							validated = true;
							name = jobj.get("user").toString();

							Map<String, String> map = new HashMap<String, String>();
							JSONArray jarray = new JSONArray();
							map.put("type", "Validated");
							map.put("username", jobj.get("user").toString());
							session.write(jmapper.merger(map));
							
							for (User alluser : userslist) {
								jarray = new JSONArray();
							for (User user : userslist) {
								map.put("name", user.getUser());
								//jarray.add(user.getUser());
								JSONObject jo = new JSONObject();
								jo.put("name", user.getUser());
								jo.put("mod", user.getMod());
								jo.put("exponent", user.getExponent());
								jarray.add(jo);
								
							}

							cs.getsession(alluser.getUser()).write(jmapper.merger("UserList", jarray));

						}
							System.out.println("total sesions "+cs.size());
						}

						else {
							session.write(jmapper.merger());
						}

					}

				} else {

					session.write(jmapper.merger());
				}

			} else {

				session.write(jmapper.merger());
			}

		} else {
			System.out.println("got into else in server");
			switch (jobj.get("type").toString()) {

			case "message":
				if (userstatus(jobj.get("to").toString())) {
					cs.getsession(jobj.get("to").toString()).write(
							jobj.toString());
				} else {
					session.write("error user:notavailable");
				}
				break;
			case "Secure":
				if (userstatus(jobj.get("to").toString())) {
					cs.getsession(jobj.get("to").toString()).write(
							jobj.toString());
				} else {
					session.write("error sending aes");
				}
				break;
			case "status":
				break;
			case "auth":
				break;

			}

		}

	}

	synchronized private void createsession(String string,String key,String exponent) {
		// TODO Auto-generated method stub

		create = new User();
		create.setUser(string);
		create.setLogedin(true);
		create.setPublicRSA(key,exponent);
		userslist.add(create);
		cs.addsession(string, session);

	}

	private boolean userstatus(String user) {
		System.out.println("checking if user is already logged in for " + user);
		if (cs.islogedin(user))
			return true;

		return false;
	}

	public void removesession() {
		cs.removesession(name);
		userslist.remove(create);

	}

}
