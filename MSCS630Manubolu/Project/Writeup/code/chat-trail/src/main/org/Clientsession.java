package main.org;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

public class Clientsession {
	
	public static Map<String,IoSession> connectedclients= new HashMap<String,IoSession>();
	
	public  void addsession(String name,IoSession session){
		System.out.println("saved session on the name"+name);
		connectedclients.put(name, session);
	}
	
	public IoSession getsession(String name){
		
		return connectedclients.get(name);
		
				
	}
	public int size(){
		
		return connectedclients.size();
	}
	
	public Set<String> getusers(){
		
		Set<String> keys = connectedclients.keySet();
		return keys;
	}
	
	public boolean islogedin(String user){
		
		if(connectedclients.containsKey(user)) return true;
		return false;
		
	}

	public void removesession(String name) {
		// TODO Auto-generated method stub
		connectedclients.remove(name);
		System.out.println("removed session on the name"+name);
	}
	


}
