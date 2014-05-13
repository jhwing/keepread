package org.jhw.keep.app.adapters;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.R;
import org.jhw.keep.app.activities.RssAddActivity;
import org.jhw.keep.datatype.MyFeed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SlidingMainListAdapter extends BaseAdapter implements View.OnClickListener {

	private Context mContext;
	private List<MyFeed> mData = new ArrayList<MyFeed>();
	private final LayoutInflater mInflater;

	static class ViewHolder {
		TextView item_title;
		Button button;
	}

	public SlidingMainListAdapter(Context context) {
		mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
			holder.item_title = (TextView) convertView.findViewById(R.id.drawer_list_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyFeed item =  mData.get(position);
		holder.item_title.setText(item.getTitle());
		return convertView;
	}

	public void refresh(List<MyFeed> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(mContext, RssAddActivity.class);
		mContext.startActivity(intent);
	}

}
