package com.example.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import android.os.Environment;
import android.util.Log;

public class TokenNew {
	public static String[][] tokenArr = new String[32][242];
	public static int row = 0;

	public static void getToken() {
		try {

			String strFile = Environment.getExternalStorageDirectory()
					+ "/ourApp/Crime.csv";

			// create BufferedReader to read csv file
			BufferedReader br = new BufferedReader(new FileReader(strFile));
			String strLine = "";
			StringTokenizer strToken = null;
			int column = 0;

			// skip the first line
			strLine = br.readLine();

			// read comma separated file line by line
			for (row = 0; row <32;) {
				
				// Read the next line
				strLine = br.readLine();

				// break comma separated line using ","
				strToken = new StringTokenizer(strLine, ",");

				while (strToken.hasMoreTokens()) {	
					//&& (strToken.nextToken() != null)
					tokenArr[row][column] = strToken.nextToken();
					// increase column
					column++;
				}
				// increase row if the current row has all valid tokens
				// in fact, there is one line invalid
				if(column == 242){
					row++;
				}
				// reset column number
				column = 0;
			}

		} catch (Exception e) {
			System.out.println("Exception 2-1 while reading csv file: " + e);
		}

	}

}
