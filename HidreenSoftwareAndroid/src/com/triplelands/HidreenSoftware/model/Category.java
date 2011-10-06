package com.triplelands.HidreenSoftware.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Category implements Serializable {
	private int id;
	private String name;
	private List<Signal> signals;
	
	public Category(int id, String name, List<Signal> signals) {
		this.id = id;
		this.name = name;
		this.signals = signals;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<Signal> getSignals() {
		return signals;
	}
	
}
