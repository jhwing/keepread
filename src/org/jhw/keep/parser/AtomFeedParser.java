package org.jhw.keep.parser;

import java.io.IOException;

import org.jhw.keep.datatype.MyFeedItem;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AtomFeedParser {

	public static FeedResult parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		FeedResult feedResult = new FeedResult();
		int eventType = parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				// id
				if ("id".equals(name)) {
					feedResult.myFeed.setTitle(parser.nextText());
				}
				// title
				if ("title".equals(name)) {
					feedResult.myFeed.setTitle(parser.nextText());
				}
				// link
				if ("link".equals(name)) {
					feedResult.myFeed.setLink(parser.nextText());
				}
				// update
				if ("update".equals(name)) {
					feedResult.myFeed.setPubDate(parser.nextText());
				}
				// entry
				if ("entry".equals(name)) {
					eventType = parseEntry(parser, feedResult);
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
	
	private static int parseEntry(XmlPullParser parser, FeedResult feedResult)
			throws XmlPullParserException, IOException {
		MyFeedItem item = new MyFeedItem(); 
		String itemElement = "";
		int eventType = -1;
		while(eventType!=XmlPullParser.END_DOCUMENT){
			itemElement = parser.getName();
			eventType = parser.getEventType();
			if (eventType == XmlPullParser.END_TAG && "entry".equals(itemElement)) {
				break;
			}
			switch (eventType) {
				case XmlPullParser.START_TAG:
//					if ("id".equals(itemElement)) {
//						item.setTitle(parser.nextText());
//						break;
//					}
					if ("title".equals(itemElement)) {
						item.setTitle(parser.nextText());
						break;
					}
					if ("summary".equals(itemElement)) {
						item.setSummary(parser.nextText());
						break;
					}
					if ("content".equals(itemElement)) {
						item.setContent(parser.nextText());
						break;
					}
//					if ("author".equals(itemElement)) {
//						item.setAuthor(parser.nextText());
//						break;
//					}
					if ("name".equals(itemElement)) {
						item.setAuthor(parser.nextText());
						break;
					}
					if ("email".equals(itemElement)) {
						item.setAuthor(parser.nextText());
						break;
					}
					
					if ("pubDate".equals(itemElement)) {
						item.setPubDate(parser.nextText());
						break;
					}
					if ("link".equals(itemElement)) {
						item.setLink(parser.nextText());
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
		item.setThumbnailImage(item.createImg());
		feedResult.items.add(item);
		return eventType;
	}
}
