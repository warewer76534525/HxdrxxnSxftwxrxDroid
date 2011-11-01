package com.triplelands.HidreenSoftware.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataManager {

	private static DataManager instance;
	private static SharedPreferences appPreference;
	private static List<Activity> listActivity;
	
	private DataManager() {
	}
	
	public static DataManager getInstance(Context ctx){
		if(instance == null){
			instance = new  DataManager();
			appPreference =  PreferenceManager.getDefaultSharedPreferences(ctx);
			listActivity = new ArrayList<Activity>();
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
	
	public void setC2DMRegistrationId(String id){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putString("HS_Reg_Id", id);
        editor.commit();
	}
	
	public String getC2DMRegistrationId(){
		return appPreference.getString("HS_Reg_Id", "");
	}
	
	public boolean isRegisteredForPush(){
		return !appPreference.getString("HS_Reg_Id", "").equals("");
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
	
	public void addHistory(Activity activity){
		listActivity.add(activity);
	}
	
	public void clearAllHistory(){
		for (int i = 0; i < listActivity.size(); i++) {
			listActivity.get(i).finish();
		}
		listActivity.clear();
	}
	
	public void registerForC2DM(Context ctx){
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(ctx, 0, new Intent(), 0));
		registrationIntent.putExtra("sender", "mobile.hidreen@gmail.com");
		ctx.startService(registrationIntent);
	}
}
