package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingAbout;

public class AboutActivity extends RoboActivity {
	
	public static final String URL_ABOUT = "http://amygdalahd.com/m/pages/view/1"; 
	
	@InjectView(R.id.txtAbout) TextView txtAbout;
	@InjectView(R.id.txtAppVersion) TextView txtVersion;
	@InjectView(R.id.btnRefreshAbout) ImageButton btnRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		txtVersion.setText("Version " + getVersionName() + " for Android. 2011.");
		
		btnRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadAboutContent();
			}
		});
		
		loadAboutContent();
	}
	
	private void loadAboutContent(){
		Intent i = new Intent(this, LoadingAbout.class);
		i.putExtra("url", URL_ABOUT);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			txtAbout.setText(Html.fromHtml(data.getStringExtra("content")));
		}
	}
	
	private String getVersionName() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (Exception e) {
			return "";
		}
	}
}
