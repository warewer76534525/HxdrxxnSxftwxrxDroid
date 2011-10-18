package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingFeedContent;
import com.triplelands.HidreenSoftware.activity.loading.LoadingNewsList;
import com.triplelands.HidreenSoftware.model.NewsFeed;
import com.triplelands.HidreenSoftware.viewcomponent.NewsListAdapter;

public class NewsListActivity extends RoboActivity {

	@InjectView(R.id.lvNews) ListView lvNews;
	@InjectView(R.id.btnRefreshFeed) ImageButton btnRefreshFeeds;
	
	public static final String URL_NEWS_FEED = "http://hidreensoftware.com/m/rss/index";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list);
		
		loadNewsFeed();
		
		btnRefreshFeeds.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadNewsFeed();
			}
		});
		
		lvNews.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				String feedId = ((NewsFeed)view.getTag()).getId();
				Intent i = new Intent(NewsListActivity.this, LoadingFeedContent.class);
				i.putExtra("url", "http://hidreensoftware.com/m/rss/site/" + feedId);
				i.putExtra("title", ((NewsFeed)view.getTag()).getName());
				startActivity(i);
			}
		});	
	}
	
	private void loadNewsFeed(){
		Intent i = new Intent(this, LoadingNewsList.class);
		i.putExtra("url", URL_NEWS_FEED);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			@SuppressWarnings("unchecked")
			ArrayList<NewsFeed> feeds = (ArrayList<NewsFeed>) data.getExtras().getSerializable("feeds");
			
			Log.i("HS", "Size: " + feeds.size());
			
			NewsListAdapter adapter = new NewsListAdapter(this);
			adapter.setList(feeds);
			
			lvNews.setAdapter(adapter);
		}
	}
}
