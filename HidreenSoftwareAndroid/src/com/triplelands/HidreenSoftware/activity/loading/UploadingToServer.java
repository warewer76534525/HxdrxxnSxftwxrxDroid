package com.triplelands.HidreenSoftware.activity.loading;

import java.io.InputStream;

import android.os.Bundle;

public class UploadingToServer extends InvokeHttpPostConnection {
	private String url;
	private String[] params;

	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
			params = bundle.getStringArray("parameter");
			super.onCreate(savedInstanceState, url, params);
		}
	}

	public void onReceivedResponse(InputStream is, int length) {

		super.onReceivedResponse(is, length);
	}
}
