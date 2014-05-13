package org.jhw.keep.parser;

import java.io.InputStream;

public interface FeedParser {
	
	final static String RSS = "rss";

	final static String ATOM = "feed";
	
	final static String NS_DC = "dc";
	final static String NS_SY = "sy";
	final static String NS_WFW = "wfw";
	final static String NS_SLASH = "slash";
	final static String NS_ATOM = "atom";
	
	final static String NS_DC_URL = "http://purl.org/dc/elements/1.1/";
	final static String NS_WFW_URL = "http://wellformedweb.org/CommentAPI/";
	final static String NS_SLASH_URL = "http://purl.org/rss/1.0/modules/slash/";
	final static String NS_SY_URL = "http://purl.org/rss/1.0/modules/syndication/";
	final static String NS_ATOM_URL = "http://www.w3.org/2005/Atom";
	
	public FeedResult parse (InputStream is) throws Exception;

	public FeedResult parse (String source) throws Exception;
}
