package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.RegisterPost;
import com.triplelands.HidreenSoftware.model.TimeZone;
import com.triplelands.HidreenSoftware.utils.DataProcessor;
import com.triplelands.HidreenSoftware.utils.StringHelper;
import com.triplelands.HidreenSoftware.viewcomponent.CustomButton;
import com.triplelands.HidreenSoftware.viewcomponent.CustomDropdown;
import com.triplelands.HidreenSoftware.viewcomponent.CustomEditText;
import com.triplelands.HidreenSoftware.viewcomponent.CustomInputText;
import com.triplelands.HidreenSoftware.viewcomponent.CustomPasswordText;

public class RegisterActivity extends RoboActivity {
	
	public static final String URL_REGISTER = "http://amygdalahd.com/index.php/m/members/register";
	
	@InjectView(R.id.layoutFormRegister) LinearLayout layoutRegister;
	
	private List<CustomInputText> listField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_screen);
		
		String json = getIntent().getExtras().getString("json");
		
		List<BasicNameValuePair> data = DataProcessor.getKeyValueArray(json);
		listField = buildFields(data);
		for (int i = listField.size() - 1; i >= 0; i--) {
			layoutRegister.addView((CustomInputText)listField.get(i));
		}
		final CustomDropdown timeZone = new CustomDropdown(this, "timezone", "Time Zone", TimeZone.getKeyValueList());
		layoutRegister.addView(timeZone);
		
		CustomButton btnRegister = new CustomButton(this, "Register");
		btnRegister.setOnClickHandler(new OnClickListener() {
			public void onClick(View v) {
				boolean valid = true;
				StringBuffer param = new StringBuffer();
				for (int i = 0; i < listField.size(); i++) {
					CustomInputText cit = listField.get(i);
					if(cit.getValue().toString().equals("")){
						valid = false;
					}
					if((cit.getName().toLowerCase().contains("password"))){
						param.append(cit.getName() + "=" + StringHelper.md5("hisoft" + cit.getValue()) + "&");
					} else {
						param.append(cit.getName() + "=" + cit.getValue() + "&");
					}
				}
				param.append("timezone=" + timeZone.getValue() + "&");
				if(valid){
					param.append("__submit=register");
					System.out.println(param.toString());
					Intent i = new Intent(RegisterActivity.this, RegisterPost.class);
					i.putExtra("url", URL_REGISTER);
					i.putExtra("params", param.toString().split("&"));
					startActivityForResult(i, 0);
				} else {
					Toast.makeText(RegisterActivity.this, "Please fill in the blank field.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		layoutRegister.addView(btnRegister);
	}
	
	private List<CustomInputText> buildFields(List<BasicNameValuePair> data){
		List<CustomInputText> listField = new ArrayList<CustomInputText>();
		for (int i = 0; i < data.size(); i++) {
			BasicNameValuePair map = (BasicNameValuePair)data.get(i);
			boolean password = (map.getName().toLowerCase().contains("password"));
			CustomInputText editField;
			if(password){
				editField = new CustomPasswordText(this, map);
			} else {
				editField = new CustomEditText(this, map);
			}
			listField.add(editField);
		}
		
		data = null;
		return listField;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Toast.makeText(this, data.getExtras().getString("message"), Toast.LENGTH_LONG).show();
			finish();
		}
	}
}
