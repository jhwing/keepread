package org.jhw.keep.parser;


public abstract class FeedParserFactory {

	public static FeedParserFactory newInstance() {
		return new FeedParserFactoryImpl();
	}
	
	public abstract FeedParser newFeedParser();
	
}
