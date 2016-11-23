package wujin.tourism.android.adapter;

import java.util.List;
import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.R;
import wujin.tourism.android.common.ViewHolder;
import wujin.tourism.android.tpc.Wzglbean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Wzgladapter extends BaseAdapter {
	private Context m_context;
	private List<Wzglbean> listviewitems;
	private int highth = 0;
	private FinalBitmap fb;

	public Wzgladapter(Context context, List<Wzglbean> listviewitems) {
		this.m_context = context;
		this.listviewitems = listviewitems;
		highth = m_context.getResources().getDisplayMetrics().heightPixels / 3;
		fb = FinalBitmap.create(m_context);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
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
			convertView = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.wzgllititem, null);
			item.button = (TextView) convertView.findViewById(R.id.imageback);
			item.contenttitle = (TextView) convertView.findViewById(R.id.viewpage1);
			item.button.setHeight(highth);
			convertView.setTag(item);
		}
		try {
			Wzglbean Wzglbean = listviewitems.get(position);
			fb.display(item.button, Wzglbean.getAlbum());
			item.contenttitle.setText(Wzglbean.getName());
		} catch (Exception e) {
		}
		return convertView;
	}
}