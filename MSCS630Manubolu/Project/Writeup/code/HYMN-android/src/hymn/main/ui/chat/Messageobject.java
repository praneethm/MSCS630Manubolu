package hymn.main.ui.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Messageobject {
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
		String time1 = sdf.format(dt);
		setDate(time1);
		this.message = message;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	String message;
	int position;
	String date;

}
