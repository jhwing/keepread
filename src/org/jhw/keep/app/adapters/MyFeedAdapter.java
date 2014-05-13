package org.jhw.keep.app.adapters;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jhw.keep.R;
import org.jhw.keep.datatype.MyFeedItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MyFeedAdapter extends BaseAdapter{

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;
	private List<MyFeedItem> mData;
	private final LayoutInflater mInflater;
	static class ViewHolder {
		TextView item_title;
		TextView item_content;
		TextView item_time;
		ImageView item_img;
	}
	
	public MyFeedAdapter(Context context,List<MyFeedItem> data,ImageLoader imageLoader,DisplayImageOptions options) {
		mImageLoader = imageLoader;
		mOptions = options;
		mData = data;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.myfeeditem, null);
			holder = new ViewHolder();
			holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
			holder.item_content = (TextView) convertView.findViewById(R.id.item_description_thumbnail_content);
			holder.item_time = (TextView) convertView.findViewById(R.id.item_description_thumbnail_content_time);
			holder.item_img = (ImageView) convertView.findViewById(R.id.item_description_thumbnail_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.item_title.setText(mData.get(position).getTitle());
		holder.item_content.setText(mData.get(position).getSummary());
		holder.item_time.setText(mData.get(position).getPubDate());
		String imgUrl = mData.get(position).getThumbnailImage();
		if (TextUtils.isEmpty(imgUrl)) {
			holder.item_img.setVisibility(View.GONE);
		} else {
			mImageLoader.displayImage(imgUrl, holder.item_img, mOptions, animateFirstListener);
		}
		Log.d("MyFeedAdapter", "MyFeedAdapter getView.." + position);
		return convertView;
	}
	
	public void refresh(List<MyFeedItem> data) {
		 mData = data;
		 notifyDataSetChanged();
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
}
