package com.triplelands.HidreenSoftware.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Signal implements Serializable {
	private int id;
	private String time;
	private String category;
	private String method;
	private String pattern;
	private String symbol;
	private String direction;
	private double probability;
	private int comment;
	private String viewed;
	
	public Signal(int id, String time, String category, String method, String pattern, String symbol, String direction, double probability, int comment, String viewed){
		this.id = id;
		this.time = time;
		this.category = category;
		this.method = method;
		this.pattern = pattern;
		this.symbol = symbol;
		this.direction = direction;
		this.probability = probability;
		this.comment = comment;
		this.viewed = viewed;
	}

	public int getId() {
		return id;
	}
	
	public String getTime() {
		return time;
	}

	public String getCategory() {
		return category;
	}

	public String getMethod() {
		return method;
	}

	public String getPattern() {
		return pattern;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getDirection() {
		return direction;
	}

	public double getProbability() {
		return probability;
	}

	public int getComment() {
		return comment;
	}

	public boolean isUp(){
		return (!direction.trim().toLowerCase().equals("down"));
	}
	
	public boolean isUnread(){
		return viewed.equals("1");
	}
}
