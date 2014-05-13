package org.jhw.keep.datatype;

import java.util.ArrayList;

public class MyFeedItem {

	private String thumbnailImage;
	
	private String title;
	private String link;
	private String comments;
	private String pubDate;
	private String summary;
	private String content;
	private String author;
	private String commentRss;
	private String slashComments;
	
	public String getSlashComments() {
		return slashComments;
	}
	public void setSlashComments(String slashComments) {
		this.slashComments = slashComments;
	}
	public String getCommentRss() {
		return commentRss;
	}
	public void setCommentRss(String commentRss) {
		this.commentRss = commentRss;
	}

	private ArrayList<String> category = new ArrayList<String>();
	
	public ArrayList<String> getCategory() {
		return category;
	}
	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}
	public String getThumbnailImage() {
		return thumbnailImage;
	}
	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "MyFeedItem [title=" + title + ", summary=" + summary + ", img="
				+ thumbnailImage + ", content=" + content + ", author=" + author
				+ ", pubDate=" + pubDate + ", link=" + link + "]";
	}
	
	public String createImg() {
		if (content.contains("src=")) {
			return getTagValue(content, "src=");
		} else {
			return "";
		}
	}

	private static String getTagValue(String source, String tagName) {
		int index = source.indexOf(tagName);
		String temp = source.substring(index + tagName.length(), source.length());
		String[] tempArr = temp.split("\"");
		return tempArr[1];
	}
}
