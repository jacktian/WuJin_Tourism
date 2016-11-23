package wujin.tourism.android.adapter;

import java.util.List;
import wujin.tourism.android.R;
import wujin.tourism.android.childactivity.MapActivity;
import wujin.tourism.android.childactivity.ZhuSuDetailActivity;
import wujin.tourism.android.common.detail_info;
import wujin.tourism.android.common.results;
import wujin.tourism.android.data.DialogUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ZhuSuAdapter extends BaseAdapter {
	private List<results> infos;
	private LayoutInflater mInflater;
	private Context context;
	ViewHolder holder;

	public ZhuSuAdapter(Context context, List<results> infos) {
		this.infos = infos;
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/***
	 * 返回列表数量
	 */
	public int getCount() {
		return infos.size();
	}

	/***
	 * 获取单个Item对应对象 返回positioin即可
	 */
	public Object getItem(int position) {
		return infos.get(position);
	}

	/***
	 * 获取单个Item对应Id
	 */
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(wujin.tourism.android.R.layout.activity_zhusu_item, null);
			holder = new ViewHolder();
			holder.tv_hotelname = (TextView) convertView.findViewById(R.id.tv_hotelname);
			holder.tv_pice = (TextView) convertView.findViewById(R.id.tv_pice);
			holder.tv_juli = (TextView) convertView.findViewById(R.id.tv_juli);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.btn_order = (Button) convertView.findViewById(R.id.btn_order);
			holder.btn_route = (Button) convertView.findViewById(R.id.btn_route);
			holder.btn_route_layout = (RelativeLayout) convertView.findViewById(R.id.btn_route_layout);
			holder.btn_order_layout = (RelativeLayout) convertView.findViewById(R.id.btn_order_layout);
			holder.ll_layout = (LinearLayout) convertView.findViewById(R.id.ll_layout);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			detail_info detail = infos.get(position).getDetail_info();
			if (infos.get(position).getName() != null) {
				holder.tv_hotelname.setText(infos.get(position).getName());
			}
			if (detail.getPrice() != null) {
				holder.tv_pice.setText("￥" + infos.get(position).getDetail_info().getPrice());
			} else {
				holder.tv_pice.setText("￥未知");
			}
			if (detail.getDistance() != null) {
				holder.tv_juli.setText(infos.get(position).getDetail_info().getDistance() + "米");
			} else {
				holder.tv_juli.setText("未知");
			}
			if (detail.getOverall_rating() != null) {
				holder.ratingBar.setVisibility(View.VISIBLE);
				holder.ratingBar.setRating(Float.valueOf(infos.get(position).getDetail_info().getOverall_rating()));
			} else {
				holder.ratingBar.setRating(0.0f);
				holder.ratingBar.setVisibility(View.GONE);
			}
			if (infos.get(position).getAddress() != null) {
				holder.tv_address.setText(infos.get(position).getAddress());
			} else {
				holder.tv_address.setText("");
			}
			if (infos.get(position).getTelephone() == null) {
				Drawable img_order = convertView.getResources().getDrawable(R.drawable.icon_open_telephone_disabled);
				img_order.setBounds(0, 0, img_order.getMinimumWidth(), img_order.getMinimumHeight());
				holder.btn_order.setCompoundDrawables(img_order, null, null, null);

			} else {
				Drawable img_order1 = convertView.getResources().getDrawable(R.drawable.icon_open_telephone);
				img_order1.setBounds(0, 0, img_order1.getMinimumWidth(), img_order1.getMinimumHeight());
				holder.btn_order.setCompoundDrawables(img_order1, null, null, null);
			}

			holder.btn_order_layout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (infos.get(position).getTelephone() == null) {
						Toast.makeText(context, "暂无该商家电话", Toast.LENGTH_LONG).show();
					} else {
						DialogUtils.showDialogPhone(context, "拨打", "取消", "预订电话:" + infos.get(position).getTelephone(),
								infos.get(position).getTelephone());

					}
				}
			});
			holder.btn_route_layout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent(context, MapActivity.class);
					Bundle bundle = new Bundle();
					bundle.putDouble("lat", infos.get(position).getLocation().getLat());
					bundle.putDouble("lng", infos.get(position).getLocation().getLng());
					bundle.putInt("type", 2);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
			holder.ll_layout.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (infos.get(position).getDetail_info().getDetail_url() == null) {
						Toast.makeText(context, "暂无该酒店详情！", Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(context, ZhuSuDetailActivity.class);
						intent.putExtra("DATA", infos.get(position));
						context.startActivity(intent);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_hotelname, tv_juli, tv_address, tv_pice;
		RatingBar ratingBar;
		Button btn_order, btn_route;
		RelativeLayout btn_route_layout, btn_order_layout;
		LinearLayout ll_layout;

	}
}
