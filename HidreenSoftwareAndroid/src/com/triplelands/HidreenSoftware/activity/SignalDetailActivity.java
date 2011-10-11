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
import com.triplelands.HidreenSoftware.model.Signal;
import com.triplelands.HidreenSoftware.viewcomponent.ImageChartScrollViewer;

public class SignalDetailActivity extends RoboActivity {

	@InjectView(R.id.txtTitleDetail) TextView txtTitleDetail;
	@InjectView(R.id.txtDetailSignal) TextView txtDetail;
	@InjectView(R.id.layoutDetailSignal) LinearLayout layoutDetailSignal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signal_detail);
		
		Signal signal = (Signal) getIntent().getExtras().getSerializable("signal");
		String metaSignal = getIntent().getExtras().getString("metaSignal");
		@SuppressWarnings("unchecked")
		List<Bitmap> images = (ArrayList<Bitmap>) getIntent().getExtras().getSerializable("images");
		
		txtTitleDetail.setText(signal.getSymbol());
		txtDetail.setText(Html.fromHtml(metaSignal.replaceAll("\n", "<br/>")));
		
//		InputStream is = this.getResources().openRawResource (R.drawable.jaka);
//		Bitmap img = ImageUtils.resizeImage(is, 300, 200);
//		
//		List<Bitmap> imgs = new ArrayList<Bitmap>();
//		imgs.add(img);
//		imgs.add(img);
		
		layoutDetailSignal.addView(new ImageChartScrollViewer(this, images));
	}
}
