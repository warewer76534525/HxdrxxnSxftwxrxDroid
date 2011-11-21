package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.EconomicCalendar;
import com.triplelands.HidreenSoftware.model.Signal;
import com.triplelands.HidreenSoftware.utils.ImageChartManager;
import com.triplelands.HidreenSoftware.viewcomponent.ButtonWide;
import com.triplelands.HidreenSoftware.viewcomponent.EconomicCalendarLayout;
import com.triplelands.HidreenSoftware.viewcomponent.ImageChartScrollViewer;

public class SignalDetailActivity extends RoboActivity {

	@InjectView(R.id.txtTitleDetail) TextView txtTitleDetail;
	@InjectView(R.id.txtDetailSignal) TextView txtDetail;
	@InjectView(R.id.layoutDetailSignal) LinearLayout layoutDetailSignal;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		setContentView(R.layout.signal_detail);
		
		DataManager.getInstance(this).addHistory(this);
		
		Signal signal = (Signal) getIntent().getExtras().getSerializable("signal");
		String metaSignal = getIntent().getExtras().getString("metaSignal");
		String id = getIntent().getExtras().getString("id");
		final String chartUrls[] = getIntent().getExtras().getStringArray("chartUrl");
				
		txtTitleDetail.setText(signal.getSymbol());
		txtDetail.setText(Html.fromHtml(metaSignal.replaceAll("\n", "<br/>")));
		
		//images
//		List<Bitmap> images = (ArrayList<Bitmap>) getIntent().getExtras().getSerializable("images");
		List<Bitmap> images = ImageChartManager.GetInstance().getCurrentChart(id);
		if (images != null && images.size() > 0) {
			layoutDetailSignal.addView(new ImageChartScrollViewer(this, images));
		}
		
		if (chartUrls!= null && chartUrls.length > 0) {
			ButtonWide btn = new ButtonWide(this, "View Charts");
			btn.setOnClickHandler(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(SignalDetailActivity.this, ChartViewerActivity.class);
					i.putExtra("chartsUrl", chartUrls);
					startActivity(i);
				}
			});
			layoutDetailSignal.addView(btn);
		}
		
		//economic calendars
		List<EconomicCalendar> ecocals = (ArrayList<EconomicCalendar>) getIntent().getExtras().getSerializable("ecocal");
		if (ecocals != null && ecocals.size() > 0) {
			layoutDetailSignal.addView(new EconomicCalendarLayout(this, "Economic Calendar", ecocals));
		}
	}
}
