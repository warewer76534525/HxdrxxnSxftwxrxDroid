package com.triplelands.HidreenSoftware.tools;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;

public class ImageDownloaderUtility implements ImageDownloaderUtilityHandler {
	
	private String urls[];
	private ImageLoadingHandler handler;
	private List<Bitmap> downloadedImages;
	private int downloaded;
	private Activity act;
	
	public ImageDownloaderUtility(Activity act, String urls[], ImageLoadingHandler handler) {
		this.urls = urls;
		this.handler = handler;
		this.act = act;
		downloadedImages = new ArrayList<Bitmap>();
		downloaded = 0;	
	}
	
	public void start(){
		startDownload(urls[downloaded]);
	}

	private void startDownload(final String url) { 
		ImageLoading loader = new ImageLoading(act, downloaded, this);
		loader.start(url);
	}

	public void onReceivedImage(Bitmap image) {
		downloaded += 1;
		downloadedImages.add(image);
		
		//finish download all images
		if(downloaded == urls.length)
			handler.onReceivedImages(downloadedImages);
		else
			startDownload(urls[downloaded]);
	}

	public void onError() {
		handler.onError();
	}
}
