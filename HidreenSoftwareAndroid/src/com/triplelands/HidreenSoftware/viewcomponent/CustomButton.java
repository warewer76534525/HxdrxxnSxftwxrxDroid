package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;

import com.triplelands.HidreenSoftware.R;

public class CustomButton extends LinearLayout {

	private Button button;
	
	public CustomButton(Context context, String label) {
		super(context);
		setPadding(15, 5, 5, 5);		
		initButton(context, label);
	}

	private void initButton(Context context, String label) {
		button = new Button(context);
		button.setTextColor(Color.WHITE);
		button.setText(label);
		button.setBackgroundResource(R.drawable.button_green);
		addView(button);
	}
	
	public void setOnClickHandler(OnClickListener handler){
		button.setOnClickListener(handler);
	}

}
