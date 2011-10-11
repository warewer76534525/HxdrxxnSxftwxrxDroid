package com.triplelands.HidreenSoftware.activity.loading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.triplelands.HidreenSoftware.activity.SignalDetailActivity;
import com.triplelands.HidreenSoftware.model.Signal;
import com.triplelands.HidreenSoftware.tools.ImageDownloaderUtility;
import com.triplelands.HidreenSoftware.tools.ImageLoadingHandler;
import com.triplelands.HidreenSoftware.utils.DataProcessor;

public class LoadingSignalDetail extends InvokeHttpGetConnection implements ImageLoadingHandler {
	private String url;
	private Signal receivedSignal;
	private String receivedMetaSignal;
	
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
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
	        Log.i("HS Signal Detail", data);
			
	        receivedSignal = DataProcessor.getSignalDetail(data);
	        receivedMetaSignal = DataProcessor.getMetaSignal(data);
	        String urls[] = DataProcessor.getImageUrls(data);
	        
	        ImageDownloaderUtility downloader = new ImageDownloaderUtility(this, urls, this);
	        downloader.start();
			
		} catch (UnsupportedEncodingException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		}
//		super.onReceivedResponse(is, length);
	}

	@Override
	public void onReceivedImages(List<Bitmap> images) {
		Intent i = new Intent(this, SignalDetailActivity.class);
        i.putExtra("signal", receivedSignal);
        i.putExtra("metaSignal", receivedMetaSignal);
        i.putExtra("images", (ArrayList<Bitmap>)images);
		startActivity(i);
		finish();
	}

	@Override
	public void onError() {
		super.onErrorConnection(null);
	}
}
