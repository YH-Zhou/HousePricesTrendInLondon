package com.example.test;

import java.io.*;
import java.util.*;

import android.os.Environment;

public class ColumnNamePopulation {

	public static String[] col_Name_Population = new String[188];
	public static String col_Name_sum_Population = null;

	public static void getColumnNamePopulation() {

		// String col_Name_sum_temp = null;
		StringBuffer col_Name_buffer = new StringBuffer();

		try {

			String strFile = Environment.getExternalStorageDirectory()
					+ "/ourApp/Population.csv";

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
				col_Name_Population[tokenNumber] = (st.nextToken()
						.replaceAll("-", "_").replaceAll("\\+", "_").replaceAll(
						" ", "_"));
				// System.out.println(col_Name[tokenNumber]);
				tokenNumber++;
			}

			for (int i = 0; i < ColumnNamePopulation.col_Name_Population.length - 1; i++) {
				col_Name_buffer = col_Name_buffer.append(
						ColumnNamePopulation.col_Name_Population[i]).append(
						" Text not null, ");
			}

			col_Name_buffer = col_Name_buffer.append(
					ColumnNamePopulation.col_Name_Population[187]).append(
					" Text not null);");

			col_Name_sum_Population = col_Name_buffer.toString();

		} catch (Exception e) {
			System.out.println("Exception 1-2 while reading csv file: " + e);
		}

	}
}