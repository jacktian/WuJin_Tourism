package wujin.tourism.android.adapter;

import wujin.tourism.android.R;
import wujin.tourism.android.common.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MaingridViewadapter extends BaseAdapter {
	private Context m_context;

	private int[] imagetoplsit = { R.drawable.newjd, R.drawable.newms, R.drawable.newjiud, R.drawable.newhd,
			R.drawable.newtc, R.drawable.newgw, R.drawable.newgl, R.drawable.newgd };
	private String[] listStrings = { "景点", "美食", "酒店", "娱乐", "特产", "购物", "交通", "更多" };

	public MaingridViewadapter(Context context) {
		this.m_context = context;
	}

	public int getCount() {
		return imagetoplsit.length;
	}

	public Object getItem(int positon) {
		return positon;
	}

	public long getItemId(int id) {
		return id;
	}

	public View getView(final int position, View convertView, ViewGroup viewgroup) {
		final ViewHolder item;
		if (convertView != null) {
			item = (ViewHolder) convertView.getTag();
		} else {
			item = new ViewHolder();
			convertView = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.maingridviewitem, null);
			item.contenttitle = (TextView) convertView.findViewById(R.id.maincion);
			item.countnum = (TextView) convertView.findViewById(R.id.maintitle);
			convertView.setTag(item);
		}
		try {
			item.contenttitle.setBackgroundResource(imagetoplsit[position]);
			item.countnum.setText(listStrings[position]);
		} catch (Exception e) {
		}
		return convertView;
	}
}
