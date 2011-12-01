package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.webkit.WebView;

import com.triplelands.HidreenSoftware.R;

public class NewsViewerActivity extends RoboActivity {

	@InjectView(R.id.wvNews) WebView wvNews;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		setContentView(R.layout.news_viewer);
		
		setTitle("Displaying news...");
		
		String url = getIntent().getExtras().getString("url");
		wvNews.loadUrl(url);
		wvNews.getSettings().setJavaScriptEnabled(true);
		wvNews.getSettings().setPluginsEnabled(true);
		wvNews.getSettings().setSupportZoom(true);
		wvNews.getSettings().setBuiltInZoomControls(true);
	}
}
