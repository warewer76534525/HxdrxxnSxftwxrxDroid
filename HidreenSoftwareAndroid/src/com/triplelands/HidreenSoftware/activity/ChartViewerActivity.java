package com.triplelands.HidreenSoftware.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.triplelands.HidreenSoftware.R;
import com.triplelands.HidreenSoftware.activity.loading.LoadingChartData;
import com.triplelands.HidreenSoftware.utils.ChartDataManager;

public class ChartViewerActivity extends RoboActivity {
	
	@InjectView(R.id.layoutChart) LinearLayout layoutChart;
	
	private String chartUrls[];
	private int index;
	private HashMap<String, List<BasicNameValuePair>> mapData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_viewer);
		mapData = new HashMap<String, List<BasicNameValuePair>>();
		index = 0;
		chartUrls = getIntent().getExtras().getStringArray("chartsUrl");
		
		loadData();
	}
	
	private void loadData(){
		if (mapData.get(chartUrls[index]) == null) {
			Intent i = new Intent(this, LoadingChartData.class);
			i.putExtra("url", chartUrls[index]);
			startActivityForResult(i, 0);
		} else {
			List<BasicNameValuePair> l = mapData.get(chartUrls[index]);
			populateChart(l);
		}
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			final List<BasicNameValuePair> list = ChartDataManager.GetInstance().getCurrentChart(chartUrls[index]);
			if (list.size() > 0) {
				mapData.put(chartUrls[index], copyData(list));
				populateChart(list);
				if (chartUrls.length > 1)
					Toast.makeText(this, "Press menu to see next chart", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "No chart data.", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
	private void populateChart(final List<BasicNameValuePair> list){
		layoutChart.removeAllViews();
		GraphViewData chartData[] = new GraphViewData[list.size()];
		for (int i = 0; i < list.size(); i++) {
			chartData[i] = new GraphViewData(i, Double.parseDouble(list.get(i).getValue()));
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(chartData);

		GraphView graphView = new LineGraphView(this, "Chart " + (index + 1) + " of " + chartUrls.length){
			@Override
			protected String formatLabel(double value, boolean isValueX) {
				if (isValueX) {
					DisplayMetrics dm = new DisplayMetrics();
			        ChartViewerActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
			        
					String valueX = (list.get((int) value).getName());
					if (dm.heightPixels > 800) {
						return (valueX.substring(0, valueX.length() - 3));
					} else {
						return (valueX.substring(5, valueX.length() - 3)).replaceAll("-", "/");
					}
				} else {
					return super.formatLabel(value, isValueX);
				}
			}
		};
		graphView.addSeries(exampleSeries); // data
		graphView.setViewPort(0, list.size() - 1);
		graphView.setScrollable(true);
		graphView.setScalable(true);
		
		layoutChart.addView(graphView);
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
	
	private List<BasicNameValuePair> copyData(List<BasicNameValuePair> listData){
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < listData.size(); i++) {
			list.add(listData.get(i));
		}
		return list;
	}
}
