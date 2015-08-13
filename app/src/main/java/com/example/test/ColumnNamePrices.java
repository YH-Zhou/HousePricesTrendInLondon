package com.example.test;

import java.io.*;
import java.util.*;

import android.os.Environment;

public class ColumnNamePrices {

	public static String[] col_Name_Prices = new String[12];
	public static String col_Name_sum_Prices = null;

	public static void getColumnNamePrices() {

		// String col_Name_sum_temp = null;
		StringBuffer col_Name_buffer = new StringBuffer();

		try {

			String strFile = Environment.getExternalStorageDirectory()
					+ "/ourApp/Prices.csv";

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer st = null;
			int tokenNumber = 0;

			// read comma separated file line by line
			strLine = br.readLine();

			// break comma separated line using ","
			st = new StringTokenizer(strLine, ",");

			while (st.hasMoreTokens()) {
				// assign each column name to the string array
				col_Name_Prices[tokenNumber] = (st.nextToken());
				// System.out.println(col_Name[tokenNumber]);
				tokenNumber++;
			}

			for (int i = 0; i < ColumnNamePrices.col_Name_Prices.length - 1; i++) {
				col_Name_buffer = col_Name_buffer.append(
						ColumnNamePrices.col_Name_Prices[i]).append(
						" Text not null, ");
			}

			col_Name_buffer = col_Name_buffer.append(
					ColumnNamePrices.col_Name_Prices[11]).append(
					" Text not null);");

			col_Name_sum_Prices = col_Name_buffer.toString();

		} catch (Exception e) {
			System.out.println("Exception 1-3 while reading csv file: " + e);
		}

	}
}