package com.triplelands.HidreenSoftware.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class ImageChartManager {
	
	private static ImageChartManager instance;
	private static String currentId;
	private static List<Bitmap> currentImages;
	
	private ImageChartManager() {
	}
	
	public static ImageChartManager GetInstance(){
		if (instance == null) {
			currentId = "";
			instance = new ImageChartManager();
			currentImages = new ArrayList<Bitmap>();
		}
		return instance;
	}
	
	public void setCurrentChart(String id, List<Bitmap> images){
		currentId = id;
		currentImages.clear();
		currentImages = images;
	}
	
	public List<Bitmap> getCurrentChart(String id){
		if (id.equals(currentId)) {
			return currentImages;
		} else {
			return null;
		}
	}
}
