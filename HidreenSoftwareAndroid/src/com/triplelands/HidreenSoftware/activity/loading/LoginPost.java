package com.triplelands.HidreenSoftware.activity.loading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.utils.DataProcessor;

public class LoginPost extends InvokeHttpPostConnection {

	protected void onCreate(Bundle savedInstanceState) {
		String url = getIntent().getExtras().getString("url");
		String params[] = getIntent().getExtras().getStringArray("params");
		super.onCreate(savedInstanceState, url, params);
	}
	
	public void onReceivedResponse(InputStream is, int length) {
		System.out.println("received response");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
			StringBuilder sb = new StringBuilder();
	        sb.append(reader.readLine() + "\n");
	        String line="0";
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        String data = sb.toString();
	        Log.i("HS", data);
	        
	        Looper.prepare();
	        String status = DataProcessor.getResponseStatus(data);
	        if (status.equals("0")) {
	        	
	    		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Login Failed").setMessage(DataProcessor.getResponseMessage(data)).
	    		setNeutralButton("Close", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dlg, int sumthin) {
	    				Looper.myLooper().quit();
	    			}
	    		}).show();
			} else {
				DataManager manager = DataManager.getInstance(this);
				manager.setEmail(DataProcessor.getEmail(data));
				manager.setSessionId(DataProcessor.getSessionId(data));
				Toast.makeText(LoginPost.this, DataProcessor.getResponseMessage(data), Toast.LENGTH_SHORT).show();
				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);
				Looper.myLooper().quit();
			}
	        Looper.loop();
			
		} catch (UnsupportedEncodingException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		}
		
		super.onReceivedResponse(is, length);
		finish();
	}
}