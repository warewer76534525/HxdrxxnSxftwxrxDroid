package com.triplelands.HidreenSoftware.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Setting {

	private static Setting instance;
	private static SharedPreferences appPreference;
	
	private Setting() {
	}
	
	public static Setting getInstance(Context ctx){
		if(instance == null){
			instance = new  Setting();
			appPreference =  PreferenceManager.getDefaultSharedPreferences(ctx);
		}
		return instance;
	}
	
	public void setDisplayEcocal(boolean display){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putBoolean("HSSetting_displayEcocal", display);
        editor.commit();
	}
	
	public void setDisplayEcoHigh(boolean display){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putBoolean("HSSetting_displayEcoHigh", display);
        editor.commit();
	}
	public void setDisplayEcoMed(boolean display){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putBoolean("HSSetting_displayEcoMed", display);
        editor.commit();
	}
	
	public void setDisplayEcoLow(boolean display){
		SharedPreferences.Editor editor = appPreference.edit();
		editor.putBoolean("HSSetting_displayEcoLow", display);
        editor.commit();
	}
	
	public boolean isDisplayEcocal(){
		return appPreference.getBoolean("HSSetting_displayEcocal", true);
	}
	
	public boolean isDisplayEcoHigh(){
		return appPreference.getBoolean("HSSetting_displayEcoHigh", true);
	}
	
	public boolean isDisplayEcoMed(){
		return appPreference.getBoolean("HSSetting_displayEcoMed", true);
	}
	
	public boolean isDisplayEcoLow(){
		return appPreference.getBoolean("HSSetting_displayEcoLow", true);
	}
}
