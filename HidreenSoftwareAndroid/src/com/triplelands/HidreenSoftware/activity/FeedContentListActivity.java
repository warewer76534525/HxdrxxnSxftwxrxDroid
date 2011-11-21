package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.FeedContent;
import com.triplelands.HidreenSoftware.viewcomponent.FeedContentAdapter;

public class FeedContentListActivity extends RoboActivity {

	@InjectView(R.id.lvFeedContents) ListView lvFeedContents;
	@InjectView(R.id.txtFeedSite) TextView txtTitle;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandlesr(new UncaughtExceptionHandler(this));
		setContentView(R.layout.feed_contents_list);
		
		DataManager.getInstance(this).addHistory(this);
		
		@SuppressWarnings("unchecked")
		List<FeedContent> contents = (ArrayList<FeedContent>)getIntent().getExtras().getSerializable("contents");
		String title = getIntent().getExtras().getString("title");
		
		txtTitle.setText(title + " Feeds");
		
		FeedContentAdapter adapter = new FeedContentAdapter(this);
		adapter.setList(contents);
		
		lvFeedContents.setAdapter(adapter);
		
		lvFeedContents.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Intent i = new Intent(FeedContentListActivity.this, NewsViewerActivity.class);
				i.putExtra("url", ((FeedContent)view.getTag()).getLink());
				startActivity(i);
			}
		});
	}
}
