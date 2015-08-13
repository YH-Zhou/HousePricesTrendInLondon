package com.example.test;



import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
/* helper class for converting a location name to GeoPoint 
 * original getFromLocationName() seem have bug
 * */
public class MapUtility {
	/* get JSON object from google map web service */
	public static JSONObject getLocationInfo(String address) {
		
		HttpGet httpGet = new HttpGet("http://maps.google."
				+ "com/maps/api/geocode/json?address=" + address
				+ "&sensor=false");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	// After executing this, another method converts that JSONObject into a
	// GeoPoint.

	public static GeoPoint getGeoPoint(JSONObject jsonObject) {

		double lon = 0.0;
		double lat = 0.0;

		try {
			JSONArray result = ((JSONArray) jsonObject.get("results"));
			int i = 0;
			/* MODIFICATION !! */
			// trick to avoid wrong location (the first entry is for Great London when multiple results available)
			if(result.length() > 1) i = 1;
			lon = result.getJSONObject(i)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");

			lat = result.getJSONObject(i)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
	}

}