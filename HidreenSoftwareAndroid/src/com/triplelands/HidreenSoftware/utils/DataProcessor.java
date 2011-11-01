package com.triplelands.HidreenSoftware.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.triplelands.HidreenSoftware.model.Category;
import com.triplelands.HidreenSoftware.model.EconomicCalendar;
import com.triplelands.HidreenSoftware.model.FeedContent;
import com.triplelands.HidreenSoftware.model.NewsFeed;
import com.triplelands.HidreenSoftware.model.Signal;

public class DataProcessor {

	public static String getResponseStatus(String json){
		JSONObject obj;
		try {
			obj = new JSONObject(json);
		} catch (JSONException e) {
			return "";
		}
		return obj.optString("status");
	}
	
	public static String getResponseMessage(String json){
		JSONObject obj;
		try {
			obj = new JSONObject(json);
		} catch (JSONException e) {
			return "";
		}
		return obj.optString("message");
	}
	
	public static String getEmail(String json){
		try {
			JSONObject obj = new JSONObject(json);
			return obj.getString("email");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getSessionId(String json){
		try {
			JSONObject obj = new JSONObject(json);
			return obj.getString("session_id");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static List<Category> getCategoryList(String fullJson) {
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

	public static Signal getSignalDetail(String fullJson) {
		try {
			JSONObject obj = new JSONObject(fullJson);
			JSONObject object = new JSONObject(obj.getString("signals"));
			Signal signal = convertToSignal(object);
			return signal;
		} catch (JSONException e) {
			return null;
		}
	}

	public static String getMetaSignal(String fullJson) {
		try {
			JSONObject obj = new JSONObject(fullJson);
			return obj.optString("metasignal");
		} catch (JSONException e) {
			return "";
		}
	}

	public static String[] getImageUrls(String fullJson) {
		try {
			JSONObject obj = new JSONObject(fullJson);
			JSONArray arrImg = obj.getJSONArray("image");
			String[] imgs = new String[arrImg.length()];
			for (int i = 0; i < arrImg.length(); i++) {
				imgs[i] = arrImg.optString(i);
			}
			return imgs;
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
			signal = new Signal(object.getInt("id"), object.optString("time"),
					object.optString("category"), object.optString("method"),
					object.optString("pattern"), object.optString("symbol"),
					object.optString("direction"),
					object.getDouble("probability"),
					object.getInt("comment_count"),
					object.optString("viewed"));
		} catch (JSONException e) {
			return null;
		}
		return signal;
	}

	public static List<BasicNameValuePair> getKeyValueArray(String json) {
		try {
			List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
			JSONObject object = new JSONObject(json);
			JSONArray arr = object.names();
			for (int i = 0; i < arr.length(); i++) {
				data.add(new BasicNameValuePair(arr.getString(i), object
						.optString(arr.getString(i))));
			}
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<NewsFeed> getNewsFeedList(String fullJson) {
		try {
			List<NewsFeed> feeds = new ArrayList<NewsFeed>();
			JSONArray arr = new JSONArray(fullJson);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject object = arr.getJSONObject(i);
				NewsFeed feed = convertToNewsFeed(object);
				feeds.add(feed);
			}
			return feeds;
		} catch (JSONException e) {
			return null;
		}
	}

	private static NewsFeed convertToNewsFeed(JSONObject object) {
		return new NewsFeed(object.optString("id"), object.optString("name"),
				object.optString("site"));
	}
	
	public static List<FeedContent> getNewsFeedContents(String fullJson) {
		try {
			List<FeedContent> feeds = new ArrayList<FeedContent>();
			JSONArray arr = new JSONArray(fullJson);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject object = arr.getJSONObject(i);
				FeedContent feed = convertToFeedContent(object);
				feeds.add(feed);
			}
			return feeds;
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static List<EconomicCalendar> getEconomicCalendarList(String fullJson){
		try {
			JSONObject obj = new JSONObject(fullJson);
			String ecocal = obj.optString("ecocal");
			
			if(ecocal.equals("false")) return null;
		
			List<EconomicCalendar> list = new ArrayList<EconomicCalendar>();
			JSONArray arr = new JSONArray(ecocal);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject object = arr.getJSONObject(i);
				EconomicCalendar ecal = convertToEconomicCalendar(object);
				list.add(ecal);
			}
			return list;
		} catch (JSONException e) {
			return null;
		}
	}

	private static EconomicCalendar convertToEconomicCalendar(JSONObject object) {
		return new EconomicCalendar(object.optString("date"), 
				object.optString("time"), 
				object.optString("timezone"), 
				object.optString("currency"), 
				object.optString("description"), 
				object.optString("importance"), 
				object.optString("actual"), 
				object.optString("forecast"), 
				object.optString("previous"));
	}

	private static FeedContent convertToFeedContent(JSONObject object) {
		return new FeedContent(object.optString("title"), object.optString("date"),
				object.optString("link"));
	}
}
