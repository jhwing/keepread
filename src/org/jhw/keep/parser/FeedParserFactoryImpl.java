package org.jhw.keep.parser;

public class FeedParserFactoryImpl extends FeedParserFactory{

	@Override
	public FeedParser newFeedParser() {
		// TODO Auto-generated method stub
		return new FeedParserImpl();
	}

}
