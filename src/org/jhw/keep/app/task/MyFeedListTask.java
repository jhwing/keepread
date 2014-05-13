package org.jhw.keep.app.task;

import java.lang.ref.WeakReference;
import java.util.List;

import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.db.MyDbManager;
import org.jhw.keep.db.MyFeedDao;

import android.content.Context;
import android.os.AsyncTask;

public class MyFeedListTask extends AsyncTask<String, String, List<MyFeed>> {

	private WeakReference<AsyncRefreshListener<List<MyFeed>>> asyncRefreshListenerReference;
	private WeakReference<Context> contextReference;
	
	private Context mContext;
	private AsyncRefreshListener<List<MyFeed>> asyncRefreshListener;

	MyDbManager dbManager;
	MyFeedDao feedDao;
	
	public MyFeedListTask(Context context) {
		super();
		this.contextReference = new WeakReference<Context>(context);
		mContext = contextReference.get();
		dbManager = new MyDbManager(mContext,"keep.db",null);
		feedDao = new MyFeedDao(dbManager.getDb());
	}

	public void setAsyncRefreshListener(
			AsyncRefreshListener<List<MyFeed>> asyncRefreshListener) {
		asyncRefreshListenerReference = new WeakReference<AsyncRefreshListener<List<MyFeed>>>(
				asyncRefreshListener);
		this.asyncRefreshListener = asyncRefreshListenerReference.get();
		
	}

	protected void onPostExecute(List<MyFeed> result) {
		if (isCancelled()) {
			return;
		}
		dbManager.clear();
		if (asyncRefreshListener != null) {
			asyncRefreshListener.onAsyncRefreshComplete(result);
		}
	};

	@Override
	protected List<MyFeed> doInBackground(String... params) {
			return feedDao.getAllHasFeed();
	}

}
