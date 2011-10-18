package com.triplelands.HidreenSoftware.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeedContent implements Serializable {
	private String title;
	private String date;
	private String link;
	
	public FeedContent(String title, String date, String link) {
		this.title = title;
		this.date = date;
		this.link = link;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLink() {
		return link;
	}
	
	
}
