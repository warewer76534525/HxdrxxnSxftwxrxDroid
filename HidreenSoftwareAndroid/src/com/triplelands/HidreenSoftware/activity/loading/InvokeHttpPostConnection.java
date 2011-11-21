package com.triplelands.HidreenSoftware.activity.loading;

import java.io.InputStream;

import roboguice.activity.RoboActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;

import com.triplelands.HidreenSoftware.tools.InternetConnection;
import com.triplelands.HidreenSoftware.tools.InternetConnectionHandler;

public abstract class InvokeHttpPostConnection extends RoboActivity implements InternetConnectionHandler {
	private InternetConnection internetConnection;
	private int idProgressDialog = 1;
	private Thread invokingThread;
	private ProgressDialog loadingDialog;
	
	protected void onCreate(Bundle savedInstanceState, String url, String[] params) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		
		if(url != null){
			uploadParam(url, params);
			url = null;
		}else{
			finish();
		}
	}

	public void onConnectionResponseNotOk() {
		dismissDialog(idProgressDialog);
		loadingDialog = null;

		Looper.prepare();
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Something Wrong!").setMessage("Something's wrong with server response.").
		setNeutralButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				try{
					finish();
				}catch (Exception e) {
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
					
				}
			}
		}).show();
		Looper.loop();
	}
	
	private void uploadParam(final String urlInvoke, final String[] params){
		if(loadingDialog == null){
			showDialog(idProgressDialog);
						
			internetConnection = new InternetConnection(this, this);
			invokingThread = new Thread(new Runnable() {
				public void run() {
					internetConnection.postData(urlInvoke, params);
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
			loadingDialog.setTitle("Please wait...");
			loadingDialog.setMessage("Communicating with server.");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(false);
			return loadingDialog;
		}
		return super.onCreateDialog(id);
	}

	@Override
	public void onErrorConnection(Exception ex) {
		dismissDialog(idProgressDialog);
		loadingDialog = null;
		
		Looper.prepare();
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error!").setMessage("Connection error. Check connection setting or try again.")
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

	@Override
	public void onConnectionCancelled() {
		finish();
	}
}
