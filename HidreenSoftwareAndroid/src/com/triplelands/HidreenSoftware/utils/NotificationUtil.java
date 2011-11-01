package com.triplelands.HidreenSoftware.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.triplelands.HidreenSoftware.R;

public class NotificationUtil {
	private static NotificationUtil _instance;
	private NotificationManager _mNotificationManager;
	private Notification _notifyDetails;
	private int NOTIFICATION_ID;
	private int _unreadNotificationNum;
	
	private NotificationUtil() {
	}
	
	public static NotificationUtil getInstance() {
        if (_instance == null) {
        	_instance = new NotificationUtil();
        }
        return _instance;
    }
	
	public void show(Context context, String alert, String title, String content, Class<?> destClass){
		initNotification(context, alert);
		showNotification(context, title, content, destClass);
	}
	
	public void clear(){
		_unreadNotificationNum = 0;
		if(_mNotificationManager != null) _mNotificationManager.cancelAll();
	}
	
	private void initNotification(Context context, String alert) {
		_mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		_notifyDetails = new Notification(R.drawable.icon, alert, System.currentTimeMillis());

		long[] vibrate = { 100, 100, 200, 300 };
		_notifyDetails.vibrate = vibrate;
		_notifyDetails.defaults = Notification.DEFAULT_ALL;
	}
	
	private void showNotification(Context context, String title, String text, Class<?> destClass) {
		Intent notifyIntent = new Intent(context, destClass);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notifyIntent, android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
		_notifyDetails.setLatestEventInfo(context, title, text, intent);
		_mNotificationManager.notify(NOTIFICATION_ID, _notifyDetails);
		_unreadNotificationNum++;
	}
}
