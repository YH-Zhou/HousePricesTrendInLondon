package com.example.test;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.test.AndroidDB;

//@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	// TabActivity implements
	// OnCheckedChangeListener {
	/** Called when the activity is first created. */

	public static AndroidDB dba;
	public static int avgCrimeNumLondon;
	public static int avgPopulationNumLondon;
	public static int avgPricesNumLondon;
	public static int avgTreesNumLondon;
	public static int[] crimeComparison = new int[32];
	public static int[] populationComparison = new int[32];
	public static int[] pricesComparison = new int[32];
	public static int[] treesComparison = new int[32];

	public static int[][] areaCrimeNum = new int[32][10];
	public static int[][] areaPopulationNum = new int[32][10];
	public static int[][] areaPricesNum = new int[32][10];
	public static int[][] areaTreesNum = new int[32][2];
	// public static int[] intYear = new int[] { 2001, 2002, 2003, 2004, 2005,
	// 2006, 2007, 2008, 2009, 2010 };
	public static String[] areaNameFromDB = new String[32];
	public static String[] areaNameForSpinner = new String[32];
	public EditText editTextCrime_Min, editTextCrime_Max;

	public static SQLiteDatabase ourDB = null;
	public static DBHandle DB;
	public static boolean db_Handle_Exist;
	public static boolean plottingDataReady = false;
	// Path to our dababase
	public static String dbPath = "/data/data/com.example.test/databases/Android_DB";

	public static boolean backendRunning = true;
	public static boolean checking = true;
	public static boolean exitCliked = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LoadingView main_imageview = (LoadingView) findViewById(R.id.main_imageview);
		main_imageview.startAnim();

		// Once the MainActivity is run, check if plotting data is ready, if
		// not, check CSV files, ColumnName and Token stuff, and database
		// otherwise skip the following block and continue the app

		System.out
				.println("Once the MainActivity is run, check if plotting data is ready.");

		if (!plottingDataReady) {

			// Plotting data is not ready.
			System.out.println("Plotting data is not ready.");

			// To obtain it, we need to make sure that
			// ColumnName and Token stuff exist AND database exists.
			System.out.println("To obtain it, we need to make sure that "
					+ "database exists AND ColumnName and Token stuff exist.");

			// Firstly, since plotting data is not ready, we can judge that
			// ColumnName and Token stuff does not exist.
			System.out
					.println("Firstly, since plotting data is not ready, we can "
							+ "judge that ColumnName and Token stuff does not exist. ");

			// To execute ColumnName and Token stuff, check if CSV files exist
			System.out.println("To execute ColumnName and Token stuff, "
					+ "check if CSV files exist.");
			String path = Environment.getExternalStorageDirectory()
					+ "/ourApp/";
			File file_Crime = new File(path, "Crime.csv");
			File file_Population = new File(path, "Population.csv");
			File file_Prices = new File(path, "Prices.csv");
			File file_Trees = new File(path, "Trees.csv");

			// Crime.csv
			if (!file_Crime.exists()) {
				new Download(MainActivity.this).execute();
				System.out
						.println("The file Crime.csv does not exist, downloading...");
			} else {
				System.out.println("The file Crime.csv exists.");
				Download.downloadCrimeCompleted = true;
			}

			// Population.csv
			if (!file_Population.exists()) {
				new DownloadPopulation(MainActivity.this).execute();
				System.out
						.println("The file Population.csv does not exist, downloading...");
			} else {
				System.out.println("The file Population.csv exists.");
				DownloadPopulation.downloadPopulationCompleted = true;
			}

			// Crime.csv
			if (!file_Prices.exists()) {
				new DownloadPrices(MainActivity.this).execute();
				System.out
						.println("The file Prices.csv does not exist, downloading...");
			} else {
				System.out.println("The file Prices.csv exists.");
				DownloadPrices.downloadPricesCompleted = true;
			}

			// Crime.csv
			if (!file_Trees.exists()) {
				new DownloadTrees(MainActivity.this).execute();
				System.out
						.println("The file Trees.csv does not exist, downloading...");
			} else {
				System.out.println("The file Trees.csv exists.");
				DownloadTrees.downloadTreesCompleted = true;
			}

			// Wait until all files are downloaded
			while (Download.downloadCrimeCompleted == false
					|| DownloadPrices.downloadPricesCompleted == false
					|| DownloadPopulation.downloadPopulationCompleted == false
					|| DownloadTrees.downloadTreesCompleted == false) {
			}
			new Thread() {
				public void run() {
					// Executing ColumnName and Token stuff...
					System.out
							.println("Executing ColumnName and Token stuff...");

					ColumnName.getColumnName();
					TokenNew.getToken();

					ColumnNamePopulation.getColumnNamePopulation();
					TokenPopulation.getTokenPopulation();

					ColumnNamePrices.getColumnNamePrices();
					TokenPrices.getTokenPrices();

					ColumnNameTrees.getColumnNameTrees();
					TokenTrees.getTokenTrees();

					// Secondly, check if database exists
					System.out.println("Secondly, check if database exists.");

					// A boolean variable that indicates whether the database
					// exists
					boolean ourDB_Exist = false;

					try {
						ourDB = SQLiteDatabase.openDatabase(dbPath, null,
								SQLiteDatabase.OPEN_READONLY);
						System.out.println("Database exists.");
						ourDB_Exist = true;
						// ourDB.close();
					} catch (SQLiteException e) {
						// database doesn't exist yet.
						System.out.println("Database does not exist.");
					}

					// If the database does not exist, create it and insert
					// tables.
					if (!ourDB_Exist) {

						dba = new AndroidDB(MainActivity.this);
						// Create a dababase
						dba.open();
						// Insert csv data into database tables
						dba.insert_Crimes();
						dba.insert_Trees();
						dba.insert_Prices();
						dba.insert_Population();
						dba.close();

						ourDB = SQLiteDatabase.openDatabase(dbPath, null,
								SQLiteDatabase.OPEN_READONLY);
						System.out
								.println("After executing AndroidDB, database now exists.");
					}

					// Create a DBHandle object
					DB = new DBHandle(ourDB);

					// Retrieve area names in London
					areaNameFromDB = DB.getAreaNameFromDB();

					// Generate area names for spinner
					for (int area = 0; area < 32; area++) {
						areaNameForSpinner[area] = "Area " + (area + 1) + " - "
								+ areaNameFromDB[area];
					}

					System.out.println("Now, retrieve plotting data...");
					// Retrieve data for plotting for each area
					for (int a = 0; a < 32; a++) {
						areaCrimeNum[a] = DB.getCrimeNumArea(a);
						areaPopulationNum[a] = DB.getPopulationNumArea(a);
						areaPricesNum[a] = DB.getPricesNumArea(a);
						areaTreesNum[a] = DB.getTreesNumArea(a);
					}

					// Retrieve data for comparing among all areas
					crimeComparison = DB.getCrimeComparison();
					populationComparison = DB.getPopulationComparison();
					pricesComparison = DB.getPricesComparison();
					treesComparison = DB.getTreesComparison();

					// Retrieve average data of London based on latest year or
					// average of 10 years
					avgCrimeNumLondon = DB.getAvgCrimeLondon();
					avgPopulationNumLondon = DB.getAvgPopulationLondon();
					avgPricesNumLondon = DB.getAvgPricesLondon();
					avgTreesNumLondon = DB.getAvgTreesLondon();

					plottingDataReady = true;
					System.out.println("Retrieve plotting data completed.");
					DB.close();
					backendRunning = false;
					System.out
							.println("After retrieving plotting data, DB is closed.");
					Intent intent = new Intent(MainActivity.this,
							StartActivity.class);
					startActivity(intent);
					while (checking) {
						 try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (exitCliked) {
							finish();
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					}
				}
			}.start();
		} else {
			System.out.println("Plotting data is ready. "
					+ "Stuff including, CSV files, ColumnName and Token, "
					+ "and database are skipped.");
			Intent intent = new Intent(MainActivity.this, StartActivity.class);
			startActivity(intent);
			while (checking) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (exitCliked) {
					finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		}

	}

}