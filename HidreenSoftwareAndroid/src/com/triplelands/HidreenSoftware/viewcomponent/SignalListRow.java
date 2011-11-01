package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.SignalListActivity;
import com.triplelands.HidreenSoftware.activity.loading.LoadingSignalDetail;
import com.triplelands.HidreenSoftware.model.Signal;

public class SignalListRow extends LinearLayout {
	
	public SignalListRow(final Context context, final Signal signal) {
		super(context);
		this.setOrientation(VERTICAL);
		
		View v = inflate(context, R.layout.signal_list_row, null);
		
		setTag(signal);
		
		TextView txtSymbol = (TextView)v.findViewById(R.id.txtSymbol);
		TextView txtDirection = (TextView)v.findViewById(R.id.txtDirection);
		TextView txtPercentage = (TextView)v.findViewById(R.id.txtPercentage);
		ImageView imgDirection = (ImageView)v.findViewById(R.id.imgDirection);
		ImageView imgNotif = (ImageView)v.findViewById(R.id.imgNotif);
		
		txtSymbol.setText(signal.getSymbol());
		txtDirection.setText((signal.isUp())? "UP":"DOWN");
		txtPercentage.setText("" + signal.getProbability() + "%");
		imgDirection.setImageResource((signal.isUp()) ? R.drawable.green : R.drawable.red);
		if (signal.isUnread()){
			imgNotif.setVisibility(View.VISIBLE);
		} else {
			imgNotif.setVisibility(View.GONE);
		}
		
		addView(v);
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Clicked: " + signal.getSymbol());
				Intent i = new Intent(context, LoadingSignalDetail.class);
				i.putExtra("url", SignalListActivity.URL_SIGNALS + "/view/" + signal.getId());
				context.startActivity(i);
			}
		});
	}
}
