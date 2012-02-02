package com.triplelands.HidreenSoftware.C2DM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.util.Log;

import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.tools.InternetConnection;
import com.triplelands.HidreenSoftware.tools.InternetConnectionHandler;
import com.triplelands.HidreenSoftware.utils.DataProcessor;

public class C2dmIdRegisterer implements InternetConnectionHandler {
	
	public static final String URL_C2DM_ID_REGISTRATION = "http://amygdalahd.com/m/members/token";
	
	private Context context;
	private String id;
	
	public C2dmIdRegisterer(Context context, String id){
		this.context = context;
		this.id = id;
	}
	
	public void start(){
		final InternetConnection internetConnection = new InternetConnection(context, this);
		Thread invokingThread = new Thread(new Runnable() {
			public void run() {
				internetConnection.setAndAccessURL(URL_C2DM_ID_REGISTRATION + "?email=" + DataManager.getInstance(context).getEmail() + "&token=" + id);
			}
		});
		invokingThread.setPriority(Thread.MAX_PRIORITY);
		invokingThread.start();
	}

	public void onReceivedResponse(InputStream is, int length) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
			StringBuilder sb = new StringBuilder();
	        sb.append(reader.readLine() + "\n");
	        String line="0";
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        String data = sb.toString();
	        //Log.i("HS C2DM Register", data + ": " + id);
	        
	        if (DataProcessor.getDataContent(data, "status").equals("1")) {
	        	DataManager.getInstance(context).setC2DMRegistrationId(id);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void onErrorConnection(Exception ex) {
		
	}

	public void onConnectionTimeout() {
		
	}

	public void onConnectionCancelled() {
		
	}

	public void onConnectionResponseNotOk() {
		
	}
	
}
