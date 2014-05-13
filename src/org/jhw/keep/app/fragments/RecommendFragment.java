package org.jhw.keep.app.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.app.activities.ArticleActivity;
import org.jhw.keep.app.adapters.FragmentPagerAdapter;
import org.jhw.keep.app.adapters.MyFeedAdapter;
import org.jhw.keep.app.fragments.ChannelFragment.MyTask;
import org.jhw.keep.cache.ImageFetcher;
import org.jhw.keep.cache.ImageCache.ImageCacheParams;
import org.jhw.keep.datatype.MyFeedItem;
import org.jhw.keep.parser.FeedParser;
import org.jhw.keep.parser.FeedParserFactory;
import org.jhw.keep.parser.FeedResult;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.LayoutParams;

/**
 * 推荐
 * @author jhw
 *
 */
public class RecommendFragment extends Fragment implements ViewPager.OnPageChangeListener,android.widget.AdapterView.OnItemClickListener{
	
	private static final String IMAGE_CACHE_DIR = "thumbs";
	
	private ViewPager mPager;
	private FragmentPagerAdapter mPagerAdapter;
	private View mHeaderView;
	private MyFeedAdapter mAdapter;
	
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;
    private List<MyFeedItem> data = new ArrayList<MyFeedItem>();
    
    private void initImageCache() {
		// init image cache
		mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
		mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
		mImageFetcher.setLoadingImage(R.drawable.ati);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
		
	}
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImageCache();
		new MyTask().execute("http://yunmusic.lofter.com/rss");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.recommend_fragment, container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.drag_list);
		mHeaderView = LayoutInflater.from(getActivity()).inflate(
				R.layout.drag_list_headview, null);
		mHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,1084));
		listView.addHeaderView(mHeaderView);
		listView.setOnItemClickListener(this);
//		mAdapter = new MyFeedAdapter(getActivity(), data ,mImageFetcher);
//		listView.setAdapter(mAdapter);
		return rootView;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
	
	class MyTask extends AsyncTask<String, String, FeedResult> {

		@Override
		protected void onPostExecute(FeedResult result) {
			if (result != null) {
				Log.d("RecommendFragment", result.toString());
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.putExtra("title", data.get(position).getTitle()); 
		intent.putExtra("content", data.get(position).getContent()); 
		intent.putExtra("link", data.get(position).getLink());
//		intent.setClass(getActivity(), ReadActivity.class);
		intent.setClass(getActivity(), ArticleActivity.class);
		startActivity(intent);		
	}
}
