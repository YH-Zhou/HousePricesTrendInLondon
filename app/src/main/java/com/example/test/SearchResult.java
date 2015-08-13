package com.example.test;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import java.util.ArrayList;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.test.AndroidDB;

public class SearchResult extends Activity implements OnItemClickListener  {


	String areaMatched[] = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresult);


		Intent intent = getIntent();
		areaMatched = intent
				.getStringArrayExtra("com.example.graphtest.AREAMATCHED");

		ListView searchResult = (ListView) findViewById(R.id.result_view);

		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item,
				areaMatched);

		searchResult.setAdapter(adapter);
		searchResult.setOnItemClickListener(this);
		
		AlertDialog alertDialog = new AlertDialog.Builder(
				SearchResult.this).create();
		alertDialog.setTitle("Congratulations!");
		alertDialog.setIcon(R.drawable.ic_launcher_small);
		alertDialog
				.setMessage(areaMatched.length + " areas are matched in total!");
		alertDialog.setButton("Great!",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				});

		alertDialog.show();

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(SearchResult.this, XYChartBuilder.class);

		intent.putExtra("com.example.graphtest.AREANAME", areaMatched[arg2]);

		int Area_Value = 0;

		for (int area = 0; area < 32; area++) {

			if (areaMatched[arg2].equals(MainActivity.areaNameFromDB[area])) {
				System.out.println(MainActivity.areaNameFromDB[area]);
				Area_Value = area;
			}

		}
		System.out.println("Area " + Area_Value + ": "
				+ MainActivity.areaNameFromDB[Area_Value]);

		// Output the crime number each year for the clicked area
		for (int y = 0; y < 10; y++) {
			System.out.println(MainActivity.areaCrimeNum[Area_Value][y]);
		}

		intent.putExtra("com.example.graphtest.AREADATA_CRIME",
				MainActivity.areaCrimeNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_PRICES",
				MainActivity.areaPricesNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_POPULATION",
				MainActivity.areaPopulationNum[Area_Value]);
		intent.putExtra("com.example.graphtest.AREADATA_TREES",
				MainActivity.areaTreesNum[Area_Value]);
		startActivity(intent);
		finish();
	}
	
	public void menuButton(View view) {
			Intent intent = new Intent(this, StartActivity.class);
			startActivity(intent);
			finish();
	}
}