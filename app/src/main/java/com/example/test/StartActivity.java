package com.example.test;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class StartActivity extends TabActivity implements
		OnCheckedChangeListener {

	private RadioGroup mainTab;
	private TabHost tabhost;

	private Intent HomeIntent;
	private Intent SearchIntent;
	private Intent MoreIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);

		tabhost = getTabHost();

		HomeIntent = new Intent(this, HomeActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("Home")
				.setIndicator(getResources().getString(R.string.main_home),
						getResources().getDrawable(R.drawable.icon_1_n))
				.setContent(HomeIntent));

		SearchIntent = new Intent(this, SearchActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("Search")
				.setIndicator(getResources().getString(R.string.menu_search),
						getResources().getDrawable(R.drawable.icon_4_n))
				.setContent(SearchIntent));

		MoreIntent = new Intent(this, MoreActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("More")
				.setIndicator(getResources().getString(R.string.main_more),
						getResources().getDrawable(R.drawable.icon_5_n))
				.setContent(MoreIntent));

	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_button:
			this.tabhost.setCurrentTabByTag("Home");
			break;
		case R.id.search_button:
			this.tabhost.setCurrentTabByTag("Search");
			break;
		case R.id.more_button:
			this.tabhost.setCurrentTabByTag("More");
			break;
		}
	}

}
