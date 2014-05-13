package org.jhw.keep.app.activities;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.R;
import org.jhw.keep.app.adapters.RssAddAdapter;
import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.db.ContentLibDao;
import org.jhw.keep.db.MyFeedDao;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class RssAddActivity extends Activity implements AdapterView.OnItemClickListener{
	
	SQLiteDatabase db;
	ContentLibDao contentLibDao;
	MyFeedDao myFeedDao;
	RssAddAdapter mAdapter;
	List<MyFeed> mData = new ArrayList<MyFeed>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_add);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView listView = (ListView) findViewById(R.id.rss_add_list);
		listView.setOnItemClickListener(this);
		db = openOrCreateDatabase("keep.db", 0, null);
		
		contentLibDao = new ContentLibDao(db);
		myFeedDao = new MyFeedDao(db);
		
		Intent intent = getIntent();
		String type =  intent.getStringExtra("type");
		if (TextUtils.isEmpty(type)) {
			mAdapter = new RssAddAdapter(this, R.layout.rss_add_item, mData);
			listView.setAdapter(mAdapter);
			new MyTask().execute("");
		} else {
			mAdapter = new RssAddAdapter(this, R.layout.rss_add_item_ex, mData);
			listView.setAdapter(mAdapter);
			new MyTask().execute(type);
		}
		
		mAdapter.setMyFeedDao(myFeedDao);
	}
	
	@Override
	protected void onDestroy() {
		if (db != null) {
			db.close();
		}
		super.onDestroy();
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) { 
	     switch (item.getItemId()) { 
	        case android.R.id.home: 
	            this.finish(); 
	        default: 
	            return super.onOptionsItemSelected(item); 
	    } 
	} 
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mAdapter.getIsEX()) {
			MyFeed feed = mData.get(position);
			Intent intent = new Intent(this, ReadActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("feed", feed);
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this,RssAddActivity.class);
			intent.putExtra("type", mData.get(position).getTitle());
			startActivity(intent);
		}
	}
	
	boolean boo = false;
	public void channelListAdd(View view) {
		ImageView imageView = (ImageView) view;
		if (boo) {
			imageView.setImageResource(R.drawable.channellist_hasadd_icon);
			boo = true;
		} else {
			imageView.setImageResource(R.drawable.channellist_noadd_icon);
			boo = false;
		}
	}
	
	class MyTask extends AsyncTask<String, String, List<MyFeed>> {

		@Override
		protected void onPostExecute(List<MyFeed> result) {
			mData.clear();
			mData.addAll(result);
			mAdapter.refresh(mData);
		}
		
		@Override
		protected List<MyFeed> doInBackground(String... params) {
			if (TextUtils.isEmpty(params[0]) ) {
				return myFeedDao.getAllContentType();
			} else {
				return myFeedDao.getContentByType(params[0]);
			}
		}
	}
	
}


