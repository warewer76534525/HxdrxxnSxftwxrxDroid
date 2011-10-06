package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboTabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;

public class RootActivity extends RoboTabActivity {
	
	private TabHost mTabHost;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root);
		setTitle("Hidreen Software");
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		Intent intent;
		intent = new Intent().setClass(this, SignalListActivity.class);
		setupTab(intent, "Signal", R.drawable.tab_img_selector_history);
		intent = new Intent().setClass(this, NewsListActivity.class);
		setupTab(intent, "News", R.drawable.tab_img_selector_setting);
	}

	private void setupTab(final Intent intent, final String tag, int imageId) {
		View tabview = createTabView(mTabHost.getContext(), tag, imageId);
	        TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text, int imageId) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		ImageView img = (ImageView)view.findViewById(R.id.tabsImg);
		img.setImageResource(imageId);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
}
