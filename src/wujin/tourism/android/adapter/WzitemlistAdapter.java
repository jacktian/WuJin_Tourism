package wujin.tourism.android.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.R;
import wujin.tourism.android.childactivity.WzitemdetailActivity;
import wujin.tourism.android.common.ViewHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WzitemlistAdapter extends BaseAdapter {
	private Context m_context;
	private ArrayList<HashMap<String, Object>> listviewitems;
	private FinalBitmap fb;

	public WzitemlistAdapter(Context context, ArrayList<HashMap<String, Object>> listviewitems) {
		this.m_context = context;
		fb = FinalBitmap.create(m_context);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
		this.listviewitems = listviewitems;
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
			convertView = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.wzlistviewitem, null);
			item.contenttitle = (TextView) convertView.findViewById(R.id.wzitemlistviewtitle);
			item.name = (TextView) convertView.findViewById(R.id.wzitemlistviewimage);
			convertView.setTag(item);
		}
		if (listviewitems.get(position).get("imageUrl").toString().length() > 5) {
			item.name.setVisibility(View.VISIBLE);
			fb.display(item.name, listviewitems.get(position).get("imageUrl").toString());
		} else {
			item.name.setVisibility(View.GONE);
		}
		item.contenttitle.setText(listviewitems.get(position).get("title").toString());
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(m_context, WzitemdetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("imageUrl", listviewitems.get(position).get("imageUrl").toString());
				bundle.putString("title", listviewitems.get(position).get("title").toString());
				bundle.putString("phone", listviewitems.get(position).get("phone").toString());
				bundle.putString("description", listviewitems.get(position).get("description").toString());
				bundle.putString("address", listviewitems.get(position).get("address").toString());
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				m_context.startActivity(intent);
			}
		});
		return convertView;
	}
}
