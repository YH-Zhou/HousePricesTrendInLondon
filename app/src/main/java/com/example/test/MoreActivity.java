package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MoreActivity extends Activity implements OnItemClickListener {

	String[] moreActivityList = new String[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);

		ListView moreActivity = (ListView) findViewById(R.id.more_view);
		moreActivityList[0] = "About";
		moreActivityList[1] = "Restart";
		moreActivityList[2] = "Exit";

		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item,
				moreActivityList);
		moreActivity.setAdapter(adapter);
		moreActivity.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (arg2) {
		case 0:
			System.out.println("About is cliked");
			AlertDialog alertDialog = new AlertDialog.Builder(MoreActivity.this)
					.create();
			alertDialog.setTitle("About");
			alertDialog
					.setMessage("This application allows users to have an insight into "
							+ "various interesting statistics such as house prices, crime numbers,"
							+ "population, and tree numbers for different London boroughs. "
							+ "All the data is retrieved from the official London Datastore "
							+ "data.london.gov.uk. Development team is composed of: "
							+ "Guiming Lin, Nikos Bregiannis, Yanhong Zhou, Yongjie Li, and Onur Yalçinkaya. "
							+ "Thank you for your usage! © UCL");
			alertDialog.setIcon(R.drawable.ic_launcher_small);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});

			alertDialog.show();
			break;
		case 1:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case 2:

			AlertDialog alertDialog1 = new AlertDialog.Builder(
					MoreActivity.this).create();
			alertDialog1.setTitle("Exit");
			alertDialog1
					.setMessage("You think you know about London? Think again! :)");
			alertDialog1.setIcon(R.drawable.ic_launcher_small);
			alertDialog1.setButton("Absolutely not, see more...",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			alertDialog1.setButton2("I assume so, leave now...",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
							MainActivity.exitCliked = true;

						}
					});

			alertDialog1.show();
			break;
		}
	}

}
