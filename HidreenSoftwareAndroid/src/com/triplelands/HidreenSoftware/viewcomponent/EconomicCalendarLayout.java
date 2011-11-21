package com.triplelands.HidreenSoftware.viewcomponent;

import java.util.List;

import com.triplelands.HidreenSoftware.model.EconomicCalendar;

import android.content.Context;
import android.widget.LinearLayout;

public class EconomicCalendarLayout extends LinearLayout {

	public EconomicCalendarLayout(Context context, String title, List<EconomicCalendar> listEcocal) {
		super(context);
		
		setOrientation(VERTICAL);
		
		LinearLayout space = new LinearLayout(context);
		space.setPadding(0, 10, 0, 0);
		addView(space);
		addView(new TitleLabel(context, title));
		
		for (int i = 0; i < listEcocal.size(); i++) {
			addView(new EconomicCalendarListRow(context, listEcocal.get(i)));
		}
	}

}
