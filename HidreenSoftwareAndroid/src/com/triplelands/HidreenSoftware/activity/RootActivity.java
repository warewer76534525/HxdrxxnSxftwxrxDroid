package com.triplelands.HidreenSoftware.activity;

import roboguice.activity.RoboTabActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LogoutLoading;
import com.triplelands.HidreenSoftware.app.DataManager;
import com.triplelands.HidreenSoftware.utils.NotificationUtil;

public class RootActivity extends RoboTabActivity {
	
	private TabHost mTabHost;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		setContentView(R.layout.root);
				
		if (!DataManager.getInstance(this).isRegisteredForPush()) {
			DataManager.getInstance(this).registerForC2DM(this);
		}
		
		DataManager.getInstance(this).addHistory(this);
		NotificationUtil.getInstance().clear();
		
		setTitle("AmygdalaHD");
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		Intent intent;
		intent = new Intent().setClass(this, SignalListActivity.class);
		setupTab(intent, "Signal", R.drawable.tab_img_selector_history);
		intent = new Intent().setClass(this, NewsListActivity.class);
		setupTab(intent, "News", R.drawable.tab_img_selector_news);
		intent = new Intent().setClass(this, ProfileActivity.class);
		setupTab(intent, "Profile", R.drawable.tab_img_selector_profile);
		intent = new Intent().setClass(this, AboutActivity.class);
		setupTab(intent, "About", R.drawable.tab_img_selector_about);
	}

	private void setupTab(final Intent intent, final String tag, int imageId) {
		View tabview = createTabView(mTabHost.getContext(), tag, imageId);
	        TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text, int imageId) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		ImageView img = (ImageView)view.findViewById(R.id.tabsImg);
		img.setImageResource(imageId);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.logoutMenu:
				new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?").setMessage("You will not be notified until you re-login").
	    		setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dlg, int sumthin) {
	    				Intent i = new Intent(RootActivity.this, LogoutLoading.class);
	    				i.putExtra("url", "http://amygdalahd.com/m/members/logout");
	    				startActivityForResult(i, 0);
	    			}
	    		}).
	    		setNegativeButton("No", new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dlg, int sumthin) {
	    			}
	    		}).show();
				
				break;
			case R.id.settingMenu:
				Intent newIntent = new Intent(this, SettingActivity.class);
				startActivity(newIntent);
				break;
			case R.id.tellFriendMenu:
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_SUBJECT, "AmygdalaHD");
				i.putExtra(Intent.EXTRA_TEXT, "Hey! I'm using AmygdalaHD for Android. It helps me a lot to make my trading decision; it's accurate! 'AmygdalaHD' is available on Android Market. Have a try!");
				startActivity(Intent.createChooser(i, "Tell Friend via"));
				break;
			case R.id.sendFeedbackMenu:
				Intent intent = new Intent(this, FeedbackActivity.class);
				startActivity(intent);
				break;
		}
		return true;
	 }
	 
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			finish();
		}
	}
}
