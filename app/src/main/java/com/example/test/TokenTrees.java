package com.example.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import android.os.Environment;
import android.util.Log;

public class TokenTrees {
	public static String[][] tokenArr_Trees = new String[33][8];
	public static int row = 0;

	public static void getTokenTrees() {
		try {

			String strFile = Environment.getExternalStorageDirectory()
					+ "/ourApp/Trees.csv";

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer strToken = null;
			int column = 0;

			// skip the first line
			strLine = br.readLine();

			// read comma separated file line by line
			while ((strLine = br.readLine()) != null) {

				// break comma separated line using ","
				strToken = new StringTokenizer(strLine, ",");

				while (strToken.hasMoreTokens()) {
					// && (strToken.nextToken() != null)
					tokenArr_Trees[row][column] = strToken.nextToken();
					// increase column
					column++;
				}
				// increase row if the current row has all valid tokens
				if (column == 8) {
					row++;
				}
				// reset column number
				column = 0;
				
			}
			
		} 
		catch (Exception e) {
			System.out.println("Exception 2-4 while reading csv file: " + e);
		}

	}
}
