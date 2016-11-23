package wujin.tourism.android.adapter;

import java.util.ArrayList;
import java.util.List;
import wujin.tourism.android.R;
import wujin.tourism.android.childactivity.PublicwebActivtiy;
import wujin.tourism.android.common.ViewHolder;
import wujin.tourism.android.common.mainnewsbean;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Mainlistadapter extends BaseAdapter {
	private Context m_context;
	private List<mainnewsbean> arrayList = new ArrayList<mainnewsbean>();

	public Mainlistadapter(Context context, List<mainnewsbean> arrayList) {
		this.m_context = context;
		this.arrayList = arrayList;
	}

	public int getCount() {
		return arrayList.size();
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
			convertView = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.mainnewsitem, null);
			item.contenttitle = (TextView) convertView.findViewById(R.id.newsitemtitle);
			item.countnum = (TextView) convertView.findViewById(R.id.newsitemtime);
			convertView.setTag(item);
		}
		try {
			final mainnewsbean mainnewsbean = arrayList.get(position);
			item.contenttitle.setText(mainnewsbean.getTitle());
			item.countnum.setText("时间：" + mainnewsbean.getTime());

			convertView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(m_context, PublicwebActivtiy.class);
					Bundle bundle = new Bundle();
					bundle.putString("title", mainnewsbean.getTitle());
					bundle.putString("url", "http://112.21.190.22/zx/wapnew.action?id=" + mainnewsbean.getId());
					intent.putExtras(bundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					m_context.startActivity(intent);
				}
			});

		} catch (Exception e) {
		}
		return convertView;
	}
}
