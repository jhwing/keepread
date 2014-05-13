package org.jhw.keep.app.task;

public interface AsyncRefreshListener<Result> {

	public void onAsyncRefreshComplete(Result result);
}
