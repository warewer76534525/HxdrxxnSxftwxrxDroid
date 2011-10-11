package com.triplelands.HidreenSoftware.tools;

import android.graphics.Bitmap;

public interface ImageDownloaderUtilityHandler {
	void onReceivedImage(Bitmap bmp);
	void onError();
}
