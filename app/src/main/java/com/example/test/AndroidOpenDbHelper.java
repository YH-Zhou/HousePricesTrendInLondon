package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.*;

// A helper class to manage database creation and version management. 
public class AndroidOpenDbHelper extends SQLiteOpenHelper {

	// String sqldetails = "create table if not exists " + AndroidDB.CRIME_TABLE
	// + " ( " + BaseColumns._ID + " Integer Primary Key autoincrement, "
	// + ColumnName.col_Name_sum;
	String sqldetailcrimes = "create table if not exists "
			+ Constants.CRIMES_TABLE + " ( " + BaseColumns._ID
			+ " Integer Primary Key autoincrement, " + ColumnName.col_Name_sum;
	String sqldetailtrees = "create table if not exists "
			+ Constants.TREES_TABLE + " ( " + BaseColumns._ID
			+ " Integer Primary Key autoincrement, "
			+ ColumnNameTrees.col_Name_sum_Trees;
	String sqldetailprices = "create table if not exists "
			+ Constants.PRICES_TABLE + " ( " + BaseColumns._ID
			+ " Integer Primary Key autoincrement, "
			+ ColumnNamePrices.col_Name_sum_Prices;
	String sqldetailpopulation = "create table if not exists "
			+ Constants.POPULATION_TABLE + " ( " + BaseColumns._ID
			+ " Integer Primary Key autoincrement, "
			+ ColumnNamePopulation.col_Name_sum_Population;

	public AndroidOpenDbHelper(Context context) {
		super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
	}

	// Called when the database is created for the first time.
	// This is where the creation of tables and the initial population of the
	// tables should happen.
	@Override
	public void onCreate(SQLiteDatabase db) {
		// We need to check whether table that we are going to create is already
		// exists.
		// Because this method get executed every time we created an object of
		// this class.
		// "create table if not exists TABLE_NAME ( BaseColumns._ID integer primary key autoincrement, FIRST_COLUMN_NAME text not null, SECOND_COLUMN_NAME integer not null);"
		Log.v("AndroidOpendDbHelper onCreate", "Creating all the tables");

		try {
			// Execute a single SQL statement that is NOT a SELECT or any other
			// SQL
			// statement that returns data.
			db.execSQL(sqldetailcrimes);
			db.execSQL(sqldetailtrees);
			db.execSQL(sqldetailprices);
			db.execSQL(sqldetailpopulation);
			// System.out.println("All tables have been created.");

		} catch (SQLiteException ex) {
			Log.v("Create table exception", ex.getMessage());
		}

	}

	// onUpgrade method is use when we need to upgrade the database in to a new
	// version
	// As an example, the first release of the app contains DB_VERSION = 1
	// Then with the second release of the same app contains DB_VERSION = 2
	// where you may have add some new tables or alter the existing ones
	// Then we need check and do relevant action to keep our pass data and move
	// with the next structure
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			// Upgrade the database
		}
	}
}
