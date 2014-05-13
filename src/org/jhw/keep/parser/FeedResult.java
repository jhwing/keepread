package org.jhw.keep.parser;

import java.util.ArrayList;
import java.util.List;

import org.jhw.keep.datatype.MyFeed;
import org.jhw.keep.datatype.MyFeedItem;

public class FeedResult {
	
	public MyFeed myFeed = new MyFeed();
	
	public List<MyFeedItem> items = new ArrayList<MyFeedItem>();

	@Override
	public String toString() {
		return "FeedResult [myFeed=" + myFeed + ", items=" + items + "]";
	}
	
}
