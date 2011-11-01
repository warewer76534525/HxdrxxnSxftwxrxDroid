package com.triplelands.HidreenSoftware.C2DM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.triplelands.HidreenSoftware.activity.RootActivity;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.utils.NotificationUtil;

public class HSC2dmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
	        handleRegistration(context, intent);
	    } else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
	        handleMessage(context, intent);
	    }
	 }

	private void handleRegistration(Context context, Intent intent) {
	    String registration = intent.getStringExtra("registration_id");
	    if (intent.getStringExtra("error") != null) {
	        // Registration failed, should try again later.
		    Log.d("C2DM", "registration failed");
		    String error = intent.getStringExtra("error");
		    if(error == "SERVICE_NOT_AVAILABLE"){
		    	Log.d("C2DM", "SERVICE_NOT_AVAILABLE");
		    }else if(error == "ACCOUNT_MISSING"){
		    	Log.d("C2DM", "ACCOUNT_MISSING");
		    }else if(error == "AUTHENTICATION_FAILED"){
		    	Log.d("C2DM", "AUTHENTICATION_FAILED");
		    }else if(error == "TOO_MANY_REGISTRATIONS"){
		    	Log.d("C2DM", "TOO_MANY_REGISTRATIONS");
		    }else if(error == "INVALID_SENDER"){
		    	Log.d("C2DM", "INVALID_SENDER");
		    }else if(error == "PHONE_REGISTRATION_ERROR"){
		    	Log.d("C2DM", "PHONE_REGISTRATION_ERROR");
		    }
	    } else if (intent.getStringExtra("unregistered") != null) {
	        // unregistration done, new messages from the authorized sender will be rejected
	    	Log.i("C2DM", "unregistered");
	    } else if (registration != null) {
	    	Log.i("C2DM", "id: " + registration);
	    	DataManager.getInstance(context.getApplicationContext()).setC2DMRegistrationId(registration);
	    }
	}

	private void handleMessage(Context context, Intent intent)
	{
		//Do whatever you want with the message
		String payload = intent.getExtras().getString("payload");
		Log.i("C2DM", "dmControl: payload = " + payload);
		NotificationUtil.getInstance().show(context.getApplicationContext(), "AmygdalaHD - New Signal(s) Received", "New Signals", payload, RootActivity.class);
	}

}
