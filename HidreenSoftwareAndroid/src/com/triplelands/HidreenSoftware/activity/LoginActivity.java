package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingRegisterForm;
import com.triplelands.HidreenSoftware.activity.loading.LoginPost;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.utils.StringHelper;

public class LoginActivity extends RoboActivity {
	public static final String URL_LOGIN = "http://amygdalahd.com/index.php/m/members/login";
	
	@InjectView(R.id.btnLogin) Button btnLogin;
	@InjectView(R.id.btnRegister) Button btnRegister;
	@InjectView(R.id.txtEmailLogin) EditText txtEmailLogin;
	@InjectView(R.id.txtPasswordLogin) EditText txtPasswordLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		final DataManager manager = DataManager.getInstance(this);
		
		if (!manager.isRegisteredForPush()) {
			manager.registerForC2DM(this);
		}
		
		if (manager.isLoggedIn()) {
			goToHomeScreen();
		} else {
			txtEmailLogin.setText(manager.getEmail());
		}
		
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (txtEmailLogin.getText().toString().equals("") || txtPasswordLogin.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "Please fill your login information.", Toast.LENGTH_SHORT).show();
				} else {
//					if (!manager.isRegisteredForPush()) {
//						manager.registerForC2DM(LoginActivity.this);
//						Toast.makeText(LoginActivity.this, "Server is busy. Please try again.", Toast.LENGTH_SHORT).show();
//					} else {
						String params[] = {"email=" + txtEmailLogin.getText().toString(), 
								"password=" + StringHelper.md5("hisoft" + txtPasswordLogin.getText().toString()),
								"type=android",
								"osversion=" + Build.VERSION.SDK_INT,
								"model=" + android.os.Build.MODEL,
								"address=" + manager.getC2DMRegistrationId(),
								"__submit=login"};
						
						Intent i = new Intent(LoginActivity.this, LoginPost.class);
						i.putExtra("url",URL_LOGIN);
						i.putExtra("params", params);
						startActivityForResult(i, 0);
//					}
				}
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, LoadingRegisterForm.class);
				i.putExtra("url", "http://amygdalahd.com/index.php/m/members/register");
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			goToHomeScreen();
		}
	}

	private void goToHomeScreen() {
		Intent i = new Intent(this, RootActivity.class);
		startActivity(i);
		finish();
	}
}
