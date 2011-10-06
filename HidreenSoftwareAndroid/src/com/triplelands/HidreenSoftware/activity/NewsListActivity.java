package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;

import com.triplelands.HidreenSoftware.R;

public class NewsListActivity extends RoboActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list);
	}
}
