package com.example.test;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

public class AndroidDB {

	private SQLiteDatabase db;
	private final Context context;
	private final AndroidOpenDbHelper dbhelper;

	public AndroidDB(Context c) {
		context = c;
		dbhelper = new AndroidOpenDbHelper(context);
	}

	public void close() {
		db.close();
	}

	public void open() throws SQLiteException {
		try {
			System.out.println("Android.db open() is to be executed.");
			db = dbhelper.getWritableDatabase();
			System.out.println("Android.db open() has been executed.");
		} catch (SQLiteException ex) {
			Log.v("open exception caught", ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}

	public void insert_Crimes() {
		try {
			ContentValues newTaskValue = new ContentValues();
			// due to some invalid values, there is one row that cannot be
			// inserted to the table
			for (int row = 0; row < TokenNew.row; row++) {
				for (int column = 0; column < ColumnName.col_Name.length; column++) {
					newTaskValue.put(ColumnName.col_Name[column],
							TokenNew.tokenArr[row][column]);
				}
				// insert row by row to the table db.insert(CRIMES_TABLE, null,
				db.insert(Constants.CRIMES_TABLE, null, newTaskValue);
			}
		} catch (SQLiteException ex) {
			Log.v("open exception caught", ex.getMessage());
		}
	}

	public void insert_Prices() {
		try {
			ContentValues newTaskValue = new ContentValues();
			// due to some invalid values, there is one row that cannot be
			// inserted to the table
			for (int row = 0; row < TokenPrices.row; row++) {
				for (int column = 0; column < ColumnNamePrices.col_Name_Prices.length; column++) {
					newTaskValue.put(ColumnNamePrices.col_Name_Prices[column],
							TokenPrices.tokenArr_Prices[row][column]);
				} // insert row by row to the table
				db.insert(Constants.PRICES_TABLE, null, newTaskValue);
			}
		} catch (SQLiteException ex) {
			Log.v("open exception caught", ex.getMessage());
		}
	}

	public void insert_Trees() {
		try {
			ContentValues newTaskValue = new ContentValues();
			// due to some invalid values, there is one row that cannot be
			// inserted to the table
			for (int row = 0; row < TokenTrees.row; row++) {
				for (int column = 0; column < ColumnNameTrees.col_Name_Trees.length; column++) {
					newTaskValue.put(ColumnNameTrees.col_Name_Trees[column],
							TokenTrees.tokenArr_Trees[row][column]);
				} // insert row by row to the table
				db.insert(Constants.TREES_TABLE, null, newTaskValue);
			}
		} catch (SQLiteException ex) {
			Log.v("open exception caught", ex.getMessage());
		}
	}

	public void insert_Population() {
		try {
			ContentValues newTaskValue = new ContentValues();
			// due to some invalid values, there is one row that cannot be
			// inserted to the table
			for (int row = 0; row < TokenPopulation.row; row++) {
				for (int column = 0; column < ColumnNamePopulation.col_Name_Population.length; column++) {
					newTaskValue.put(
							ColumnNamePopulation.col_Name_Population[column],
							TokenPopulation.tokenArr_Population[row][column]);
				}
				// insert row by row to the table
				db.insert(Constants.POPULATION_TABLE, null, newTaskValue);
			}
		} catch (SQLiteException ex) {
			Log.v("open exception caught", ex.getMessage());
		}
	}
}
