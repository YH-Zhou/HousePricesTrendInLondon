package com.example.test;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;

import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

//import com.example.test.MapUtility;
//import com.example.test.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

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

public class XYChartBuilder extends MapActivity {

	private GraphicalView mChartView;
	private Button BackBtn;
	private String AreaName;
	private int[] AreaData_Crime, AreaData_Prices, AreaData_Population,
			AreaData_Trees;

	private MapController mapCtl;
	private MapView mapView;
	private Activity self; // self pointer
	private MyOverlay myOverlay; // display pin
	private MyLocationOverlay myLocationOverlay; // to display my location
	private GeoPoint gp;

	private ArrayAdapter<String> areaAdapter;
	private Spinner areaSelect;
	private boolean initialSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// get DATA
		Intent intent = getIntent();
		AreaName = intent.getStringExtra("com.example.graphtest.AREANAME");
		AreaData_Crime = intent
				.getIntArrayExtra("com.example.graphtest.AREADATA_CRIME");
		AreaData_Prices = intent
				.getIntArrayExtra("com.example.graphtest.AREADATA_PRICES");
		AreaData_Population = intent
				.getIntArrayExtra("com.example.graphtest.AREADATA_POPULATION");
		AreaData_Trees = intent
				.getIntArrayExtra("com.example.graphtest.AREADATA_TREES");

		setContentView(R.layout.activity_xychart_builder);

		// set up the back home button
		BackBtn = (Button) findViewById(R.id.home_button);
		BackBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(XYChartBuilder.this, StartActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});

		setSpinner();

		// SetBackgroundColor(color.black);
		mChartView = this.getGraphicalView(this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		initMap();
		moveToArea(AreaName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_xychart_builder, menu);
		return true;
	}

	public GraphicalView getGraphicalView(Context context) {

		// Toast.makeText(context, AreaName, Toast.LENGTH_SHORT).show();

		// Data series
		int[] x = { 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
				2011 };
		int[] y_Crime = AreaData_Crime, y_Prices = AreaData_Prices, y_Population = AreaData_Population, y_Trees = AreaData_Trees;

		TimeSeries series_Prices = new TimeSeries("House Price"), series_Crimes = new TimeSeries(
				"Crime x 10"), series_Population = new TimeSeries("Population"), series_Trees = new TimeSeries(
				"Tree Num x 10");
		for (int i = 0; i < 10; i++) {
			series_Prices.add(x[i], y_Prices[i]);
			series_Crimes.add(x[i], y_Crime[i] * 10);
			series_Population.add(x[i], y_Population[i]);
		}
		series_Trees.add(x[6], y_Trees[0] * 10);
		series_Trees.add(x[10], y_Trees[1] * 10);

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series_Prices);
		dataset.addSeries(series_Crimes);
		dataset.addSeries(series_Population);
		dataset.addSeries(series_Trees);

		// Custom for lines
		XYSeriesRenderer renderer_Prices = new XYSeriesRenderer();
		renderer_Prices.setColor(Color.YELLOW);
		renderer_Prices.setPointStyle(PointStyle.CIRCLE);
		renderer_Prices.setFillPoints(true);
		renderer_Prices.setLineWidth(2);

		XYSeriesRenderer renderer_Crimes = new XYSeriesRenderer();
		renderer_Crimes.setColor(Color.RED);
		renderer_Crimes.setPointStyle(PointStyle.TRIANGLE);
		renderer_Crimes.setFillPoints(true);
		renderer_Crimes.setLineWidth(2);

		XYSeriesRenderer renderer_Population = new XYSeriesRenderer();
		renderer_Population.setColor(Color.BLUE);
		renderer_Population.setPointStyle(PointStyle.DIAMOND);
		renderer_Population.setFillPoints(true);
		renderer_Population.setLineWidth(2);

		XYSeriesRenderer renderer_Trees = new XYSeriesRenderer();
		renderer_Trees.setColor(Color.GREEN);
		renderer_Trees.setPointStyle(PointStyle.SQUARE);
		renderer_Trees.setFillPoints(true);
		renderer_Trees.setLineWidth(2);

		XYMultipleSeriesRenderer sRenderer = new XYMultipleSeriesRenderer();
		sRenderer.addSeriesRenderer(renderer_Prices);
		sRenderer.addSeriesRenderer(renderer_Crimes);
		sRenderer.addSeriesRenderer(renderer_Population);
		sRenderer.addSeriesRenderer(renderer_Trees);
		sRenderer.setChartTitle(AreaName);
		sRenderer.setShowGrid(true);
		sRenderer.setGridColor(Color.DKGRAY);
		sRenderer.setZoomEnabled(true);
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
			finish();
			break;
		case R.id.crimeComp:
			System.out.println("Crime is clicked.");
			intent = new Intent(this, CrimeChartBuilder.class);
			startActivity(intent);
			finish();
			break;
		case R.id.populationComp:
			System.out.println("Population is clicked.");
			intent = new Intent(this, PopulationChartBuilder.class);
			startActivity(intent);
			finish();
			break;
		case R.id.treesComp:
			System.out.println("Tree is clicked.");
			intent = new Intent(this, TreeChartBuilder.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	public void setSpinner() {
		areaSelect = (Spinner) findViewById(R.id.areaSelect);
		areaAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				MainActivity.areaNameForSpinner);
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
		finish();

	}

	private void initMap() {
		// basic setup for MapView
		mapView = (MapView) findViewById(R.id.mapview);
		mapCtl = mapView.getController();
		mapView.setClickable(true);
		mapView.setEnabled(true);
		mapView.setBuiltInZoomControls(true); // zoomable

		// mapView.getController().setCenter(new GeoPoint(39971036,116314659));
		// mapView.getController().setZoom(10);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		// myLocationOverlay.enableCompass();
		// myLocationOverlay.enableMyLocation();

		// add my location overlay to map view
		mapView.getOverlays().add(myLocationOverlay);
	}

	private void moveToArea(String areaName) {
		// move map center to areaName

		areaName = areaName.replace("&", "and");
		areaName = "Royal+Borough+of+" + areaName;
		// get GeoPoint for the areaName
		gp = MapUtility.getGeoPoint((MapUtility.getLocationInfo(areaName)));

		// add pin
		if (myOverlay != null)
			mapView.getOverlays().remove(myOverlay);
		myOverlay = new MyOverlay();
		// show the area
		mapView.getOverlays().add(myOverlay);
		mapCtl.setZoom(13);
		mapCtl.animateTo(gp);
	}

	class MyOverlay extends Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			// paint overlay on map
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			Point screenPoint = new Point();
			mapView.getProjection().toPixels(gp, screenPoint);
			Bitmap marker = BitmapFactory.decodeResource(getResources(),
					R.drawable.marker);
			canvas.drawBitmap(marker, screenPoint.x - 40, screenPoint.y - 40,
					paint);

			return super.draw(canvas, mapView, shadow, when);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
