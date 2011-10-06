package com.triplelands.HidreenSoftware.tools;

import java.io.InputStream;

public interface InternetConnectionHandler {
	void onReceivedResponse(InputStream response, int length);
	void onErrorConnection(Exception ex);
	void onConnectionTimeout();
	void onConnectionCancelled();
	void onConnectionResponseNotOk();
}
