package com.triplelands.HidreenSoftware.C2DM;

import java.io.InputStream;

import android.content.Context;

import com.triplelands.HidreenSoftware.tools.InternetConnection;
import com.triplelands.HidreenSoftware.tools.InternetConnectionHandler;

public class C2dmIdRegisterer implements InternetConnectionHandler {
	
	public static final String URL_C2DM_ID_REGISTRATION = "";
	
	private Context context;
	private String id;
	
	public C2dmIdRegisterer(Context context, String id){
		this.context = context;
		this.id = id;
	}
	
	public void start(){
		final String params[] = { "registrationId=" + id };
		final InternetConnection internetConnection = new InternetConnection(context, this);
		Thread invokingThread = new Thread(new Runnable() {
			public void run() {
				internetConnection.postData(URL_C2DM_ID_REGISTRATION, params);
			}
		});
		invokingThread.setPriority(Thread.MAX_PRIORITY);
		invokingThread.start();
	}

	@Override
	public void onReceivedResponse(InputStream response, int length) {
		//registration id sent. save to SharedPreference
	}

	@Override
	public void onErrorConnection(Exception ex) {
		
	}

	@Override
	public void onConnectionTimeout() {
		
	}

	@Override
	public void onConnectionCancelled() {
		
	}

	@Override
	public void onConnectionResponseNotOk() {
		
	}
	
}
