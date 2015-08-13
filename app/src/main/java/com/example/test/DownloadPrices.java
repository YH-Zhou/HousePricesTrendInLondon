package com.example.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class DownloadPrices extends AsyncTask<Void, Void, String> {
	ProgressDialog mProgressDialog;
	Context context;
	public static boolean downloadPricesCompleted = false;

	public DownloadPrices(Context context) {
		this.context = context;
	}

//	protected void onPreExecute() {
//		mProgressDialog = ProgressDialog.show(context, "   ",
//				" Please wait, downloading Prices.csv...");
//	}

	protected String doInBackground(Void... params) {
		try {

			// create URL object
			URL url = new URL(
					"https://dl.dropbox.com/s/yck7l4d130wmqkh/House_price.csv?dl=1");
			// open the http connection
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			// add some options
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			// connect to it!
			c.connect();
			System.out.println("Download prices started.");

			// Creates the path to the external memory to download the file
			String PATH = Environment.getExternalStorageDirectory()
					+ "/ourApp/";
			Log.v("", "PATH: " + PATH);
			File file = new File(PATH);
			file.mkdirs();
			// this is the final downloaded file
			File outputFile = new File(file, "Prices.csv");
			// the stream that writes to the file
			FileOutputStream fos = new FileOutputStream(outputFile);
			// the stream from the connection
			InputStream is = c.getInputStream();

			// create a buffer to store the is and fos
			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}
			fos.close();
			is.close();
			System.out.println("Download prices completed.");
			downloadPricesCompleted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "done";
	}

//	protected void onPostExecute(String result) {
//		if (result.equals("done")) {
//			mProgressDialog.dismiss();
//		}
//	}
}