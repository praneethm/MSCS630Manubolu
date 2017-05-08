package hymn.main.ui.chat;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ListViewadapter extends BaseAdapter {
	static Context co;
	ArrayList<Messageobject> moa;
	Messageobject mo;

	public ListViewadapter(Context co, ArrayList<Messageobject> moa) {
		this.co = co;
		this.moa = moa;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return moa.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		// TODO Auto-generated constructor stub
		public TextView tv = new TextView(co);
		public TextView tm = new TextView(co);
		public LinearLayout llh = new LinearLayout(co);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View myView = convertView;
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) co
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myView = inflater.inflate(R.layout.messagetemplate, parent, false);
			holder.tv = (TextView) myView.findViewById(R.id.inftex);
			myView.setTag(holder);
			holder.tm = (TextView) myView.findViewById(R.id.tmstamp);
			holder.llh = (LinearLayout) myView.findViewById(R.id.innerll);
			System.out.println("creating new getView");

		} else {

			holder = (ViewHolder) myView.getTag();
		}

		mo = new Messageobject();
		mo = moa.get(position);
		holder.tv.setText(mo.getMessage());
		System.out.println("adding this to text"+mo.getMessage());
		LayoutParams llp = new LayoutParams(holder.llh.getLayoutParams());
		LayoutParams lp = new LayoutParams(holder.tv.getLayoutParams());
		switch (mo.getPosition()) {

		case 0:
			llp.gravity = Gravity.LEFT;
			lp.gravity = Gravity.LEFT;
			holder.tv.setTextSize(15);
			holder.tv.setTextColor(Color.BLUE);
			break;
		case 1:
			llp.gravity = Gravity.RIGHT;
			lp.gravity = Gravity.RIGHT;
			holder.tv.setTextSize(15);
			holder.tv.setTextColor(Color.RED);
			break;
		case 2:
			llp.gravity = Gravity.CENTER;
			lp.gravity = Gravity.CENTER;
			holder.tv.setTextSize(10);
			holder.tv.setTextColor(Color.LTGRAY);
			
		}

		holder.tv.setLayoutParams(lp);
		holder.llh.setLayoutParams(llp);
		holder.tm.setText(mo.getDate().toString());
		holder.tm.setTextColor(Color.BLACK);

		// TODO Auto-generated method stub

		return myView;
	}

}
