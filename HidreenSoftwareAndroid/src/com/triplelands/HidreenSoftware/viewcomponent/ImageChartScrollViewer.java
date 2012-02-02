package com.triplelands.HidreenSoftware.viewcomponent;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.triplelands.HidreenSoftware.activity.ImageViewerActivity;

public class ImageChartScrollViewer extends HorizontalScrollView {

	private Context context;
	
	public ImageChartScrollViewer(Context context, final List<Bitmap> images) {
		super(context);
		this.context = context;
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setBackgroundColor(Color.DKGRAY);
		
		for (int i = 0; i < images.size(); i++) {
			ImageView iv = new ImageView(context);
			iv.setPadding(10, 10, 10, 10);
			iv.setImageBitmap(images.get(i));
			iv.setOnClickListener(listener);
			iv.setTag(i);
			layout.addView(iv);
		}
		addView(layout);
				
//		setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if(event.getAction() == MotionEvent.ACTION_UP)
//	            {
//	                float currentPosition = getScrollX();
////	                float pagesCount = images.size();
//	                float pageLengthInPx = 320;
//	                float currentPage = currentPosition/pageLengthInPx;
//
//	                System.out.println("Current page: " + currentPage + ", " + (int)currentPage);
//	                
//	                Boolean isBehindHalfScreen =  currentPage - (int)currentPage > 0.5;
//
//	                float edgePosition = 0;
//	                if(isBehindHalfScreen)
//	                {
//	                    edgePosition = (int)(currentPage + 1)*pageLengthInPx;
//	                }
//	                else
//	                {
//	                    edgePosition = (int)currentPage * pageLengthInPx;
//	                }
//	                scrollTo((int)edgePosition, 0);
//	            }
//				return false;
//			}
//		});
	}

//	@Override
//	public void computeScroll() {
//		return;
//	}
	
	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(context, ImageViewerActivity.class);
			i.putExtra("path", Environment.getExternalStorageDirectory() + "/.hidreensoftware/images/" + v.getTag() + ".jpg");
			context.startActivity(i);
		}
	};
}
