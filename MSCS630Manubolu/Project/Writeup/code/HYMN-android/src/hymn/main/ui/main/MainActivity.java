package hymn.main.ui.main;

import hymn.main.parser.MessageParser;
import hymn.main.parser.MessageParserhandler;
import hymn.main.server.Comminitiator;
import hymn.main.ui.chat.ChatViewFrag;
import hymn.main.ui.chat.R;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity{
	
	SharedPreferences sharedpref;
	Editor editor;
	MessageParserhandler mps=new MessageParserhandler();
	MessageParser mpr=new MessageParser();
	UsersList ul;
	Login login;
	ChatViewFrag cvf;
	Comminitiator comminitiator;
	FragmentManager fragmentmanager = getFragmentManager();
	FragmentTransaction fragmenttransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedpref=getApplicationContext().getSharedPreferences("pref", 0);
		sharedpref.edit().clear().commit();
		editor=sharedpref.edit();
		ul=new UsersList(editor);
		login = new Login(editor);
		setContentView(R.layout.fragmentholder);
		comminitiator=new Comminitiator(ul);
		comminitiator.execute();

		if(sharedpref.getString("phonenumber", "0").equalsIgnoreCase("0")){
			Fragmentchange(1);
		}
		else {
			Fragmentchange(2);
			MessageParserhandler.parse("type:"+"validate"+" PH:"+sharedpref.getString("phonenumber","0")+" Pass:"+sharedpref.getString("password", "0")+" user:"+sharedpref.getString("user", "0"));
			
		}
	}

	public void Fragmentchange(int switcher) {
		
		switch (switcher) {

		case 1:
			
			fragmentmanager.beginTransaction().replace(R.id.frame, login)
					.commit();
			break;
		case 2:
			fragmenttransaction=fragmentmanager.beginTransaction();
			fragmenttransaction.add(R.id.frame, ul).commit();
			//fragmenttransaction.addToBackStack(null).commit();
			break;
			
		case 3:
			fragmenttransaction=fragmentmanager.beginTransaction();
			fragmenttransaction.add(R.id.frame, cvf);
			fragmenttransaction.addToBackStack(null)
			.commit();
			break;
		case 4:
			fragmentmanager.beginTransaction().remove(login)
			.commit();
		}

	}
	

	@Override
	public void onBackPressed() {
	    if (getFragmentManager().getBackStackEntryCount() > 0 ){
	        getFragmentManager().popBackStack();
	    } else {
	        super.onBackPressed();
	    }
	}

public void setcvf(ChatViewFrag cvf) {
	// TODO Auto-generated method stub
	this.cvf=cvf;
	Fragmentchange(3);
	
}



}
