package org.jhw.keep.app.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.R;
import org.jhw.keep.app.activities.ArticleActivity;
import org.jhw.keep.app.activities.MainActivity;
import org.jhw.keep.app.adapters.MyFeedAdapter;
import org.jhw.keep.app.adapters.RssAddAdapter;
import org.jhw.keep.app.task.AsyncRefreshListener;
import org.jhw.keep.app.task.FeedListTask;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.datatype.MyFeedItem;
import org.jhw.keep.db.ContentLibDao;
import org.jhw.keep.db.MyFeedDao;
import org.jhw.keep.parser.FeedResult;
import org.jhw.keep.views.SlidingUpPanelView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 我的订阅
 * 
 * @author jihongwen
 */
public class MyFeedFragment extends Fragment implements
		AsyncRefreshListener<FeedResult> {

	private boolean emptyLayout = true;
	private String channelUrl = "";

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	private List<MyFeedItem> mData = new ArrayList<MyFeedItem>();
	private MyFeedAdapter mAdapter;
	private FeedListTask mTask;
	private PullToRefreshListView mPullRefreshListView;
	private ListView listView;
	private ListView myChannelListView;
	private ImageView imageView;
	
    private int screenWidth = 0;
    private int screenHeight = 0;
    Button cancel;
	SQLiteDatabase db;
	ContentLibDao contentLibDao;
	MyFeedDao myFeedDao;
	RssAddAdapter mRssAdapter;
	List<MyFeed> mRssData = new ArrayList<MyFeed>();
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = getActivity().openOrCreateDatabase("keep.db", 0, null);
		contentLibDao = new ContentLibDao(db);
		myFeedDao = new MyFeedDao(db);
		
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ati)
				.showImageOnFail(R.drawable.ati).resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		mAdapter = new MyFeedAdapter(getActivity(), mData, imageLoader, options);
		/*
		 * 要显示的频道信息 channel url 如果channel url为空，显示默认界面
		 */
		Bundle bundle = getArguments();
		if (bundle != null) {
			channelUrl = bundle.getString("channelurl");
			if (!TextUtils.isEmpty(channelUrl)) {
				emptyLayout = false;
			}
		}
	}

	private void getData(String channelUrl) {
		mTask = new FeedListTask();
		mTask.setAsyncRefreshListener(this);
		mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, channelUrl);
	}

	class MyTask extends AsyncTask<String, String, List<MyFeed>> {

		@Override
		protected void onPostExecute(List<MyFeed> result) {
			mRssData.clear();
			mRssData.addAll(result);
			mRssAdapter.refresh(mRssData);
		}
		
		@Override
		protected List<MyFeed> doInBackground(String... params) {
			return myFeedDao.getAllHasFeed();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (emptyLayout) {
			View rootView = inflater.inflate(R.layout.myfeed, container, false);
			return rootView;
		}

		View rootView = inflater.inflate(R.layout.myfeed, container, false);
		final SlidingUpPanelView panelView = (SlidingUpPanelView) rootView.findViewById(R.id.slingup_view);
		panelView.setPanelSlideListener(new org.jhw.keep.views.SlidingUpPanelView.PanelSlideListener() {
			
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				Toast.makeText(getActivity(), "onPanelSlide", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPanelExpanded(View panel) {
				Toast.makeText(getActivity(), "onPanelExpanded", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPanelCollapsed(View panel) {
				Toast.makeText(getActivity(), "onPanelCollapsed", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPanelAnchored(View panel) {
				Toast.makeText(getActivity(), "onPanelAnchored", Toast.LENGTH_SHORT).show();
			}
		});
		myChannelListView = (ListView) rootView.findViewById(R.id.mychannel_list);
		mRssAdapter = new RssAddAdapter(getActivity(), R.layout.rss_add_item, mRssData);
		mRssAdapter.setMyFeedDao(myFeedDao);
		myChannelListView.setAdapter(mRssAdapter);
		myChannelListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "onItemClick", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), MainActivity.class);
				intent.putExtra("channelurl", mRssData.get(position).getLinkSelf());
				startActivity(intent);
			}
		});
		new MyTask().execute("");
//		imageView = (ImageView) rootView.findViewById(R.id.guess_img);
//		imageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
////				initPopupWindow();
//			}
//		});

		mPullRefreshListView = (PullToRefreshListView) rootView
				.findViewById(R.id.myfeed_list);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						getData(channelUrl);
					}
				});
		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
					}
				});

		listView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(listView);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				int size = mData.size();
				position--;
				if (size >= position && position >= 0) {
					Intent intent = new Intent();
					intent.putExtra("title", mData.get(position).getTitle());
					intent.putExtra("content", mData.get(position).getContent());
					intent.putExtra("link", mData.get(position).getLink());
					intent.setClass(getActivity(), ArticleActivity.class);
					startActivity(intent);
				}
			}
		});
		getData(channelUrl);
		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTask.cancel(true);
	}

	@Override
	public void onAsyncRefreshComplete(FeedResult result) {
		mPullRefreshListView.onRefreshComplete();
		if (result != null) {
			mData.clear();
			mData.addAll(result.items);
			mAdapter.refresh(mData);
		}
	}

}
