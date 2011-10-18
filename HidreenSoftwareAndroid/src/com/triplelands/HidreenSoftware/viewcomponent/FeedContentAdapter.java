package com.triplelands.HidreenSoftware.viewcomponent;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.triplelands.HidreenSoftware.model.FeedContent;

public class FeedContentAdapter extends BaseAdapter {

	private List<FeedContent> listFeedContent;
	private Context context;

	public FeedContentAdapter(Context ctx) {
		this.context = ctx;
	}

	public int getCount() {
		return listFeedContent.size();
	}
	
	public void setList(List<FeedContent> listNews){
		this.listFeedContent = listNews;
	}

	public FeedContent getItem(int position) {
		return listFeedContent.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		FeedContent feed = listFeedContent.get(position);
		return new FeedContentAdapterView(context, feed);
	}
}
