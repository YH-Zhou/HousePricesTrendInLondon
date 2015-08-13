package com.example.test;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHandle {

	private SQLiteDatabase db_Handle;
	private int crimeLondonTotal = 0;
	private int pricesLondonTotal = 0;
	private int populationLondonTotal = 0;
	private int treeLondonTotal = 0;

	public DBHandle(SQLiteDatabase ourDB) {
		db_Handle = ourDB;
		MainActivity.db_Handle_Exist = true;
	}

	public void close() {
		db_Handle.close();
		MainActivity.db_Handle_Exist = false;
	}
	
	/** 
	 * Below are the functions that return the average values of London, in differnt aspects */
	
	public int getAvgCrimeLondon() {
		return crimeLondonTotal / 32;
	}
	
	public int getAvgPricesLondon() {
		return pricesLondonTotal / 32;
	}
	
	public int getAvgPopulationLondon() {
		return populationLondonTotal / 32;
	}
	
	public int getAvgTreesLondon() {
		return treeLondonTotal / 32;
	}
	
	 
	/**
	 * Below are the functions that compare the number of crime, prices,
	 * population, and trees in each area, based on the latest data, or the
	 * average data of years
	 */

	// Comparison for crime
	public int[] getCrimeComparison() {
		int comp[] = new int[32];
		int i = 0;
		for (int area = 0; area < 32; area++) {
			comp[i++] = getAvgCrimeNumArea(area);
			crimeLondonTotal = crimeLondonTotal + comp[i - 1];
		}
		return comp;
	}

	// Comparison for population
	public int[] getPopulationComparison() {
		int comp[] = new int[32];
		int i = 0;
		for (int area = 0; area < 32; area++) {
			comp[i++] = getLatestPopulationNumArea(area);
			populationLondonTotal = populationLondonTotal + comp[i - 1];
			
		}
		return comp;
	}

	// Comparison for prices
	public int[] getPricesComparison() {
		int comp[] = new int[32];
		int i = 0;
		for (int area = 0; area < 32; area++) {
			comp[i++] = getLatestPricesNumArea(area);
			pricesLondonTotal = pricesLondonTotal + comp[i - 1];
		}
		return comp;
	}

	// Comparison for trees
	public int[] getTreesComparison() {
		int comp[] = new int[32];
		int i = 0;
		for (int area = 0; area < 32; area++) {
			comp[i++] = getLatestTreesNumArea(area);
			treeLondonTotal = treeLondonTotal + comp[i - 1];
		}
		return comp;
	}

	/**
	 * Below are the functions that show latest number or average number of
	 * crime, prices, population, trees in each area
	 */

	// This function returns the average crime number for each area
	public int getAvgCrimeNumArea(int area) {
		
		int totNum = 0;
		int avgNum;
		for (int year = 0; year < 10; year++) {
			totNum = totNum + getCrimeNumArea(area)[year];
		}
		// Caculate and return the average number
		
		avgNum = totNum / 10;
		return avgNum;
	}

	// This function returns the latest population number for each area
	public int getLatestPopulationNumArea(int area) {
		return getPopulationNumArea(area)[9];
	}

	// This function returns the latest price for each area
	public int getLatestPricesNumArea(int area) {
		return getPricesNumArea(area)[9];
	}

	// This function returns the latest price for each area
	public int getLatestTreesNumArea(int area) {
		return getTreesNumArea(area)[1];
	}

	/** Below are the functions that return 10-year values for each area */

	// This function returns an array of the number of crime
	// in each area in each year during the last 10 years
	public int[] getCrimeNumArea(int area) {
		
		int[] CrimeNum = new int[10];
		int i = 0;
		for (int year = 6; year < 25;) {
			
			CrimeNum[i++] = getData(Constants.CRIMES_TABLE,
					ColumnName.col_Name[year],
					new String[] { Integer.toString(area + 1) });
			
			year = year + 2;
		}
		return CrimeNum;
	}

	// This function returns an array of the number of population
	// in each area in each year during the last 10 years
	public int[] getPopulationNumArea(int area) {
		int[] PopulationNum = new int[10];
		int i = 0;
		for (int year = (area + 1) * 10 + 1; year < (area + 2) * 10 + 1; year++) {
			PopulationNum[i++] = getData(Constants.POPULATION_TABLE,
					ColumnNamePopulation.col_Name_Population[3],
					new String[] { Integer.toString(year) });
		}
		return PopulationNum;
	}

	// This function returns an array of the number of prices
	// in each area in each year during the last 10 years
	public int[] getPricesNumArea(int area) {
		int[] PricesNum = new int[10];
		int i = 0;
		for (int year = 2; year < 12;) {
			PricesNum[i++] = getData(Constants.PRICES_TABLE,
					ColumnNamePrices.col_Name_Prices[year++],
					new String[] { Integer.toString(area + 1) });
		}
		return PricesNum;
	}

	// This function returns an int number of trees
	// in each area in each year during the last 10 years
	public int[] getTreesNumArea(int area) {
		int[] TreesNum = new int[2];
		int i = 0;
		for (int year = 2; year < 4;) {
			TreesNum[i++] = getData(Constants.TREES_TABLE,
				ColumnNameTrees.col_Name_Trees[year++],
				new String[] { Integer.toString(area + 1) });
		}
		
		return TreesNum;
	}

	/** Below are the common part for all tables */

	// This function returns a value from a specific table in the database
	public int getData(String TableName, String col, String[] row) {
		int data = 0;
		String sql_sel = "select " + col + " from " + TableName
				+ " where _id = ?";
		Cursor c = db_Handle.rawQuery(sql_sel, row);
		// moveToNext() move cursor to the next row
		while (c.moveToNext()) {
			// getString(i) gets String value of column i in the current row
			data = Integer.valueOf(c.getString(0));
		}
		c.close();
		return data;
	}

	// This function returns an array of the area name in London
	public String[] getAreaNameFromDB() {
		String[] areaName_temp = new String[32];
		String sql_sel = "select Area_name from Crimes_Table";
		Cursor c = db_Handle.rawQuery(sql_sel, null);
		for (int area = 0; area < 32; area++) {
			if (c.moveToNext()) {
				areaName_temp[area] = (c.getString(0).replaceAll(" ", "_")
						.replaceAll("&", "and"));
//				System.out.println(areaName_temp[area]);
			}
		}
		c.close();
		return areaName_temp;
	}

	// This function returns an array of the area names that match the
	// requirement of the user input of a crime number range
	public String[] searchArea(double crimeNum_Min, double crimeNum_Max,
			double populationNum_Min, double populationNum_Max, double pricesNum_Min,
			double pricesNum_Max, double treesNum_Min, double treesNum_Max) {
		// Use ArrayList since we do not have knowledge of the size
		// of the number how many areas match
		ArrayList<String> areaMatchedArrList = new ArrayList<String>();
		double crime_avg_temp, population_latest_temp, prices_latest_temp, trees_latest_temp;
		System.out.println("pricesNum_Max input: " + pricesNum_Max);
		
		// Get the average crime number for each area and justify the condition
		for (int area = 0; area < 32; area++) {
		
			 System.out.println("Avg for area " + area + ": "
			 + getAvgCrimeNumArea(area));
		
			crime_avg_temp = getAvgCrimeNumArea(area);
			population_latest_temp = getLatestPopulationNumArea(area);
			prices_latest_temp = getLatestPricesNumArea(area);
			trees_latest_temp = getLatestTreesNumArea(area);
			if ((crime_avg_temp > crimeNum_Min)
					&& (crime_avg_temp < crimeNum_Max)
					&& (population_latest_temp > populationNum_Min)
					&& (population_latest_temp < populationNum_Max)
					&& (prices_latest_temp > pricesNum_Min)
					&& (prices_latest_temp < pricesNum_Max)
					&& (trees_latest_temp > treesNum_Min)
					&& (trees_latest_temp < treesNum_Max)) {
				System.out.println("this area matches: " + area);
				areaMatchedArrList.add(MainActivity.areaNameFromDB[area]);
				SearchActivity.areaMatched_Exist = true;
			}
		}

		String[] areaMatchedArr;

		// Check first if there is/are area(s) matched
		if (SearchActivity.areaMatched_Exist) {
			// Assign the array list to a string array
			areaMatchedArr = new String[areaMatchedArrList.size()];
			for (int i = 0; i < areaMatchedArrList.size(); i++) {
				areaMatchedArr[i] = areaMatchedArrList.toArray()[i].toString();
			}
		} else {
			// Assign an array so that there will not be an exception in
			// MainActivity
			areaMatchedArr = MainActivity.areaNameFromDB;
		}
		
//		 System.out.println("length!! " + areaMatchedArr.length);

		return areaMatchedArr;
	}

}
