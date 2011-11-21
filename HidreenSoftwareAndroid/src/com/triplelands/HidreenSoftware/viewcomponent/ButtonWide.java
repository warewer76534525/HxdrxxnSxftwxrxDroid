package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.triplelands.HidreenSoftware.R;

public class ButtonWide extends FrameLayout {

	private Button btn;
	
	public ButtonWide(Context context, String label) {
		super(context);
		View v = inflate(context, R.layout.wide_button, null);
		btn = (Button)v.findViewById(R.id.btnWide);
		btn.setText(label);
		addView(v);
	}
	
	public void setOnClickHandler(OnClickListener handler){
		btn.setOnClickListener(handler);
	}

}
