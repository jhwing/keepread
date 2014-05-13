package org.jhw.keep.app.adapters;

import org.jhw.keep.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCoverAdapter extends BaseAdapter {

	// 定义Context
	 private final Context mContext;
     private int mItemHeight = 0;
     private int mNumColumns = 0;
     private int mActionBarHeight = 0;
	 LayoutInflater mInflater;
	 GridView.LayoutParams mImageViewLayoutParams;
	
	static class ViewHolder {
		TextView item_title;
		ImageView item_img;
	}
	
	public int getmItemHeight() {
		return mItemHeight;
	}

	public void setmItemHeight(int mItemHeight) {
		this.mItemHeight = mItemHeight;
	}

	public int getmNumColumns() {
		return mNumColumns;
	}

	public void setmNumColumns(int mNumColumns) {
		this.mNumColumns = mNumColumns;
	}

	public int getmActionBarHeight() {
		return mActionBarHeight;
	}

	public void setmActionBarHeight(int mActionBarHeight) {
		this.mActionBarHeight = mActionBarHeight;
	}

	// 定义整型数组 即图片源
	private Integer[] mImageIds = { R.drawable.earth, R.drawable.mars,
			R.drawable.mercury, R.drawable.jupiter, R.drawable.uranus,
			R.drawable.neptune, R.drawable.saturn, R.drawable.venus,
			R.drawable.ati, R.drawable.loading };

	public MyCoverAdapter(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mImageViewLayoutParams = new GridView.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) {
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics());
        }
	}

	@Override
	public int getCount() {
		return mImageIds.length;
	}

	@Override
	public Object getItem(int position) {
		return position ;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
//			imageView = new RecyclingImageView(mContext);
			convertView = mInflater.inflate(R.layout.griditem111, null);
			holder.item_img = (ImageView) convertView.findViewById(R.id.cover_img);
			holder.item_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LayoutParams layoutParams =  holder.item_img.getLayoutParams();
		// Check the height matches our calculated column width
//        if (holder.item_img.getLayoutParams().height != mItemHeight) {
//        	holder.item_img.setLayoutParams(layoutParams);
//        }
        layoutParams.height = 250;
        layoutParams.width = 350;
		holder.item_img.setLayoutParams(layoutParams);
		holder.item_img.setImageResource(mImageIds[position]);
		return convertView;
	}

}
