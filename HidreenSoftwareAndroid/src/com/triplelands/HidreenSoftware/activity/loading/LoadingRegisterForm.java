package com.triplelands.HidreenSoftware.activity.loading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.triplelands.HidreenSoftware.activity.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LoadingRegisterForm extends InvokeHttpGetConnection {

	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		url = getIntent().getExtras().getString("url");
		super.onCreate(savedInstanceState, url);
	}
	
	@Override
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
	        Log.i("HS", data);
	        
	        Intent i = new Intent(this, RegisterActivity.class);
	        i.putExtra("json", data);
	        startActivity(i);
	        
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
