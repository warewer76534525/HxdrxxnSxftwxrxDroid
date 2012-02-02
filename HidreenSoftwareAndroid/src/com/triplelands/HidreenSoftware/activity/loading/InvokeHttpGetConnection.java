package com.triplelands.HidreenSoftware.activity.loading;

import java.io.InputStream;

import roboguice.activity.RoboActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Looper;

import com.triplelands.HidreenSoftware.tools.InternetConnection;
import com.triplelands.HidreenSoftware.tools.InternetConnectionHandler;

public abstract class InvokeHttpGetConnection extends RoboActivity implements InternetConnectionHandler {
	
	private InternetConnection internetConnection;
//	private String url;
	private int idProgressDialog = 1;
	private Thread invokingThread;
	private ProgressDialog loadingDialog;
	
	public static final int CONNECTION_TYPE_DOWNLOAD_DATA = 0;
	public static final int CONNECTION_TYPE_DOWNLOAD_CONTENT = 1;
	
	private int connectionType = CONNECTION_TYPE_DOWNLOAD_DATA;
	private String loadingMessage = "Loading data...";
	
	protected void onCreate(Bundle savedInstanceState, String url) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		
		if(url != null){
			invokeUrl(url);
		}else{
			finish();
		}
	}
	
	protected void setConnectionType(int type){
		connectionType = type;
	}
	
	protected void setLoadingMessage(String message){
		loadingMessage = message;
	}

	public void onConnectionResponseNotOk() {
		dismissDialog(idProgressDialog);
		loadingDialog = null;

		Looper.prepare();
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error!").setMessage("Server response error. Please try again later.").
		setNeutralButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				try{
					finish();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).show();
		Looper.loop();
	}

	public void onReceivedResponse(InputStream is, int length) {
		dismissDialog(idProgressDialog);
		loadingDialog = null;
	}
	
	public void onConnectionTimeout() {
		dismissDialog(idProgressDialog);
		loadingDialog = null;
		
		Looper.prepare();
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error!").setMessage("Connection timeout.")
		.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				try{
					finish();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).show();
		Looper.loop();
	}
	
	private void invokeUrl(final String urlInvoke){
		if(loadingDialog == null){
			showDialog(idProgressDialog);
						
			internetConnection = new InternetConnection(this, this);
			invokingThread = new Thread(new Runnable() {
				public void run() {
					internetConnection.setAndAccessURL(urlInvoke);
				}
			});
			invokingThread.setPriority(Thread.MAX_PRIORITY);
			invokingThread.start();
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if(id == idProgressDialog){
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setTitle("Please Wait...");
			loadingDialog.setMessage(loadingMessage);
			loadingDialog.setIndeterminate(false);
			loadingDialog.setCancelable(false);
			loadingDialog.setOnCancelListener(cancelListener);
			
			if(connectionType == CONNECTION_TYPE_DOWNLOAD_CONTENT){
				loadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				loadingDialog.setMax(1);
			}else{
				loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			}
			
			return loadingDialog;
		}
		return super.onCreateDialog(id);
	}
	
	protected void setDownloadProgress(int progress, int total){
		
		if(loadingDialog.getMax() != total){
			loadingDialog.setMax(total);
			loadingDialog.incrementProgressBy(1024);
		}
		
		loadingDialog.setProgress(progress);
	}
	
	private OnCancelListener cancelListener = new OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			internetConnection.cancel(invokingThread);
//			invokingThread.interrupt();
		}
	};

	public void onErrorConnection(Exception ex) {
		dismissDialog(idProgressDialog);
		loadingDialog = null;
		
		Looper.prepare();
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error!").setMessage("Connection Error. Check your connection setting or try again.")
		.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				try{
					finish();
				}catch (Exception e) {
				}
			}
		}).show();
		Looper.loop();
	}

	public void onConnectionCancelled() {
        finish();
	}
	
}
