package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingRegisterForm;

public class LoginActivity extends RoboActivity {
	
	@InjectView(R.id.btnLogin) Button btnLogin;
	@InjectView(R.id.btnRegister) Button btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, RootActivity.class);
				startActivity(i);
				finish();
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, LoadingRegisterForm.class);
				i.putExtra("url", "http://www.hidreensoftware.com/index.php/m/members/register");
				startActivity(i);
			}
		});
	}
}
