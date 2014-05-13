package org.jhw.keep.app.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	protected int mResource;
	protected final LayoutInflater mInflater;
	ViewHolder mHolder;
	List<T> mData;

	public CommonAdapter(Context context, int resource, List<T> data,
			ViewHolder holder) {
		mResource = resource;
		mData = data;
		mHolder = holder;
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
		if (convertView == null) {
			convertView = mInflater.inflate(mResource, null);
			initView(convertView, position, mHolder);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		setView(convertView, position, mData, mHolder);
		Log.d("MyFeedAdapter", "MyFeedAdapter getView.." + position);
		return convertView;
	}

	public void refresh(List<T> data) {
		notifyDataSetChanged();
	}

	public static class ViewHolder {
	};

	public abstract void initView(View view, int position, ViewHolder holder);

	public abstract void setView(View view, int position, List<T> mData,
			ViewHolder holder);

}
