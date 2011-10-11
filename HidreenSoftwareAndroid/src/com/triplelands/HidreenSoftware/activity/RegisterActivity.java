package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.utils.DataProcessor;
import com.triplelands.HidreenSoftware.viewcomponent.CustomEditText;
import com.triplelands.HidreenSoftware.viewcomponent.CustomInputText;
import com.triplelands.HidreenSoftware.viewcomponent.CustomPasswordText;

public class RegisterActivity extends RoboActivity {
	
	@InjectView(R.id.layoutFormRegister) LinearLayout layoutRegister;
	
	private List<CustomInputText> listField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_screen);
		
		String json = getIntent().getExtras().getString("json");
		
		List<BasicNameValuePair> data = DataProcessor.getKeyValueArray(json);
		listField = buildFields(data);
		for (int i = 0; i < listField.size(); i++) {
			layoutRegister.addView((CustomInputText)listField.get(i));
		}
	}
	
	private List<CustomInputText> buildFields(List<BasicNameValuePair> data){
		List<CustomInputText> listField = new ArrayList<CustomInputText>();
		for (int i = 0; i < data.size(); i++) {
			BasicNameValuePair map = (BasicNameValuePair)data.get(i);
			boolean password = (map.getName().toLowerCase().indexOf("password") != -1);
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
}
