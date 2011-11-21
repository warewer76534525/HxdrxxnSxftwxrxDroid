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
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.activity.SignalDetailActivity;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.EconomicCalendar;
import com.triplelands.HidreenSoftware.model.Signal;
import com.triplelands.HidreenSoftware.tools.ImageLoadingHandler;
import com.triplelands.HidreenSoftware.utils.DataProcessor;
import com.triplelands.HidreenSoftware.utils.ImageChartManager;

public class LoadingSignalDetail extends InvokeHttpGetConnection implements ImageLoadingHandler {
	private String url, id;
	private Signal receivedSignal;
	private String receivedMetaSignal;
	private String chartsUrls[];
	private ArrayList<EconomicCalendar> listEcocals;
	
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
			id = bundle.getString("id");
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
	        Log.i("HS Signal Detail", data);
			
	        if (DataProcessor.getDataContent(data, "status").equals("0")) {
	        	Looper.prepare();
	        	Toast.makeText(this, DataProcessor.getDataContent(data, "message"), Toast.LENGTH_SHORT).show();
	        	DataManager.getInstance(this).clearAllHistory();
				DataManager.getInstance(this).setSessionId("");
				finish();
				Looper.loop();
			} else {
				receivedSignal = DataProcessor.getSignalDetail(data);
		        receivedMetaSignal = DataProcessor.getMetaSignal(data);
		        listEcocals = (ArrayList<EconomicCalendar>) DataProcessor.getEconomicCalendarList(data);
//		        String urls[] = DataProcessor.getImageUrls(data);
		        chartsUrls = DataProcessor.getChartUrls(data);
		 		
//		        if (urls.length > 0) {
//		        	ImageDownloaderUtility downloader = new ImageDownloaderUtility(this, urls, this);
//			        downloader.start();
//				} else {
					goToDetailPage();
//				}
			}
			
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
		ImageChartManager.GetInstance().setCurrentChart(id, images);
		goToDetailPage();
	}
	
	private void goToDetailPage(){
		Intent i = new Intent(this, SignalDetailActivity.class);
        i.putExtra("signal", receivedSignal);
        i.putExtra("ecocal", listEcocals);
        i.putExtra("metaSignal", receivedMetaSignal);
        i.putExtra("chartUrl", chartsUrls);
        i.putExtra("id", id);
		startActivity(i);
		finish();
	}

	@Override
	public void onError() {
		super.onErrorConnection(null);
	}
}
