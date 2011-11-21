package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.webkit.WebView;

import com.triplelands.HidreenSoftware.R;

public class ImageViewerActivity extends RoboActivity {

	@InjectView(R.id.wvImageViewer) WebView wvImageViewer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		setContentView(R.layout.image_viewer);
		
		String path = getIntent().getExtras().getString("path");
		
		wvImageViewer.loadUrl("file://" + path);
		wvImageViewer.getSettings().setSupportZoom(true);
		wvImageViewer.getSettings().setBuiltInZoomControls(true);
	}
}
