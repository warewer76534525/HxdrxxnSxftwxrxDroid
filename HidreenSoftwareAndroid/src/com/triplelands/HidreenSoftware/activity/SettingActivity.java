package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.app.Setting;

public class SettingActivity extends RoboActivity {

	@InjectView(R.id.layoutEcoImportance) LinearLayout layoutEcoImportance;
	@InjectView(R.id.checkBoxEcocal) CheckBox checkBoxEcocal;
	@InjectView(R.id.checkBoxEcoHigh) CheckBox checkBoxEcoHigh;
	@InjectView(R.id.CheckBoxEcoMed) CheckBox checkBoxEcoMed;
	@InjectView(R.id.CheckBoxEcoLow) CheckBox checkBoxEcoLow;
	@InjectView(R.id.btnSaveSetting) Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		populatePage();

		checkBoxEcocal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateLayoutImportance();
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				save();
			}
		});
	}

	private void save(){
		if (checkBoxEcocal.isChecked() && !checkBoxEcoHigh.isChecked()
				&& !checkBoxEcoMed.isChecked() && !checkBoxEcoLow.isChecked()) {
			Toast.makeText(SettingActivity.this, "Please select at least one importance level to be displayed", Toast.LENGTH_SHORT).show();
		} else {
			updateSetting();
			finish();
		}
	}
	
	private void updateSetting() {
		Setting setting = Setting.getInstance(this);
		setting.setDisplayEcocal(checkBoxEcocal.isChecked());
		if (checkBoxEcocal.isChecked()) {
			setting.setDisplayEcoHigh(checkBoxEcoHigh.isChecked());
			setting.setDisplayEcoMed(checkBoxEcoMed.isChecked());
			setting.setDisplayEcoLow(checkBoxEcoLow.isChecked());
		} else {
			setting.setDisplayEcoHigh(false);
			setting.setDisplayEcoMed(false);
			setting.setDisplayEcoLow(false);
		}
	}

	private void populatePage() {
		Setting setting = Setting.getInstance(this);
		checkBoxEcocal.setChecked(setting.isDisplayEcocal());
		checkBoxEcoHigh.setChecked(setting.isDisplayEcoHigh());
		checkBoxEcoMed.setChecked(setting.isDisplayEcoMed());
		checkBoxEcoLow.setChecked(setting.isDisplayEcoLow());
		updateLayoutImportance();
	}

	private void updateLayoutImportance() {
		if (checkBoxEcocal.isChecked()) {
			layoutEcoImportance.setVisibility(View.VISIBLE);
		} else {
			layoutEcoImportance.setVisibility(View.GONE);
		}
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("")
				.setMessage("Save change before exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dlg, int sumthin) {
								save();
							}
						})
				.setNeutralButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						finish();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dlg, int sumthin) {
							}
						}).show();
	}

}
