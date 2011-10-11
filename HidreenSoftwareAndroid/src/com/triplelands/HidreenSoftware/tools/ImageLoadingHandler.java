package com.triplelands.HidreenSoftware.tools;

import java.util.List;

import android.graphics.Bitmap;

public interface ImageLoadingHandler {
	void onReceivedImages(List<Bitmap> images);
	void onError();
}
