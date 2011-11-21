package com.triplelands.HidreenSoftware.activity.loading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.triplelands.HidreenSoftware.utils.DataProcessor;

public class LoadingAbout extends InvokeHttpGetConnection {
	private String url;

	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
			super.onCreate(savedInstanceState, url);
		}
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
	        Log.i("HS", data);
	        
	        Intent resultIntent = new Intent();
			resultIntent.putExtra("content", DataProcessor.getDataContent(data, "content"));
			setResult(RESULT_OK, resultIntent);
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
