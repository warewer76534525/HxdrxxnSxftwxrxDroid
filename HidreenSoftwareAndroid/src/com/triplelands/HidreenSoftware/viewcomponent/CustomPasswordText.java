package com.triplelands.HidreenSoftware.viewcomponent;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class CustomPasswordText extends CustomInputText {
	
	public CustomPasswordText(Context context, BasicNameValuePair nvp) {
		super(context, nvp);
		setPassword(true);
	}
	
	@Override
	public String getName() {
		return super.getName();
	}
	
	@Override
	public String getValue() {
		return super.getValue();
	}
}
