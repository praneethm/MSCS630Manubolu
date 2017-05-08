package hymn.main.server;

import hymn.main.parser.MessageParser;
import hymn.main.parser.MessageParserhandler;
import hymn.main.ui.main.UsersList;

import java.io.IOException;
import java.net.Socket;

import android.os.AsyncTask;

public class Comminitiator extends AsyncTask<String, String, String> {

	Socket socket;
	Servercommrecieve scr;
	Servercommsend scs;
	MessageParser mpr;
	MessageParserhandler mps;
	UsersList ul;
	
	public Comminitiator (UsersList ul){

		this.ul=ul;
		System.out.println("comminitiator initiated");
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			socket = new Socket("192.168.43.228", 5222);
			MessageParserhandler.setsocket(socket);
			System.out.println("socket created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			scr=new Servercommrecieve(mpr,socket,ul);
			scr.execute();
			//ul.addscr(scr);
			System.out.println("server reciever created");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("socket is set in messageparserhandler");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		return null;
	}

}
