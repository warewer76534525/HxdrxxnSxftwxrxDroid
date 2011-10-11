package com.triplelands.HidreenSoftware.viewcomponent;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.graphics.Color;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomInputText extends LinearLayout {

	private EditText editText;
	private BasicNameValuePair nvp;
	
	public CustomInputText(Context context, BasicNameValuePair nvp) {
		super(context);
		this.nvp = nvp;
		setPadding(15, 5, 15, 0);
		setOrientation(VERTICAL);
		initEditText(context);
	}
	
	private void initEditText(Context context){		
		TextView txt = new TextView(context);
		txt.setTextColor(Color.BLACK);
		txt.setText(nvp.getValue());
		txt.setPadding(5, 5, 5, 0);
		
		editText = new EditText(context);
		editText.setWidth(LayoutParams.FILL_PARENT);
		//editText.setHint(nvp.getValue());
		
		addView(txt);
		addView(editText);
	}
	
	public void setPassword(boolean password){
		if(password) editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
	
//	public int getFilter() {
//		String filter = node.getAttribute("filter");
//		if (filter == null) return InputType.TYPE_CLASS_TEXT;
//			
//		System.out.println("filter: " + filter);
//		if (filter.toUpperCase().equals("INTEGER")
//				|| filter.toUpperCase().equals("NUMBER")
//				|| filter.toUpperCase().equals("NUMERIC")) {
//			return InputType.TYPE_CLASS_NUMBER;
//		}
//		if (filter.toUpperCase().equals("EMAIL")) {
//			return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
//		}
//		if (filter.toUpperCase().equals("PHONE")
//				|| filter.toUpperCase().equals("PHONE_NUMBER")) {
//			return InputType.TYPE_CLASS_PHONE;
//		}
//
//		return InputType.TYPE_CLASS_TEXT;
//	}

	public String getName() {
		return nvp.getValue();
	}

	public String getValue() {
		return editText.getText().toString();
	}

}
