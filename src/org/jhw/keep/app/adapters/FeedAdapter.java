package org.jhw.keep.app.adapters;

import java.util.List;

import org.jhw.keep.R;
import org.jhw.keep.app.adapters.CommonAdapter.ViewHolder;
import org.jhw.keep.datatype.MyFeed;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedAdapter extends CommonAdapter<MyFeed> {

	public FeedAdapter(Context context, int resource,
			List<MyFeed> data) {
		super(context, resource, data, null);
	}

	class MyHolder extends CommonAdapter.ViewHolder {
		TextView title;
		TextView url;
		ImageView imageView;
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = mInflater.inflate(mResource, null);
			initView(convertView, position, holder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setView(convertView, position, mData, holder);
		Log.d("MyFeedAdapter", "MyFeedAdapter getView.." + position);
		return convertView;
	}
	
	@Override
	public void initView(View view, int position,
			org.jhw.keep.app.adapters.CommonAdapter.ViewHolder holder) {
		MyHolder holder2 = (MyHolder) holder;
		holder2.title = (TextView) view.findViewById(R.id.feed_item_title);
		holder2.url = (TextView) view.findViewById(R.id.feed_item_url);
	}

	@Override
	public void setView(View view, int position,
			List<MyFeed> mData,
			org.jhw.keep.app.adapters.CommonAdapter.ViewHolder holder) {
		MyHolder holder2 = (MyHolder) holder;
		holder2.title.setText(mData.get(position).getTitle());
		holder2.url.setText(mData.get(position).getLinkSelf());
	}

}
