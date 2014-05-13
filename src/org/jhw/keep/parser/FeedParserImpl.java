package org.jhw.keep.parser;

import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class FeedParserImpl implements FeedParser {

	@Override
	public FeedResult parse(InputStream is) throws Exception {
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				String rssType = parser.getName();
				if (RSS.equals(rssType)) {
					return RSSFeedParser.parse(parser);
				}
				if (ATOM.equals(rssType)) {
					return AtomFeedParser.parse(parser);
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
		
		return null;
	}

	@Override
	public FeedResult parse(String source) throws Exception {
		URL url = new URL(source);
		return parse(url.openStream());
	}

}
