package com.triplelands.HidreenSoftware.activity.loading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.triplelands.HidreenSoftware.activity.FeedContentListActivity;
import com.triplelands.HidreenSoftware.model.FeedContent;
import com.triplelands.HidreenSoftware.utils.DataProcessor;

public class LoadingFeedContent extends InvokeHttpGetConnection {
	private String url;
	private String title;

	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
			title = bundle.getString("title");
			super.onCreate(savedInstanceState, url);
		}
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
	        
	        ArrayList<FeedContent> contents = (ArrayList<FeedContent>) DataProcessor.getNewsFeedContents(data);
	        
	        Intent i = new Intent(this, FeedContentListActivity.class);
	        i.putExtra("contents", contents);
	        i.putExtra("title", title);
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
