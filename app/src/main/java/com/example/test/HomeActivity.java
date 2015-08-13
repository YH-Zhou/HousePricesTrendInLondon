package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
	}

	public void displaygraph(View view) {

		Intent intent = new Intent(this, XYChartBuilder.class);
		Button Area = (Button) findViewById(view.getId());
		String AreaNameFromMap = Area.getText().toString();

		intent.putExtra("com.example.graphtest.AREANAME", AreaNameFromMap);
		
		int Area_Value = 0;
		for (int area = 0; area < 32; area++) {

			if (AreaNameFromMap.equals(MainActivity.areaNameFromDB[area])) {
				Area_Value = area;
			}

		}
		System.out.println("Area " + Area_Value + ": "
				+ MainActivity.areaNameFromDB[Area_Value]);

		intent.putExtra("com.example.graphtest.AREADATA_CRIME",
				MainActivity.areaCrimeNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_PRICES",
				MainActivity.areaPricesNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_POPULATION",
				MainActivity.areaPopulationNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_TREES",
				MainActivity.areaTreesNum[Area_Value]);
		startActivity(intent);

	}
}