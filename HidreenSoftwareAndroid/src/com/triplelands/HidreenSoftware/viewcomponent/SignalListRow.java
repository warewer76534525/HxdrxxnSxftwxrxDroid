package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.model.Signal;

public class SignalListRow extends LinearLayout {
	
	public SignalListRow(Context context, Signal signal) {
		super(context);
		this.setOrientation(VERTICAL);
		
		View v = inflate(context, R.layout.signal_list_row, null);
		
		setTag(signal);
		
		TextView txtSymbol = (TextView)v.findViewById(R.id.txtSymbol);
		TextView txtDirection = (TextView)v.findViewById(R.id.txtDirection);
		TextView txtPercentage = (TextView)v.findViewById(R.id.txtPercentage);
		ImageView imgDirection = (ImageView)v.findViewById(R.id.imgDirection);
		
		txtSymbol.setText(signal.getSymbol());
		txtDirection.setText((signal.isUp())? "UP":"DOWN");
		txtPercentage.setText("" + signal.getProbability() + "%");
		imgDirection.setImageResource((signal.isUp()) ? R.drawable.green : R.drawable.red);
		
		addView(v);
	}
	
}
