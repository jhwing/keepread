package org.jhw.keep.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jhw.keep.Constants;
import org.jhw.keep.datatype.MyFeedItem;
import org.jhw.keep.util.HtmlRegexpUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.text.TextUtils;
import android.util.Log;

public class RSSFeedParser {
	
	final static String NS_CONTENT = "content";
	final static String NS_DC = "dc";
	final static String NS_SY = "sy";
	final static String NS_WFW = "wfw";
	final static String NS_SLASH = "slash";
	final static String NS_ATOM = "atom";
	
	final static String NS_CONTENT_URL = "http://purl.org/rss/1.0/modules/content/";
	final static String NS_DC_URL = "http://purl.org/dc/elements/1.1/";
	final static String NS_WFW_URL = "http://wellformedweb.org/CommentAPI/";
	final static String NS_SLASH_URL = "http://purl.org/rss/1.0/modules/slash/";
	final static String NS_SY_URL = "http://purl.org/rss/1.0/modules/syndication/";
	final static String NS_ATOM_URL = "http://www.w3.org/2005/Atom";

	static boolean hasCONTENT = false;
	static boolean hasDC = false;
	static boolean hasSY = false;
	static boolean hasWFW = false;
	static boolean hasSLASH = false;
	static boolean hasATOM = false;
	
	public static FeedResult parse(XmlPullParser parser) throws Exception {
		
		// get xmlns
		String content = parser.getNamespace(NS_CONTENT);
		String dc = parser.getNamespace(NS_DC);
		String sy = parser.getNamespace(NS_SY);
		String wfw = parser.getNamespace(NS_WFW);
		String slash = parser.getNamespace(NS_SLASH);
		String atom = parser.getNamespace(NS_ATOM);
		
		if (content != null && content.equals(NS_CONTENT_URL)) {
			hasCONTENT = true;
		} else {
			hasCONTENT = false;
		}
		if (dc != null && dc.equals(NS_DC_URL)) {
			hasDC = true;
		} else {
			hasDC = false;
		}
		if (sy != null && sy.equals(NS_DC_URL)) {
			hasSY = true;
		} else {
			hasSY = false;
		}
		if (wfw != null && wfw.equals(NS_DC_URL)) {
			hasWFW = true;
		} else {
			hasWFW = false;
		}
		if (slash != null && slash.equals(NS_DC_URL)) {
			hasSLASH = true;
		} else {
			hasSLASH = false;
		}
		if (atom != null && atom.equals(NS_DC_URL)) {
			hasATOM = true;
		} else {
			hasATOM = false;
		}
		
		FeedResult feedResult = new FeedResult();
		int eventType = parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				// channel
				if ("channel".equals(name)) {
					parser.nextTag();
				}
				// title
				if ("title".equals(name)) {
					feedResult.myFeed.setTitle(parser.nextText());
				}
				// link
				if ("link".equals(name)) {
					feedResult.myFeed.setLink(parser.nextText());
				}
				// description
				if ("description".equals(name)) {
					feedResult.myFeed.setDescription(parser.nextText());
				}
				if ("language".equals(name)) {
					feedResult.myFeed.setLanguage(name);
				}
				// pubData
				if ("pubData".equals(name)) {
					feedResult.myFeed.setPubDate(parser.nextText());
				}
				// item
				if ("item".equals(name)) {
					eventType = parseItem(parser, feedResult);
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			case XmlPullParser.END_DOCUMENT:
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		return feedResult;
	}

	private static int parseItem(XmlPullParser parser, FeedResult feedResult)
			throws XmlPullParserException, IOException {
		MyFeedItem item = new MyFeedItem(); 
		String itemElement = "";
		int eventType = -1;
		while(eventType!=XmlPullParser.END_DOCUMENT){
			itemElement = parser.getName();
			eventType = parser.getEventType();
			if (eventType == XmlPullParser.END_TAG && "item".equals(itemElement)) {
				break;
			}
			switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("title".equals(itemElement)) {
						item.setTitle(parser.nextText());
						break;
					}
					if ("link".equals(itemElement)) {
						item.setLink(parser.nextText());
						break;
					}
					if ("comments".equals(itemElement)) {
						item.setComments(parser.nextText());
						break;
					}
					if ("pubDate".equals(itemElement)) {
						item.setPubDate(parser.nextText());
						break;
					}
					if ("author".equals(itemElement)) {
						item.setAuthor(parser.nextText());
						break;
					}
					if (hasDC && "creator".equals(itemElement)) {
						item.setAuthor(parser.nextText());
						break;
					}
					if ("category".equals(itemElement)) {
						item.getCategory().add(parser.nextText());
						break;
					}
					if ("image".equals(itemElement)) {
						item.setThumbnailImage(parser.nextText());
					}
					if ("description".equals(itemElement)) {
						item.setSummary(parser.nextText());
						break;
					}
					if (hasCONTENT && "encoded".equals(itemElement)) {
						item.setContent(parser.nextText());
						break;
					}
					if (hasWFW && "commentRss".equals(itemElement)) {
						item.setCommentRss(parser.nextText());
						break;
					}
					if (hasSLASH && "comments".equals(itemElement)) {
						item.setSlashComments(parser.nextText());
						break;
					}
					
					eventType = parser.next();
					break;
				case XmlPullParser.END_TAG:
					parser.nextToken();
					break;
				default:
					eventType = parser.next();
					break;
			}
		}
		if (TextUtils.isEmpty(item.getContent())) {
			item.setContent(item.getSummary());
		}
		if (TextUtils.isEmpty(item.getThumbnailImage())) {
			item.setThumbnailImage(item.createImg());
		}
		item.setSummary(HtmlRegexpUtil.filterHtml(item.getSummary()));
		feedResult.items.add(item);
		return eventType;
	}
	
}
