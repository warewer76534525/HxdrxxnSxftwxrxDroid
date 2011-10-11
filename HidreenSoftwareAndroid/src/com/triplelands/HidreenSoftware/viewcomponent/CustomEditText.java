package com.triplelands.HidreenSoftware.viewcomponent;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class CustomEditText extends CustomInputText {

	public CustomEditText(Context context, BasicNameValuePair nvp) {
		super(context, nvp);
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
