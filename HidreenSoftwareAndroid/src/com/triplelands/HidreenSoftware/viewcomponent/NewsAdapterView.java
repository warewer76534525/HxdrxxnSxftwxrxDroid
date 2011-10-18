package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.model.NewsFeed;

public class NewsAdapterView extends LinearLayout {

	public NewsAdapterView(Context context, NewsFeed news) {
		super(context);
		this.setOrientation(VERTICAL);
		
		View v = inflate(context, R.layout.news_list_row, null);
		
		setTag(news);
		
		TextView txtName = (TextView)v.findViewById(R.id.txtLabelNews);
		txtName.setText(news.getName());
		
		addView(v);
	}

}
