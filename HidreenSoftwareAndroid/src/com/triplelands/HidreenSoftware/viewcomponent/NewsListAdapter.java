package com.triplelands.HidreenSoftware.viewcomponent;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.triplelands.HidreenSoftware.model.NewsFeed;

public class NewsListAdapter extends BaseAdapter {

	private List<NewsFeed> listNews;
	private Context context;

	public NewsListAdapter(Context ctx) {
		this.context = ctx;
	}

	public int getCount() {
		return listNews.size();
	}
	
	public void setList(List<NewsFeed> listNews){
		this.listNews = listNews;
	}

	public NewsFeed getItem(int position) {
		return listNews.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		NewsFeed news = listNews.get(position);
		return new NewsAdapterView(context, news);
	}

}
