package hymn.main.ui.chat;

import hymn.main.parser.MessageParserhandler;
import hymn.main.server.Servercommrecieve;
import hymn.main.server.Servercommsend;
import hymn.main.ui.main.UsersList;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ChatView extends Activity {

	ListView ll;
	ListViewadapter lva;
	ArrayList<Messageobject> messagearray = new ArrayList<Messageobject>();
	EditText et;
	Messageobject mo;
	// Servercomm sc=new Servercomm();
	static BufferedWriter bufferedWriter;
	ImageButton submit;
	Bundle extras;
	String touser;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		onPause();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		extras = getIntent().getExtras();
		touser = extras.get("toname").toString();
		getActionBar().setTitle(touser);
		getActionBar().setSubtitle("Active");
		//Servercommrecieve.adduseractivity(touser, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatview);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		mo = new Messageobject();
		mo.setMessage("welcome");
		mo.setPosition(2);
		messagearray.add(mo);

		ll = (ListView) findViewById(R.id.ll);

		lva = new ListViewadapter(this.getApplicationContext(), messagearray);
		ll.setAdapter(lva);
		et = (EditText) findViewById(R.id.ed);
		submit = (ImageButton) findViewById(R.id.submit);
		/*
		 * try { socket = new Socket("192.168.1.5", 5222); sc.execute(socket);
		 * bufferedWriter = new BufferedWriter(new
		 * OutputStreamWriter(socket.getOutputStream())); } catch
		 * (UnknownHostException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
		et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ll.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ll.setSelection(lva.getCount() - 1);
					}
				});

			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mo = new Messageobject();
				if (!et.getText().toString().equals("")) {
					mo.setMessage(et.getText().toString());
					mo.setPosition(1);
					messagearray.add(mo);
					lva.notifyDataSetChanged();
					System.out.println("message that is going to be sent"+et.getText().toString());
					MessageParserhandler.parse("message "
							+ "to:"
							+ touser
							+ " from:"
							+ extras.get("fromuser") + " text:"
									+ et.getText().toString());

					/*
					 * try { bufferedWriter.write(et.getText().toString()+"$");
					 * bufferedWriter.flush(); } catch (IOException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); }
					 */

					// send message to server

				}
				et.setText("");
				ll.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ll.setSelection(lva.getCount() - 1);
					}
				});

			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Servercommrecieve.removeuseractivity(touser);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void updatechat(Messageobject mo) {

		/*
		 * mo = new Messageobject(); mo.setMessage(message); mo.setPosition(0);
		 */
		messagearray.add(mo);
		lva.notifyDataSetChanged();
		ll.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ll.setSelection(lva.getCount() - 1);
			}
		});

	}
}
