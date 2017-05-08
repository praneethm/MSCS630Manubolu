package hymn.main.ui.main;

import java.io.UnsupportedEncodingException;

import main.security.init.CopyOfKeyGen;
import main.security.init.KeyGen;
import hymn.main.parser.MessageParserhandler;
import hymn.main.ui.chat.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Fragment {
	EditText PhoneNumber;
	EditText Password;
	EditText Name;
	Button submit;
	MainActivity activity;
	Editor editor;

	public Login(Editor editor) {
		this.editor = editor;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (MainActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.login, container, false);

		PhoneNumber = (EditText) rootView.findViewById(R.id.pno);
		Password = (EditText) rootView.findViewById(R.id.password);
		Name = (EditText) rootView.findViewById(R.id.name);
		submit = (Button) rootView.findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit.setTextColor(Color.GRAY);
				submit.setClickable(false);
				activity.Fragmentchange(2);
				activity.Fragmentchange(4);
				editor.putString("phonenumber", PhoneNumber.getText()
						.toString());
				editor.putString("password", Password.getText().toString());
				editor.putString("user", Name.getText().toString());
				CopyOfKeyGen.GenerateKeys();
				MessageParserhandler.parse("type:" + "validate" + " PH:"
						+ PhoneNumber.getText().toString() + " Pass:"
						+ Password.getText().toString() + " user:"
						+ Name.getText().toString() + " RSA:"
						+ CopyOfKeyGen.mod
						+ " exponent:" + CopyOfKeyGen.exponent);

				// MessageParserhandler.parse("type:"+"validate"+" PH:"+PhoneNumber.getText().toString()+" Pass:"+Password.getText().toString()+" user:"+Name.getText().toString()+" RSA:"+Base64.encode(KeyGen.getPKA().getEncoded(),
				// Base64.NO_WRAP));
				// MessageParserhandler.parse("type:"+"validate"+" PH:"+PhoneNumber.getText().toString()+" Pass:"+Password.getText().toString()+" user:"+Name.getText().toString()+" RSA:"+Base64.getEncoder().encodeToString(KeyGen.getPKA().getEncoded()));
				// MainActivity activity=(MainActivity) getActivity();

			}
		});
		return rootView;
		// TODO Auto-generated method stub

	}

}
