package com.example.test;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;

import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.Bundle;
import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PricesChartBuilder extends Activity {

	private GraphicalView mChartView;
	private Button BackBtn;
	// private String AreaName;
	// private int[] AreaData_Crime, AreaData_Prices, AreaData_Population;
	// private int AreaData_Trees;
	private ArrayAdapter<String> areaAdapter;
	private Spinner areaSelect;
	private boolean initialSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prices_chart_builder);
		// set up the back home button
		BackBtn = (Button) findViewById(R.id.home_button);
		BackBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PricesChartBuilder.this,
						StartActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		setSpinner();

		mChartView = this.getGraphicalView(this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.prices_chart);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_prices_chart_builder, menu);
		return true;
	}

	public GraphicalView getGraphicalView(Context context) {
		// Data series
		// String[] x = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
		// "11", "12", "13", "14", "15", "16", "17",
		// "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
		// "29", "30", "31", "32" };
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
				18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32 };

		int[] y = MainActivity.pricesComparison;
		// CategorySeries series = new CategorySeries("House Price");
		TimeSeries series = new TimeSeries("House Price");
		TimeSeries seriesAvgLondon = new TimeSeries("Average Value of London");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
			seriesAvgLondon.add(x[i], MainActivity.avgPricesNumLondon);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(seriesAvgLondon);
		// Custom for lines
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.YELLOW);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setLineWidth(2);

		XYSeriesRenderer rendererAvgLondon = new XYSeriesRenderer();
		rendererAvgLondon.setColor(Color.CYAN);
		rendererAvgLondon.setFillPoints(true);
		rendererAvgLondon.setLineWidth(2);

		XYMultipleSeriesRenderer sRenderer = new XYMultipleSeriesRenderer();
		sRenderer.addSeriesRenderer(renderer);
		sRenderer.addSeriesRenderer(rendererAvgLondon);
		sRenderer.setChartTitle("Price Comparison Among All Areas (Year 2010)");
		sRenderer.setShowGrid(true);
		sRenderer.setGridColor(Color.DKGRAY);
		sRenderer.setZoomEnabled(true);
		sRenderer.setXTitle("                                                                " +
				"                                                                Area Number");
		sRenderer.setYTitle("                                                                " + 
				"                                                                            " + 
				"                                House Price /GBP");
		sRenderer.setBackgroundColor(color.holo_green_light);

		GraphicalView graphicalView = ChartFactory.getLineChartView(context,
				dataset, sRenderer);

		return graphicalView;
	}

	public void displayComparison(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.pricesComp:
			System.out.println("Price is clicked.");
			intent = new Intent(this, PricesChartBuilder.class);
			startActivity(intent);
			break;
		case R.id.crimeComp:
			System.out.println("Crime is clicked.");
			intent = new Intent(this, CrimeChartBuilder.class);
			startActivity(intent);
			break;
		case R.id.populationComp:
			System.out.println("Population is clicked.");
			intent = new Intent(this, PopulationChartBuilder.class);
			startActivity(intent);
			break;
		case R.id.treesComp:
			System.out.println("Tree is clicked.");
			intent = new Intent(this, TreeChartBuilder.class);
			startActivity(intent);
			break;
		}
	}

	public void setSpinner() {
		areaSelect = (Spinner) findViewById(R.id.areaSelect);
		areaAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				MainActivity.areaNameForSpinner);
		// System.out.println("Area 0 in FromDB: "
		// + MainActivity.areaNameFromDB[0]);
		areaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areaSelect.setAdapter(areaAdapter);

		areaSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextSize(13);
				if (initialSelected) {
					// Do nothing
					initialSelected = false;
				} else {
					System.out.println("Area " + arg2 + ": "
							+ MainActivity.areaNameFromDB[arg2]
							+ " is selected.");
					// Make chart for selected area
					displayChart(arg2);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
		areaSelect.setVisibility(View.VISIBLE);
	}

	public void displayChart(int area) {

		Intent intent = new Intent(this, XYChartBuilder.class);

		intent.putExtra("com.example.graphtest.AREANAME",
				MainActivity.areaNameFromDB[area]);
		intent.putExtra("com.example.graphtest.AREADATA_CRIME",
				MainActivity.areaCrimeNum[area]);
		intent.putExtra("com.example.graphtest.AREADATA_PRICES",
				MainActivity.areaPricesNum[area]);
		intent.putExtra("com.example.graphtest.AREADATA_POPULATION",
				MainActivity.areaPopulationNum[area]);
		intent.putExtra("com.example.graphtest.AREADATA_TREES",
				MainActivity.areaTreesNum[area]);

		startActivity(intent);
	}
}
