package com.triplelands.HidreenSoftware.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtils {

	public static Bitmap resizeImage(Bitmap bitmap , int newWidth, int newHeight) {
		try {
//		    BitmapFactory.Options options = new BitmapFactory.Options();
//		    options.inSampleSize = 1;
//		    Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		    if (bitmap != null) {
		        int width = bitmap.getWidth();
		        int height = bitmap.getHeight();
		        float scaleWidth = ((float) newWidth) / width;
		        float scaleHeight = ((float) newHeight) / height;
		        Matrix matrix = new Matrix();
		        matrix.postScale(scaleWidth, scaleHeight);
		        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		        return resizedBitmap;
		    } else {
		    	return null;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}
}
