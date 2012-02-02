package com.triplelands.HidreenSoftware.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.triplelands.HidreenSoftware.utils.ImageUtils;

public class ImageLoading implements InternetConnectionHandler {
	
	private ImageDownloaderUtilityHandler handler;
	private int index;
	private File dir;
	private Activity activity;
	
	public ImageLoading(Activity activity, int index, ImageDownloaderUtilityHandler handler) {
		this.handler = handler;
		this.index = index;
		this.activity = activity;
		dir = new File(Environment.getExternalStorageDirectory() + "/.hidreensoftware/images/");
		dir.mkdirs();
	}
	
	public void start(final String url){
		final InternetConnection internetConnection = new InternetConnection(activity.getBaseContext(), this);
		Thread invokingThread = new Thread(new Runnable() {
			public void run() {
				internetConnection.setAndAccessURL(url);
			}
		});
		invokingThread.setPriority(Thread.MAX_PRIORITY);
		invokingThread.start();
	}

	public void onReceivedResponse(InputStream is, int length) {
		BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;        
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, bmOptions);
        
        saveImage(bitmap);
        
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        int newWidth = 0;
        int newHeight = 0;
        if (dm.widthPixels < dm.heightPixels) {
        	newWidth = dm.widthPixels - 20;
        	newHeight = (2 * newWidth) / 3;
		} else {
			newWidth = dm.heightPixels - 20;
        	newHeight = (2 * newWidth) / 3;
		}
        
        Bitmap resizedImg = ImageUtils.resizeImage(bitmap, newWidth, newHeight);
		handler.onReceivedImage(resizedImg);
	}
	
	
	private void saveImage(Bitmap bitmap){
		
		if(isFileExist(new File(dir, index + "." + "jpg"))){
			deleteFile(dir, index + "." + "jpg");
		}
		
        InputStream fileInputStream;
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		fileInputStream = new ByteArrayInputStream(bytes.toByteArray());
		
		File fileImage = new File(dir, index + "." + "jpg");
        FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(fileImage);
        
	        byte buffer[] = new byte[1024];
	        do {
	            int numread = fileInputStream.read(buffer);
	            if (numread <= 0)
	                break;
	            outputStream.write(buffer, 0, numread);
	        } while (true);
        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private boolean isFileExist(File file){
		return file.isFile();
	}
	
	private void deleteFile(File dir, String filename){
		try{
			File file = new File(dir, filename);
	    	file.delete();
		}catch (Exception e) {
			Log.e("ERROR", "error deleting file");
		}
	}

	public void onErrorConnection(Exception ex) {
		deleteFile(dir, index + "." + "jpg");
		handler.onError();
	}

	public void onConnectionTimeout() {
		deleteFile(dir, index + "." + "jpg");
		handler.onError();
	}

	public void onConnectionCancelled() {
		deleteFile(dir, index + "." + "jpg");
		handler.onError();
	}

	public void onConnectionResponseNotOk() {
		deleteFile(dir, index + "." + "jpg");
		handler.onError();
	}
}
