package com.triplelands.HidreenSoftware.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.triplelands.HidreenSoftware.model.Category;
import com.triplelands.HidreenSoftware.model.Signal;

public class DataProcessor {
	
	public static List<Category> getCategoryList(String fullJson){
		try {
			List<Category> cats = new ArrayList<Category>();
			JSONObject obj = new JSONObject(fullJson);
			JSONArray arr = obj.optJSONArray("groups");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject object = arr.getJSONObject(i);
				Category category = convertToCategory(object);
				cats.add(category);
			}
			return cats;
		} catch (JSONException e) {
			return null;
		}
	}
	
	private static Category convertToCategory(JSONObject object) {
		try {
			int id = object.getInt("id");
			String name = object.optString("name");
			
			List<Signal> signals = new ArrayList<Signal>();
			JSONArray arr = object.optJSONArray("signals");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				Signal signal = convertToSignal(obj);
				signals.add(signal);
			}
			return new Category(id, name, signals);
		} catch (Exception e) {
			return null;
		}
	}
	private static Signal convertToSignal(JSONObject object) {
		Signal signal;
		try {
			signal = new Signal(
					object.getInt("id"),
					object.optString("time"),
					object.optString("category"), 
					object.optString("method"), 
					object.optString("pattern"), 
					object.optString("symbol"), 
					object.optString("direction"), 
					object.getDouble("probability"), 
					object.getInt("comment_count"));
		} catch (JSONException e) {
			return null;
		}
		return signal;
	}
}
