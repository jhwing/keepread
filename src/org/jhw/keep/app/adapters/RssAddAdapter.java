package org.jhw.keep.app.adapters;

import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.db.MyFeedDao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RssAddAdapter extends CommonAdapter<MyFeed>{

	private boolean isEX = false;
	private MyFeedDao myFeedDao;

	public MyFeedDao getMyFeedDao() {
		return myFeedDao;
	}

	public void setMyFeedDao(MyFeedDao myFeedDao) {
		this.myFeedDao = myFeedDao;
	}

	static class MyHolder extends CommonAdapter.ViewHolder {
		TextView title;
		Button add;
	}

	public RssAddAdapter(Context context, int resource, List<MyFeed> data) {
		super(context, resource, data, new MyHolder());

		if (R.layout.rss_add_item_ex == resource) {
			isEX = true;
		}
	}

	public boolean getIsEX() {
		return isEX;
	}

	@Override
	public void initView(View view, int position,
			org.jhw.keep.app.adapters.CommonAdapter.ViewHolder holder) {
		MyHolder holder2 = (MyHolder) holder;
		holder2.title = (TextView) view.findViewById(R.id.rss_add_item_title);
		if (isEX) {
			ImageView imageView = (ImageView) view
					.findViewById(R.id.rss_add_item_next);
			imageView.setOnClickListener(new MyClickListener(mData
					.get(position)));
		}

	}

	class MyClickListener implements OnClickListener {

		MyFeed feed;

		public MyClickListener(MyFeed feed) {
			super();
			this.feed = feed;
		}

		@Override
		public void onClick(View v) {
			ImageView imageView = (ImageView) v;
			Log.e(Constants.LOG_TAG, "feed onclick " + feed);
			if (feed.isHasAdd()) {
				feed.setHasAdd(false);
				myFeedDao.update(feed);
				imageView.setImageResource(R.drawable.channellist_noadd_icon);
			} else {
				feed.setHasAdd(true);
				myFeedDao.update(feed);
				imageView.setImageResource(R.drawable.channellist_hasadd_icon);
			}
		}
	}

	@Override
	public void setView(View view, int position, List<MyFeed> mData,
			org.jhw.keep.app.adapters.CommonAdapter.ViewHolder holder) {
		MyHolder holder2 = (MyHolder) holder;
		holder2.title.setText(mData.get(position).getTitle());
		if (isEX) {
			ImageView imageView = (ImageView) view
					.findViewById(R.id.rss_add_item_next);
			if (mData.get(position).isHasAdd()) {
				imageView.setImageResource(R.drawable.channellist_hasadd_icon);
			} else {
				imageView.setImageResource(R.drawable.channellist_noadd_icon);
			}
		}
	}
}
