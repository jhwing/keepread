package org.jhw.keep.app.task;

import java.lang.ref.WeakReference;

import org.jhw.keep.parser.FeedParser;
import org.jhw.keep.parser.FeedParserFactory;
import org.jhw.keep.parser.FeedResult;

import android.os.AsyncTask;

public class FeedListTask extends AsyncTask<String, String, FeedResult> {

	private WeakReference<AsyncRefreshListener<FeedResult>> asyncRefreshListenerReference;

	private AsyncRefreshListener<FeedResult> asyncRefreshListener;

	public void setAsyncRefreshListener(
			AsyncRefreshListener<FeedResult> asyncRefreshListener) {
		asyncRefreshListenerReference = new WeakReference<AsyncRefreshListener<FeedResult>>(
				asyncRefreshListener);
		this.asyncRefreshListener = asyncRefreshListenerReference.get();
	}

	protected void onPostExecute(FeedResult result) {
		if (isCancelled()) {
			return;
		}
		if (asyncRefreshListener != null) {
			asyncRefreshListener.onAsyncRefreshComplete(result);
		}
	};

	@Override
	protected FeedResult doInBackground(String... params) {
		if (isCancelled()) {
			return null;
		}
		FeedParser feedParser = FeedParserFactory.newInstance().newFeedParser();
		try {
			return feedParser.parse(params[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
