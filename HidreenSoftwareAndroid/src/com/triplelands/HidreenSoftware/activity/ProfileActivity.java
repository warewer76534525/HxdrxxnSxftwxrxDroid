package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.app.DataManager;

public class ProfileActivity extends RoboActivity {

	@InjectView(R.id.txtProfileName) TextView txtProfileName;
	@InjectView(R.id.txtProfileEmail) TextView txtProfileEmail;
	@InjectView(R.id.txtProfileCity) TextView txtProfileCity;
	@InjectView(R.id.txtProfileCountry) TextView txtProfileCountry;
	@InjectView(R.id.txtProfilePhone) TextView txtProfilePhone;
	@InjectView(R.id.txtProfileExpired) TextView txtProfileExpired;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		DataManager manager = DataManager.getInstance(this);
		txtProfileName.setText(manager.getName());
		txtProfileEmail.setText(manager.getEmail());
		txtProfileCountry.setText(manager.getCountry());
		txtProfileCity.setText(manager.getCity());
		txtProfilePhone.setText(manager.getPhone());
		txtProfileExpired.setText(manager.getAccountExpired());
	}
}
