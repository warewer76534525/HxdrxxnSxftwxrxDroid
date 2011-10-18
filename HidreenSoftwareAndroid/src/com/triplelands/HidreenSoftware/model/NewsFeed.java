package com.triplelands.HidreenSoftware.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NewsFeed implements Serializable {

	private String id;
	private String name;
	private String site;
	
	public NewsFeed(String id, String name, String site) {
		this.id = id;
		this.name = name;
		this.site = site;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSite() {
		return site;
	}
	
	
}
