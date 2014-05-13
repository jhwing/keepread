package org.jhw.keep.datatype;

import java.io.Serializable;

public class MyFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7402763241253856349L;
	
	private String url;
	private String title;
	private String link;
	private String linkWebSite;
	private String linkSelf;
	private String description;
	private String language;
	private String pubDate;
	private String lastBuildDate;
	private String type;
	private boolean hasAdd;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkWebSite() {
		return linkWebSite;
	}
	public void setLinkWebSite(String linkWebSite) {
		this.linkWebSite = linkWebSite;
	}
	public String getLinkSelf() {
		return linkSelf;
	}
	public void setLinkSelf(String linkSelf) {
		this.linkSelf = linkSelf;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isHasAdd() {
		return hasAdd;
	}
	public void setHasAdd(boolean hasAdd) {
		this.hasAdd = hasAdd;
	}
	@Override
	public String toString() {
		return "MyFeed [url=" + url + ", title=" + title + ", link=" + link
				+ ", linkWebSite=" + linkWebSite + ", linkSelf=" + linkSelf
				+ ", description=" + description + ", language=" + language
				+ ", pubDate=" + pubDate + ", lastBuildDate=" + lastBuildDate
				+ ", type=" + type + ", hasAdd=" + hasAdd + "]";
	}
}
