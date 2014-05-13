package org.jhw.keep.app.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.Constants;
import org.jhw.keep.R;
import org.jhw.keep.app.activities.ReadActivity;
import org.jhw.keep.app.adapters.FeedAdapter;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.db.MyDbManager;
import org.jhw.keep.db.MyFeedDao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Feed订阅主界面 显示订阅的频道信息 list形式
 * 
 * @author jhw
 * 
 */
public class FeedFragment extends Fragment implements OnItemClickListener{

	List<MyFeed> mData = new ArrayList<MyFeed>();

	FeedAdapter mAdapter;
	MyDbManager dbManager;
	MyFeedDao feedDao;
	
	public FeedFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initListDate();
	}

	@Override
	public void onResume() {
		super.onResume();
//		new MyTask().execute("");
	}
	
	private void initListDate() {
		dbManager = new MyDbManager(getActivity(),"keep.db",null);
		feedDao = new MyFeedDao(dbManager.getDb());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.myfeed, container, false);
//		ListView listView = (ListView) rootView.findViewById(R.id.feed_list);
//		mAdapter = new FeedAdapter(getActivity(), R.layout.feeditem, mData);
//		listView.setAdapter(mAdapter);
//		listView.setOnItemClickListener(this);
		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		if (dbManager != null) {
			dbManager.clear();
		}
		super.onDestroy();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyFeed feed = mData.get(position);
		Intent intent = new Intent(getActivity(), ReadActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("feed", feed);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	class MyTask extends AsyncTask<String, String, List<MyFeed>> {

		@Override
		protected void onPostExecute(List<MyFeed> result) {
			if (result != null) {
				mData.clear();
				mData.addAll(result);
				Log.e(Constants.LOG_TAG, mData + "");
				mAdapter.refresh(mData);
			}
		}
		
		@Override
		protected List<MyFeed> doInBackground(String... params) {
			return feedDao.getAllHasFeed();
		}
	}
}
