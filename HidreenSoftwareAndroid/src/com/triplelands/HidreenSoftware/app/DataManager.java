package com.triplelands.HidreenSoftware.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataManager {

	private static DataManager instance;
	private static SharedPreferences appPreference;
	
	private DataManager() {
	}
	
	public static DataManager getInstance(Context ctx){
		if(instance == null){
			instance = new  DataManager();
			appPreference =  PreferenceManager.getDefaultSharedPreferences(ctx);
		}
		return instance;
	}
	
	public void setEmail(String email){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putString("HS_Email", email);
        editor.commit();
	}
	
	public void setSessionId(String sessionId){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putString("HS_Session", sessionId);
        editor.commit();
	}
	
	public String getEmail(){
		return appPreference.getString("HS_Email", "");
	}
	
	public String getSessionId(){
		return appPreference.getString("HS_Session", "");
	}
		
	public boolean isLoggedIn(){
		return (!getSessionId().equals(""));
	}
}
