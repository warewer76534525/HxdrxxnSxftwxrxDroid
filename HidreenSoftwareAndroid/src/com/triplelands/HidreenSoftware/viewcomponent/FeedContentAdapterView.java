package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.model.FeedContent;

public class FeedContentAdapterView extends LinearLayout {

	public FeedContentAdapterView(Context context, FeedContent feed) {
		super(context);
		
		View v = inflate(context, R.layout.feeds_row, null);
		
		setTag(feed);
				
		TextView txtTitle = (TextView)v.findViewById(R.id.txtFeedTitle);
		TextView txtFeedDate = (TextView)v.findViewById(R.id.txtFeedDate);
		txtTitle.setText(Html.fromHtml(feed.getTitle()));
		txtFeedDate.setText(feed.getDate());
		
		addView(v);
	}
}
