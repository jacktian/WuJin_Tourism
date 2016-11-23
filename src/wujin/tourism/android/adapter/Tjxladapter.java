package wujin.tourism.android.adapter;

import java.util.ArrayList;
import wujin.tourism.android.R;
import wujin.tourism.android.common.ViewHolder;
import wujin.tourism.android.tpc.Timestreambean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Tjxladapter extends BaseAdapter {
	private Context m_context;
	private ArrayList<Timestreambean> listviewitems;
	private int highth = 0;

	public Tjxladapter(Context context, ArrayList<Timestreambean> listviewitems) {
		this.m_context = context;
		this.listviewitems = listviewitems;
		highth = m_context.getResources().getDisplayMetrics().heightPixels;
	}

	public int getCount() {
		return listviewitems.size();
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
			convertView = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.listitem, null);
			item.button = (TextView) convertView.findViewById(R.id.imageurl);
			LayoutParams lp = item.button.getLayoutParams();
			lp.height = highth / 4;
			item.button.setLayoutParams(lp);
			item.countnum = (TextView) convertView.findViewById(R.id.name);
			item.contenttitle = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(item);
		}
		try {
			item.countnum.setText(listviewitems.get(position).getTextcontent());
			item.contenttitle.setText("推荐" + listviewitems.get(position).getTime());
			item.button.setBackgroundResource(listviewitems.get(position).getImage());
		} catch (Exception e) {
		}

		return convertView;
	}
}