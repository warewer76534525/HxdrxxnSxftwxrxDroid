package com.triplelands.HidreenSoftware.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

public class ChartDataManager {
	
	private static ChartDataManager instance;
	private static String currentUrl;
	private static List<BasicNameValuePair> currentData;
	
	private ChartDataManager() {
	}
	
	public static ChartDataManager GetInstance(){
		if (instance == null) {
			currentUrl = "";
			instance = new ChartDataManager();
			currentData = new ArrayList<BasicNameValuePair>();
		}
		return instance;
	}
	
	public void setCurrentData(String id, List<BasicNameValuePair> images){
		currentUrl = id;
		currentData.clear();
		currentData = images;
	}
	
	public List<BasicNameValuePair> getCurrentChart(String id){
		if (id.equals(currentUrl)) {
			return currentData;
		} else {
			return null;
		}
	}
}
