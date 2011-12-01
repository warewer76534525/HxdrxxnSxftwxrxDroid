package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingListSignals;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.Category;
import com.triplelands.HidreenSoftware.model.Signal;
import com.triplelands.HidreenSoftware.viewcomponent.SignalListRow;
import com.triplelands.HidreenSoftware.viewcomponent.TitleLabel;

public class SignalListActivity extends RoboActivity {

	public static final String URL_SIGNALS = "http://amygdalahd.com/index.php/m/signals";
	
	@InjectView(R.id.signalListLayout) LinearLayout listSignalLayout;
	@InjectView(R.id.btnRefreshSignals) ImageButton btnRefreshSignals;
	@InjectView(R.id.txtExpired) TextView txtExpired;
	@InjectView(R.id.txtLicense) TextView txtLicense;
	@InjectView(R.id.txtFlashNews) TextView txtFlashNews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		setContentView(R.layout.signal_list);
		
		btnRefreshSignals.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadSignalsData();
			}
		});
		
		loadSignalsData();
	}
	
	private void loadSignalsData(){
		Intent i = new Intent(this, LoadingListSignals.class);
		i.putExtra("url", URL_SIGNALS);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			try {
				listSignalLayout.removeAllViews();
				@SuppressWarnings("unchecked")
				ArrayList<Category> cats = (ArrayList<Category>) data.getExtras().getSerializable("categories");
				for (int i = 0; i < cats.size(); i++) {
					listSignalLayout.addView(new TitleLabel(this, cats.get(i).getName()));
					List<Signal> signals = cats.get(i).getSignals();
					for (int j = 0; j < signals.size(); j++) {
						listSignalLayout.addView(new SignalListRow(this, signals.get(j)));
					}
				}
			} catch (Exception e) {
			}
			
			txtExpired.setText("Account expired on: " + DataManager.getInstance(this).getAccountExpired());
			if (!DataManager.getInstance(this).getLicense().equals("")) {
				txtLicense.setText("License: " + DataManager.getInstance(this).getLicense());
			} else {
				txtLicense.setText(Html.fromHtml("License: <i>Please logout and login again to generate your license</i>"));
			}
			
			if (!DataManager.getInstance(this).getFlashNews().equals("")) {
				txtFlashNews.setText(DataManager.getInstance(this).getFlashNews());
				txtFlashNews.setVisibility(View.VISIBLE);
			} else {
				txtFlashNews.setVisibility(View.GONE);
			}
		}
	}
}
