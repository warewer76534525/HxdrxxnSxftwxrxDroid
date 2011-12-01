package com.triplelands.HidreenSoftware.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.triplelands.HidreenSoftware.model.OLHCData;

public class ChartDataManager {
	
	private static ChartDataManager instance;
	private static String currentUrl;
	private static List<BasicNameValuePair> currentData;
	private static List<OLHCData> currentCandleData;
	
	private ChartDataManager() {
	}
	
	public static ChartDataManager GetInstance(){
		if (instance == null) {
			currentUrl = "";
			instance = new ChartDataManager();
			currentData = new ArrayList<BasicNameValuePair>();
			currentCandleData = new ArrayList<OLHCData>();
		}
		return instance;
	}
	
	public void setCurrentData(String id, List<BasicNameValuePair> images){
		currentUrl = id;
		currentData.clear();
		currentData = images;
	}
	
	public void setCurrentCandleData(String id, List<OLHCData> data){
		currentUrl = id;
		currentCandleData.clear();
		currentCandleData = data;
	}
	
	public List<BasicNameValuePair> getCurrentChart(String id){
		if (id.equals(currentUrl)) {
			return currentData;
		} else {
			return null;
		}
	}
	
	public List<OLHCData> getCurrentCandleChart(String id){
		if (id.equals(currentUrl)) {
			return currentCandleData;
		} else {
			return null;
		}
	}
}
