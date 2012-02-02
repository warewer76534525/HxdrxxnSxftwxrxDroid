package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.StringList;
import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingCandleChartData;
import com.triplelands.HidreenSoftware.model.OLHCData;
import com.triplelands.HidreenSoftware.utils.ChartDataManager;

public class CandleChartViewerActivity extends RoboActivity {

	@InjectView(R.id.layoutChart) LinearLayout layoutChart;
	
	private String chartUrls[];
	private int index;
	private HashMap<String, List<OLHCData>> mapData;
	
	private final int MODE_ZOOM = 1;
    private final int MODE_DRAG = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_viewer);
		mapData = new HashMap<String, List<OLHCData>>();
		index = 0;
		
		chartUrls = getIntent().getExtras().getStringArray("chartsUrl");
		
		loadData();
	}
	
	private void loadData(){
		if (mapData.get(chartUrls[index]) == null) {
			Intent i = new Intent(this, LoadingCandleChartData.class);
			i.putExtra("url", chartUrls[index]);
			startActivityForResult(i, 0);
		} else {
			List<OLHCData> l = mapData.get(chartUrls[index]);
			populateChart(l);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			final List<OLHCData> list = ChartDataManager.GetInstance().getCurrentCandleChart(chartUrls[index]);
			System.out.println("Data Chart size: " + list.size());
			if (list.size() > 0) {
				mapData.put(chartUrls[index], copyData(list));
				populateChart(list);
				if (chartUrls.length > 1 && index == 0)
					Toast.makeText(this, "Press menu to see next chart", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "No chart data.", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
	private List<OLHCData> copyData(List<OLHCData> listData){
		List<OLHCData> list = new ArrayList<OLHCData>();
		for (int i = 0; i < listData.size(); i++) {
			list.add(listData.get(i));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void populateChart(final List<OLHCData> listData){
		layoutChart.removeAllViews();
		
		
		TChart chart = new TChart(this){
			int mode = 0;
        	float oldDist, tempX, tempY;

            private float spacing(MotionEvent event) {
        		float x = event.getX(0) - event.getX(1);
        		float y = event.getY(0) - event.getY(1);
        		return FloatMath.sqrt(x * x + y * y);
        	}
            
        	@Override
        	public boolean onTouch(android.view.View arg0, MotionEvent ev) {
        		switch (ev.getAction()) {
        		case MotionEvent.ACTION_DOWN:
        			System.out.println("ACTION_DOWN");
        			mode = MODE_DRAG;
        			break;
        		case MotionEvent.ACTION_POINTER_1_DOWN:
        			System.out.println("ACTION_POINTER_1_DOWN");
        		case MotionEvent.ACTION_POINTER_2_DOWN:
        			System.out.println("ACTION_POINTER_2_DOWN");
        			oldDist = spacing(ev);
        			mode = MODE_ZOOM;
        			break;
        		case MotionEvent.ACTION_MOVE:
        			
        			if (mode == MODE_ZOOM) {
        				float newDist = spacing(ev);
        				if(newDist > 10f){
        					if(newDist > oldDist){
                				getZoom().zoomPercent(95);
        					} else {
    							getZoom().zoomPercent(105);
        					}
         				}
					}
        			if (mode == MODE_DRAG){
        				if (tempX == 0 && tempY == 0) {
        					tempX = ev.getX();
                    		tempY = ev.getY();
						}
        				
        				if (ev.getX() > tempX) {
        					if((ev.getX() - tempX) > 3) this.getAxes().getBottom().scroll(0 - (this.getAxes().getBottom().getRange()/20), true);
						} else if ((ev.getX() < tempX)) {
							if((tempX - ev.getX()) > 3) this.getAxes().getBottom().scroll(this.getAxes().getBottom().getRange()/20, true);
						}
        				
        				if (ev.getY() > tempY) {
        					this.getAxes().getLeft().scroll(this.getAxes().getLeft().getRange()/20, true);
						} else if ((ev.getY() < tempY)) {
							this.getAxes().getLeft().scroll(0 - (this.getAxes().getLeft().getRange()/20), true);
						}
        				tempX = ev.getX();
                		tempY = ev.getY();
        			}
        			break;
        		case MotionEvent.ACTION_POINTER_UP:
        			mode = MODE_DRAG;
        			tempX = 0;
        			tempY = 0;
        			break;
        		case MotionEvent.ACTION_UP:
        			tempX = 0;
        			tempY = 0;
        			mode = 0;
        			break;
        		}
        		invalidate();
        		return true;
        	}
		};
		layoutChart.addView(chart);
        
        chart.removeAllSeries();

		try {
			Candle series = (Candle)Series.createNewSeries(chart.getChart(), Candle.class, null);
			
//			Line POline = (Line)Series.createNewSeries(chart.getChart(), Line.class, null);
//			Line SLline = (Line)Series.createNewSeries(chart.getChart(), Line.class, null);
//			Line TPline = (Line)Series.createNewSeries(chart.getChart(), Line.class, null);
			
			StringList times = new StringList(listData.size());
			for (int i = 0; i < listData.size(); i++) {
//				POline.add(1.345, Color.WHITE);
//				SLline.add(1.350, Color.WHITE);
//				TPline.add(1.340, Color.WHITE);
				
				OLHCData olhc = listData.get(i);
				series.add(olhc.getOpen(), olhc.getLow(), olhc.getHigh(), olhc.getClose());
				times.add((olhc.getTime().substring(0, olhc.getTime().length() - 3)));
			}
			
//			for (int i = 0; i < 200; i++) {
//				POline.add(1.345, Color.RED);
//				SLline.add(1.350, Color.RED);
//				TPline.add(1.340, Color.RED);
//			}
			
			series.setLabels(times);
			
			chart.getPanel().setTransparent(false);
			chart.getAspect().setView3D(false);
			chart.addSeries(series);
//			chart.addSeries(POline);
//			chart.addSeries(SLline);
//			chart.addSeries(TPline);
			chart.getLegend().setVisible(false);
			chart.getLegend().setAlignment(LegendAlignment.BOTTOM);
			chart.getHeader().setText("Chart " + (index + 1) + " of " + chartUrls.length);
			chart.getHeader().getFont().setColor(Color.black);
		} catch (InstantiationException e) {
			Log.e("TeeChartDemo-Instantiation", e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("TeeChartDemo-IllegalAccess", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		if (chartUrls.length > 1) {
			MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.chart_menu, menu);
		}
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.prevMenu:
			prev();
			break;
		case R.id.nextMenu:
			next();
			break;
	}
	return true;
	}
	
	private void next(){
		if (index < chartUrls.length - 1) {
			index ++;
			loadData();
		} else {
			Toast.makeText(this, "No more chart", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void prev(){
		if (index > 0) {
			index --;
			loadData();
		} else {
			Toast.makeText(this, "Already diplaying first chart", Toast.LENGTH_SHORT).show();
		}
	}
}
