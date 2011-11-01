package com.triplelands.HidreenSoftware.viewcomponent;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;

public class CustomDropdown extends LinearLayout {

	private String[] arrChoiceValue;
	private Spinner dropdownMenu;
	private List<BasicNameValuePair> listNvp;
	private String label;
	private String name;

	public CustomDropdown(Context context, String name, String label, List<BasicNameValuePair> listNvp) {
		super(context);
		this.listNvp = listNvp;
		this.label = label;
		this.name = name;
		setPadding(15, 5, 15, 5);
		setOrientation(VERTICAL);
		initDropdownMenu(context);
	}
	
	private void initDropdownMenu(Context context) {
		addLabel(context);
				
		arrChoiceValue = new String[listNvp.size()];
		String[] arrChoiceLabel = new String[listNvp.size()];
		for(int i=0; i < listNvp.size(); i++){
			arrChoiceValue[i] = listNvp.get(i).getName();
			arrChoiceLabel[i] = listNvp.get(i).getValue();
		}
		
		dropdownMenu = new Spinner(context);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_content, arrChoiceLabel);
		dropdownMenu.setAdapter(adapter);
		addView(dropdownMenu);
	}
	
	private void addLabel(Context context){
		TextView txtLabel = new TextView(context);
		txtLabel.setPadding(5, 5, 5, 0);
		txtLabel.setTextColor(Color.BLACK);
		txtLabel.setText(label);
		addView(txtLabel);
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue(){
		return arrChoiceValue[dropdownMenu.getSelectedItemPosition()];
	}

}
