package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.EconomicCalendar;
import com.triplelands.HidreenSoftware.model.Signal;
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
		setContentView(R.layout.signal_detail);
		
		DataManager.getInstance(this).addHistory(this);
		
		Signal signal = (Signal) getIntent().getExtras().getSerializable("signal");
		String metaSignal = getIntent().getExtras().getString("metaSignal");
		txtTitleDetail.setText(signal.getSymbol());
		txtDetail.setText(Html.fromHtml(metaSignal.replaceAll("\n", "<br/>")));
		
		//images
		List<Bitmap> images = (ArrayList<Bitmap>) getIntent().getExtras().getSerializable("images");
		if (images != null && images.size() > 0) {
			layoutDetailSignal.addView(new ImageChartScrollViewer(this, images));
		}
		
		//economic calendars
		List<EconomicCalendar> ecocals = (ArrayList<EconomicCalendar>) getIntent().getExtras().getSerializable("ecocal");
		if (ecocals != null && ecocals.size() > 0) {
			layoutDetailSignal.addView(new EconomicCalendarLayout(this, "Economic Calendar", ecocals));
		}
	}
}
