package org.jhw.keep.datatype;

import java.io.Serializable;

public class ContentLib implements Serializable{
	
	private static final long serialVersionUID = 5504265085924465440L;
	
	private String type;
	private String feedLink;
	private String feedTitle;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFeedLink() {
		return feedLink;
	}
	public void setFeedLink(String feedLink) {
		this.feedLink = feedLink;
	}
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
}
