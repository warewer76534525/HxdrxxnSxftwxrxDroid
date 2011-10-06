package com.triplelands.HidreenSoftware.viewcomponent;

import com.triplelands.HidreenSoftware.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleLabel extends LinearLayout {

	public TitleLabel(Context context, String label) {
		super(context);
		this.setOrientation(VERTICAL);

		View v = inflate(context, R.layout.title_label, null);

		TextView txtLabel = (TextView) v.findViewById(R.id.txtLabel);
		txtLabel.setText(label);

		addView(v);
	}
}
