package com.triplelands.HidreenSoftware.viewcomponent;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.model.EconomicCalendar;

public class EconomicCalendarListRow extends LinearLayout {

	public EconomicCalendarListRow(Context context, EconomicCalendar ecocal) {
		super(context);
		
		this.setOrientation(VERTICAL);
		
		View v = inflate(context, R.layout.ecocal_row, null);
		
		setTag(ecocal);
		
		TextView txtSymbol = (TextView)v.findViewById(R.id.txtCalSym);
		TextView txtTime = (TextView)v.findViewById(R.id.txtCalTime);
		TextView txtImportance = (TextView)v.findViewById(R.id.txtCalImportance);
		TextView txtDescription = (TextView)v.findViewById(R.id.txtCalDesc);
		TextView txtForecast = (TextView)v.findViewById(R.id.txtCalForecast);
		TextView txtPrev = (TextView)v.findViewById(R.id.txtCalPrev);
		TextView txtActual = (TextView)v.findViewById(R.id.txtCalActual);
		ImageView imgFlag = (ImageView)v.findViewById(R.id.imgFlag);
		
		txtSymbol.setText(ecocal.getCurrency().toUpperCase());
		txtTime.setText( "| " + ecocal.getTime() + " " + ecocal.getTimezone() + " |");
		txtImportance.setText(ecocal.getImportance().toLowerCase());
		txtImportance.setTextColor(generateImportanceColor(ecocal.getImportance()));
		txtDescription.setText(ecocal.getDescription());
		txtForecast.setText((ecocal.getForecast().equals("")) ? "-" : ecocal.getForecast());
		txtPrev.setText((ecocal.getPrevious().equals("")) ? "-" : ecocal.getPrevious());
		txtActual.setText((ecocal.getActual().equals("")) ? "-" : ecocal.getActual());
		
		if (ecocal.getImageResource() != 999) {
			imgFlag.setImageResource(ecocal.getImageResource());
		}
		
		addView(v);
	}
	
	private int generateImportanceColor(String importance){
		if (importance.toLowerCase().contains("low")) {
			return Color.rgb(0, 183, 0);
		}
		if (importance.toLowerCase().contains("medium")) {
			return Color.rgb(58, 155, 254);
		}
		if (importance.toLowerCase().contains("high")) {
			return Color.rgb(255, 102, 81);
		}
		return Color.BLACK;
	}

}
