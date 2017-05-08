package main.database.communication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validations {
	
	Connection co=Mysqlconn.getConnection();
	String sqlavailableuser="select name from hymn.login where name =?";
	String sqlvalidateuser="select name,password from hymn.login where phonenumber=?";
	ResultSet rs;
	PreparedStatement ps;
	
	public boolean checkforavailability(String user){
		System.out.println("checking is user is available in database");
    	
		try {
			ps = co.prepareStatement(sqlavailableuser);
			ps.setString(1, user);
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				if(user.equals(rs.getString("name"))) return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return false;
	}
	
	public boolean validateuser(String PH,String user,String pass){

		try {
			System.out.println("checking for user credentials");
			ps = co.prepareStatement(sqlvalidateuser);
			ps.setString(1, PH);
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				if(user.equals(rs.getString("name")) && pass.equals(rs.getString("password"))) return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error validating user");
		}

		
		
		return false;

	}

}
