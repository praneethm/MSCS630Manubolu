package hymn.main.ui.main;


import hymn.main.server.Servercommrecieve;
import hymn.main.ui.chat.ChatView;
import hymn.main.ui.chat.ChatViewFrag;
import hymn.main.ui.chat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.security.init.KeyGen;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UsersList extends Fragment{

	ListView lv;
	ProgressBar pb;
	ArrayList<String> list=new ArrayList<String>();
	BaseAdapter baseadapter;
	MainActivity activity;
	//MessageParserhandler mps;
	Servercommrecieve scr;
	boolean scradded=false;
	static String Username; 
	Map<String, String> usernumber = new HashMap<String, String>();
	Map<String, ChatViewFrag> intentarr = new HashMap<String, ChatViewFrag>();
	private Editor editor;
	KeyGen keyGen;
	//LinkedList<String> list=new LinkedList<String>();
	
	public UsersList(Editor editor) {
		// TODO Auto-generated constructor stub
		this.editor=editor;
	}

	public void addscr(Servercommrecieve scr){
		this.scr=scr;
		scradded=true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list.add("No users");
		View rootView = inflater.inflate(R.layout.userslist, container,false);
		lv=(ListView) rootView.findViewById(R.id.userslist);
		pb=(ProgressBar) rootView.findViewById(R.id.progressBar1);	
		getActivity().getActionBar().setTitle("Friends");
		//getActivity().getActionBar().setSubtitle("");
		baseadapter=new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View MyView=convertView;
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				MyView=inflater.inflate(R.layout.userslisttemplate, parent,false);
				TextView tv =(TextView) MyView.findViewById(R.id.user);
				Object var=list.get(position);
				tv.setText(var.toString());
				return MyView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return list.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
			

		};
		lv.setAdapter(baseadapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//String touser=lv.getItemAtPosition(position).toString();
			/*	Intent intent=intentarr.get(lv.getItemAtPosition(position).toString());
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);*/
				System.out.println("name of clicked item"+lv.getItemAtPosition(position).toString());
				ChatViewFrag cvf=intentarr.get(lv.getItemAtPosition(position).toString());
				System.out.println("selected fragment "+cvf.getUser());
				activity.setcvf(cvf);
				cvf.Secure();
				
				// TODO Auto-generated method stub
				
			}
			
		});
		
				return rootView;
		// TODO Auto-generated method stub
		
	}
	
	public void updatelist(JSONArray ja){
		editor.commit();
		pb.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);		
		list.remove("No users");
		list =new ArrayList<String>();
		baseadapter.notifyDataSetChanged();
		for(int i=0;i<ja.size();i++){
			JSONObject or=(JSONObject) ja.get(i);
			if(!Username.equalsIgnoreCase((String) or.get("name"))){
			list.add((String) or.get("name"));
			ChatViewFrag cvf=new ChatViewFrag(or.get("name").toString(), Username,or.get("mod").toString(),or.get("exponent").toString());
			intentarr.put(or.get("name").toString(), cvf);
/*			list.add(ja.get(i).toString());
			ChatViewFrag cvf=new ChatViewFrag(ja.get(i).toString(), Username);
			intentarr.put(ja.get(i).toString(), cvf);*/
			}
		}

/*		for(int i=0;i<userlist.length;i++){
			list.add(userlist[i]);
		}*/
		baseadapter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=(MainActivity) activity;
		
	}

	public void faillogin(){

		activity.Fragmentchange(1);

	}
	public void updateuserinfo(JSONObject jobj){
		
		
		Username=jobj.get("username").toString();
		//keyGen.GenerateKeys();
		
		
	}

	public void updateKey(String user, String key) {
		
		ChatViewFrag cvf=intentarr.get(user);
		cvf.setAESKEY(key);
		// TODO Auto-generated method stub
		
	}
	

	
	
}
