package com.example.test;

import java.io.*;
import java.util.*;

import android.os.Environment;

public class ColumnNameTrees {

	public static String[] col_Name_Trees = new String[8];
	public static String col_Name_sum_Trees = null;

	public static void getColumnNameTrees() {

		// String col_Name_sum_temp = null;
		StringBuffer col_Name_buffer = new StringBuffer();

		try {

			String strFile = Environment.getExternalStorageDirectory()
					+ "/ourApp/Trees.csv";

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
				col_Name_Trees[tokenNumber] = (st.nextToken()
						.replaceAll("-", "_").replaceAll("'", "_")
						.replaceAll("\\[", "_").replaceAll("]", "_")
						.replaceAll(" ", "_"));
				// System.out.println(col_Name[tokenNumber]);
				tokenNumber++;
			}

			for (int i = 0; i < ColumnNameTrees.col_Name_Trees.length - 1; i++) {
				col_Name_buffer = col_Name_buffer.append(
						ColumnNameTrees.col_Name_Trees[i]).append(
						" Text not null, ");
			}

			col_Name_buffer = col_Name_buffer.append(
					ColumnNameTrees.col_Name_Trees[7]).append(
					" Text not null);");

			col_Name_sum_Trees = col_Name_buffer.toString();

		} catch (Exception e) {
			System.out.println("Exception 1-4 while reading csv file: " + e);
		}

	}
}