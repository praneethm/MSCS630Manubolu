package hymn.main.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.os.AsyncTask;

public class Servercommsend {
	BufferedWriter bufferedWriter;
	Socket socket;
	
	public Servercommsend(Socket socket){
		System.out.println("new initilization of scs");
		this.socket=socket;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public void sendmessagetoserver(String... string){
		System.out.println("started scs thread");
try {
	bufferedWriter.write(string[0]);
	bufferedWriter.flush();
	System.out.println("sent message to server");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	
		
	}



}
