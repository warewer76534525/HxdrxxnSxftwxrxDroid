package com.triplelands.HidreenSoftware.activity;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.EditProfilePost;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.model.TimeZone;

public class ProfileActivity extends RoboActivity {
	
	private DataManager manager;
	private boolean isEditState;
	
	public static final String URL_UPDATE_PROFILE = "http://amygdalahd.com/m/profiles/update";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = DataManager.getInstance(this);
		populateProfile();
	}
	
	private void populateProfile(){
		isEditState = false;
		setContentView(R.layout.profile);
		TextView txtProfileName = (TextView)findViewById(R.id.txtProfileName);
		TextView txtProfileEmail = (TextView)findViewById(R.id.txtProfileEmail);
		TextView txtProfileCity = (TextView)findViewById(R.id.txtProfileCity);
		TextView txtProfileCountry = (TextView)findViewById(R.id.txtProfileCountry);
		TextView txtProfilePhone = (TextView)findViewById(R.id.txtProfilePhone);
		TextView txtProfileTimezone = (TextView)findViewById(R.id.txtProfileTimezone);
		TextView txtProfileExpired = (TextView)findViewById(R.id.txtProfileExpired);
		ImageButton btnEditProfile = (ImageButton)findViewById(R.id.btnEditProfile);
				
		txtProfileName.setText(manager.getName());
		txtProfileEmail.setText(manager.getEmail());
		txtProfileCountry.setText(manager.getCountry());
		txtProfileCity.setText(manager.getCity());
		txtProfilePhone.setText(manager.getPhone());
		if (manager.getTimezone().equals("")) {
			txtProfileTimezone.setText(Html.fromHtml("<i><font size = '10'>Please logout and login again to update your timezone</font></i>"));
		} else {
			txtProfileTimezone.setText(TimeZone.getTimezoneString(manager.getTimezone()));
		}
		
		txtProfileExpired.setText(manager.getAccountExpired());
		
		btnEditProfile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				populateEditProfile();
			}
		});
	}
	
	private void populateEditProfile(){
		isEditState = true;
		setContentView(R.layout.edit_profile);
		final EditText txtName = (EditText) findViewById(R.id.txtName);
		final EditText txtCountry = (EditText) findViewById(R.id.txtCountry);
		final EditText txtCity = (EditText) findViewById(R.id.txtCity);
		final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
		final Spinner cbTimezone = (Spinner) findViewById(R.id.cbTimezone);
		
		txtName.setText(manager.getName());
		txtCountry.setText(manager.getCountry());
		txtCity.setText(manager.getCity());
		txtPhone.setText(manager.getPhone());
		
		List<BasicNameValuePair> listTimezone = TimeZone.getKeyValueList();
		String[] arrTimezone = new String[listTimezone.size()];
		for(int i=0; i < listTimezone.size(); i++){
			arrTimezone[i] = listTimezone.get(i).getValue();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_content, arrTimezone);
		cbTimezone.setAdapter(adapter);
		cbTimezone.setSelection(TimeZone.getIndex(manager.getTimezone()));
		
		Button btnSave = (Button) findViewById(R.id.btnSaveProfile);
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (txtName.getText().toString().equals("") || txtCity.getText().toString().equals("")
					|| txtCountry.getText().toString().equals("") || txtPhone.getText().toString().equals("")){
					Toast.makeText(ProfileActivity.this, "Please fill all the blank field.", Toast.LENGTH_SHORT).show();
				} else {
					String[] param = {"name=" + txtName.getText().toString(),
							"country=" + txtCountry.getText().toString(),
							"city=" + txtCity.getText().toString(),
							"phone=" + txtPhone.getText().toString(),
							"timezone=" + TimeZone.getKey(cbTimezone.getSelectedItemPosition())}; 
					Intent i = new Intent(ProfileActivity.this, EditProfilePost.class);
					i.putExtra("url", URL_UPDATE_PROFILE);
					i.putExtra("params", param);
					startActivityForResult(i, 0);
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			updatePersistentProfile(data.getExtras().getStringArray("params"));
			populateProfile();
			Toast.makeText(this, data.getExtras().getString("message"), Toast.LENGTH_LONG).show();
		}
	}
	
	private void updatePersistentProfile(String[] arrProfile){
		DataManager manager = DataManager.getInstance(this);
		for (int i = 0; i < arrProfile.length; i++) {
			String key = arrProfile[i].split("=")[0];
			String value = arrProfile[i].split("=")[1];
			if (key.equals("name")) {
				manager.setName(value);
			} else if (key.equals("country")){
				manager.setCountry(value);
			} else if (key.equals("city")){
				manager.setCity(value);
			} else if (key.equals("phone")){
				manager.setPhone(value);
			} else if (key.equals("timezone")){
				manager.setTimezone(value);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		if (isEditState) {
			populateProfile();
		} else {
			super.onBackPressed();
		}
	}
}
