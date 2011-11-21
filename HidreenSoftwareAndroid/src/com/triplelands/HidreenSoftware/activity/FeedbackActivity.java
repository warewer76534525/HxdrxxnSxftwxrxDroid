package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.R;

public class FeedbackActivity extends RoboActivity {
	
	@InjectView(R.id.txtFeedback) EditText txtFeedback;
	@InjectView(R.id.btnSendFeedback) Button btnSendFeedback;
	
	public static final String SUPPORT_EMAIL = "support@amygdalahd.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		
		btnSendFeedback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (txtFeedback.getText().toString().equals("")) {
					Toast.makeText(FeedbackActivity.this, "Please fill your feedback.", Toast.LENGTH_SHORT).show();
				} else {
					Intent sendIntent = new Intent(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_EMAIL,
							new String[] { SUPPORT_EMAIL });
					sendIntent.putExtra(Intent.EXTRA_TEXT, txtFeedback.getText().toString());
					sendIntent.putExtra(Intent.EXTRA_SUBJECT,
							"[Feedback] User Feedback");
					sendIntent.setType("message/rfc822");
					startActivity(sendIntent);
					finish();
				}
				
			}
		});
	}
}
