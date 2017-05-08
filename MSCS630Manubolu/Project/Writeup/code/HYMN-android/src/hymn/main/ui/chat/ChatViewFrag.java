package hymn.main.ui.chat;

import hymn.main.parser.MessageParserhandler;
import hymn.main.server.Servercommrecieve;

import java.io.BufferedWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import main.security.init.AES;
import main.security.init.CopyOfKeyGen;
import main.security.init.KeyGen;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ChatViewFrag extends Fragment {

	String touser;

	public String getUser() {
		return touser;
	}

	ListView ll;
	ListViewadapter lva;
	ArrayList<Messageobject> messagearray = new ArrayList<Messageobject>();
	EditText et;
	Messageobject mo;
	BufferedWriter bufferedWriter;
	ImageButton submit;
	String from;
	SecretKey AESKEY;
	PublicKey RSAPub;
	boolean created = true;

	public ChatViewFrag(String touser, String from, String mod, String exponent) {
		this.touser = touser;
		this.from = from;
		this.RSAPub = setPublicRSA(mod, exponent);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.chatview, container, false);
		getActivity().getActionBar().setTitle(touser);
		getActivity().getActionBar().setSubtitle("Active");
		Servercommrecieve.adduseractivity(touser, this);

		if (created) {
			mo = new Messageobject();
			mo.setMessage("welcome");
			mo.setPosition(2);
			messagearray.add(mo);
			created = false;
		}
		ll = (ListView) rootView.findViewById(R.id.ll);
		lva = new ListViewadapter(getActivity(), messagearray);
		ll.setAdapter(lva);
		et = (EditText) rootView.findViewById(R.id.ed);
		submit = (ImageButton) rootView.findViewById(R.id.submit);

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
					System.out.println("message that is going to be sent"
							+ et.getText().toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "message");
					map.put("to", touser);
					map.put("from", from);
;
					System.out.println(" AES KEY "+String.format("%032X", new BigInteger(+1,AESKEY.getEncoded())));
/*					map.put("hash",
							AES.AESEncrypt(
									KeyGen.getHashfile(et.getText().toString()),
									String.format("%032X", new BigInteger(+1,AESKEY.getEncoded()))));*/
					map.put("hash",
							KeyGen.AESdataEncrypt(
									KeyGen.getHashfile(et.getText().toString()),
									AESKEY));
					if (null != AESKEY) {

						map.put("text", KeyGen.AESdataEncrypt(et.getText()
								.toString(), AESKEY));
						// map.put("text",
						 //AES.AESEncrypt(et.getText().toString(),
							//	 String.format("%032X", new BigInteger(+1,AESKEY.getEncoded()))));

					} else
						map.put("text", "Not Authenticated");
					MessageParserhandler.parse(map);

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
		return rootView;
	}

	public void updatechat(Messageobject mo, String encMessage, String encHash) {
		String hash = KeyGen.AESdataDecrypt(encHash, AESKEY);
		String message = KeyGen.AESdataDecrypt(encMessage, AESKEY);
		// String hash=AES.AESDecrypt(encHash,
			//	 String.format("%032X", new BigInteger(+1,AESKEY.getEncoded())));
	//	 String message = AES.AESDecrypt(encMessage,
		//		 String.format("%032X", new BigInteger(+1,AESKEY.getEncoded())));
		System.out.println(" comparing hash "
				+ hash.equalsIgnoreCase(KeyGen.getHashfile(message)));
		// KeyGen.getHashfile(hash);
		if (hash.equalsIgnoreCase(KeyGen.getHashfile(message)))
			mo.setMessage(message);
		// mo.setMessage(message);

		// mo.setMessage(Base64.encodeToString(AESKEY.getEncoded(),
		// Base64.DEFAULT));

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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Servercommrecieve.removeuseractivity(touser);
	}

	public void Secure() {
		// TODO Auto-generated method stub
		if (null == AESKEY) {

			AESKEY = CopyOfKeyGen.GenerateASEKey();
			System.out
					.println("generated AES key"
							+ Base64.encodeToString(AESKEY.getEncoded(),
									Base64.DEFAULT));
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "Secure");
			map.put("to", touser);
			map.put("from", from);
			map.put("AES", CopyOfKeyGen.RSAEncrypt(RSAPub, AESKEY));
			MessageParserhandler.parse(map);

		}

	}

	public void setAESKEY(String AESKEY) {
		this.AESKEY = CopyOfKeyGen.RSADecrypt(AESKEY);
	}

	public static PublicKey setPublicRSA(String mod, String exponent) {

		System.out.println("recieved to other client");
		System.out.println(exponent);
		System.out.println(mod);

		BigInteger m = new BigInteger(mod);
		BigInteger ex = new BigInteger(exponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, ex);
		KeyFactory fact;

		RSAPublicKey pub = null;
		try {
			fact = KeyFactory.getInstance("RSA");

			pub = (RSAPublicKey) fact.generatePublic(keySpec);
			System.out.println("after creating back");
			System.out.println("mod " + pub.getModulus());
			System.out.println("mod " + pub.getPublicExponent());
			return pub;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) { // TODO
																			// Auto-generated
																			// catch
																			// block
			e.printStackTrace();
		}

		return pub;

		/*
		 * byte[] decodedKey = Base64.decode(mod, 0); X509EncodedKeySpec
		 * X509publicKey = null; X509publicKey = new
		 * X509EncodedKeySpec(decodedKey); KeyFactory kf; try { kf =
		 * KeyFactory.getInstance("RSA"); // return
		 * kf.generatePublic(X509publicKey); RSAPublicKey pub = (RSAPublicKey)
		 * kf.generatePublic(X509publicKey);
		 * System.out.println("after creating back"); System.out.println("mod "
		 * + pub.getModulus()); System.out.println("mod " +
		 * pub.getPublicExponent()); return pub; } catch
		 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InvalidKeySpecException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } return null;
		 */

	}

}
