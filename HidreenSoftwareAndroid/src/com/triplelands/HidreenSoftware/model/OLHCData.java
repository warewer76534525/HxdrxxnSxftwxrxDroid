package com.triplelands.HidreenSoftware.model;

public class OLHCData {

	private String time;
	private double open;
	private double low;
	private double high;
	private double close;
	
	public OLHCData(String time, double open, double low, double high, double close) {
		this.time = time;
		this.open = open;
		this.low = low;
		this.high = high;
		this.close = close;
	}

	public String getTime() {
		return time;
	}

	public double getOpen() {
		return open;
	}

	public double getLow() {
		return low;
	}

	public double getHigh() {
		return high;
	}

	public double getClose() {
		return close;
	}
	
}
