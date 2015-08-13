package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

public class SearchActivity extends Activity {

	public static double crime_Min = 0, crime_Max = 90, population_Min = 0,
			population_Max = 0.4, prices_Min = 0, prices_Max = 1.5,
			trees_Min = 0, trees_Max = 40;
	public static String[] areaMatched;
	public static boolean areaMatched_Exist = false;

	public EditText etCrime_Min, etCrime_Max, etPopulation_Min,
			etPopulation_Max, etHousePrice_Min, etHousePrice_Max, etTrees_Min,
			etTrees_Max;
	public Button startSearchBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchview);

		etCrime_Min = (EditText) findViewById(R.id.crimeNum_Min);
		etCrime_Max = (EditText) findViewById(R.id.crimeNum_Max);
		etPopulation_Min = (EditText) findViewById(R.id.population_Min);
		etPopulation_Max = (EditText) findViewById(R.id.population_Max);
		etHousePrice_Min = (EditText) findViewById(R.id.housePrice_Min);
		etHousePrice_Max = (EditText) findViewById(R.id.housePrice_Max);
		etTrees_Min = (EditText) findViewById(R.id.tree_Min);
		etTrees_Max = (EditText) findViewById(R.id.tree_Max);

		startSearchBtn = (Button) findViewById(R.id.searchBtn);
		startSearchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!MainActivity.db_Handle_Exist) {
					System.out
							.println("Check result when Search button is clicked: DB is closed.");
					MainActivity.ourDB = SQLiteDatabase.openDatabase(
							MainActivity.dbPath, null,
							SQLiteDatabase.OPEN_READONLY);
					// Create a DBHandle object
					MainActivity.DB = new DBHandle(MainActivity.ourDB);
					System.out.println("Now DB is open.");
				}
				try {
					crime_Min = Double.valueOf(etCrime_Min.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input crime_Min not succeed, using default values.");
				}
				try {
					crime_Max = Double.valueOf(etCrime_Max.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input crime_Max not succeed, using default values.");
				}
				try {
					population_Min = Double.valueOf(etPopulation_Min.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input population_Min not succeed, using default values.");
				}
				try {
					population_Max = Double.valueOf(etPopulation_Max.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input population_Max not succeed, using default values.");
				}
				try {
					prices_Min = Double.valueOf(etHousePrice_Min.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input prices_Min not succeed, using default values.");
				}
				try {
					prices_Max = Double.valueOf(etHousePrice_Max.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input prices_Max not succeed, using default values.");
				}
				try {
					trees_Min = Double.valueOf(etTrees_Min.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input trees_Min not succeed, using default values.");
				}
				try {
					trees_Max = Double.valueOf(etTrees_Max.getText()
							.toString());
				} catch (Exception e) {
					Log.i("Info",
							"Getting user input trees_Max not succeed, using default values.");
				}

				if (crime_Min <= crime_Max && crime_Min >= 0 && crime_Max <= 90
						&& population_Min <= population_Max
						&& population_Min >= 0 && population_Max <= 0.4
				 && prices_Min <= prices_Max && prices_Min >= 0
				 && prices_Max <= 1.5 && trees_Min < trees_Max
				 && trees_Min >= 0 && trees_Max <= 40) {
					System.out.println("User input for house prices, "
							+ "crime numbers, population and trees: ");
					System.out.println(prices_Min + " and " + prices_Max);
					System.out.println(crime_Min + " and " + crime_Max);
					System.out.println(population_Min + " and "
							+ population_Max);
					System.out.println(trees_Min + " and " + trees_Max);

					// Search areas
					String[] areaMatchedArr_temp = MainActivity.DB.searchArea(
							crime_Min * 1E3, crime_Max * 1E3, population_Min * 1E6,
							population_Max * 1E6, prices_Min * 1E6, prices_Max * 1E6, trees_Min * 1E3,
							trees_Max * 1E3);

					// First check if areaMatched is empty, if so, skip printing
					if (areaMatched_Exist) {

						areaMatched = new String[areaMatchedArr_temp.length];
						areaMatched = areaMatchedArr_temp;

						// Print out matched areas
						for (int i = 0; i < areaMatched.length; i++) {
							System.out.println("Area " + areaMatched[i]
									+ " matches.");
						}

						Intent searchIntent = new Intent(SearchActivity.this,
								SearchResult.class);
						searchIntent.putExtra(
								"com.example.graphtest.AREAMATCHED",
								areaMatched);
						startActivity(searchIntent);
						finish();
					} else {
						System.out.println("No area matches!");

						AlertDialog alertDialog = new AlertDialog.Builder(
								SearchActivity.this).create();
						alertDialog.setTitle("Search Result");
						alertDialog.setIcon(R.drawable.ic_launcher_small);
						alertDialog
								.setMessage("No area matches! Please try again.");
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});

						alertDialog.show();

					}
				} else {
					System.out
							.println("Contain invalid input for house prices, "
									+ "crime numbers, population and trees: ");
					System.out.println(prices_Min + " and " + prices_Max);
					System.out.println(crime_Min + " and " + crime_Max);
					System.out.println(population_Min + " and "
							+ population_Max);
					System.out.println(trees_Min + " and " + trees_Max);

					AlertDialog alertDialog = new AlertDialog.Builder(
							SearchActivity.this).create();
					alertDialog.setTitle("Warning");
					alertDialog.setIcon(R.drawable.ic_launcher_small);
					alertDialog
							.setMessage("Input range invalid! Please try again.");
					/*alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dial/Users/zyh/Documents/Android App/London Boroughs (V1.0 Beta1)/MainActivity/src/com/example/test/SearchResult.javaog,
										int which) {
								}
							});
					*/
					alertDialog.show();
				}

				// Reset user inputs to default values
				crime_Min = 0;
				crime_Max = 90;
				population_Min = 0;
				population_Max = 0.4;
				prices_Min = 0;
				prices_Max = 1.5;
				trees_Min = 0;
				trees_Max = 40;

				// Reset to MainActivity.areaMatched_Exist false
				areaMatched_Exist = false;
			}
		});

		MainActivity.DB.close();
		System.out.println("After Search function, DB is closed again.");
	}

}
