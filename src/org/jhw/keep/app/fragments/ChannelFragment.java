package org.jhw.keep.app.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.app.activities.ArticleActivity;
import org.jhw.keep.app.activities.BaseActivity;
import org.jhw.keep.app.adapters.MyFeedAdapter;
import org.jhw.keep.cache.ImageCache.ImageCacheParams;
import org.jhw.keep.cache.ImageFetcher;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.datatype.MyFeedItem;
import org.jhw.keep.parser.FeedParser;
import org.jhw.keep.parser.FeedParserFactory;
import org.jhw.keep.parser.FeedResult;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChannelFragment extends ListFragment {
	
	private DisplayImageOptions options;
    
    private MyFeed mFeed;
    private List<MyFeedItem> data = new ArrayList<MyFeedItem>();
    MyFeedAdapter mAdapter;
	
	public ChannelFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ati)
			.showImageOnFail(R.drawable.ati)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();
		initList();
		Bundle bundle = getArguments();
//		mFeed = (MyFeed) bundle.getSerializable("feed");
		new MyTask().execute(Constants.ATOM_36kr);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("title", data.get(position).getTitle()); 
		intent.putExtra("content", data.get(position).getContent()); 
		intent.putExtra("link", data.get(position).getLink());
//		intent.setClass(getActivity(), ReadActivity.class);
		intent.setClass(getActivity(), ArticleActivity.class);
		startActivity(intent);
		
	}
	
	private void initList() {
		mAdapter = new MyFeedAdapter(getActivity(), data , ImageLoader.getInstance(),options);
		setListAdapter(mAdapter);
	}

	private void initImageCache() {
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ati)
			.showImageOnFail(R.drawable.ati)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private void fetchData () {
		new MyTask().execute(mFeed.getLinkSelf());
	}
	
	class MyTask extends AsyncTask<String, String, FeedResult> {

		@Override
		protected void onPostExecute(FeedResult result) {
			if (result != null) {
				Log.d("CoverFragment", result.toString());
				data.clear();
				data.addAll(result.items);
				mAdapter.refresh(data);
			}
		}
		
		@Override
		protected FeedResult doInBackground(String... params) {
			
			FeedParser feedParser = FeedParserFactory.newInstance().newFeedParser();
			try {
				return feedParser.parse(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
