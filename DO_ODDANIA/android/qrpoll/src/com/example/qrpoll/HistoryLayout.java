/*
* Copyright 2014 Byliniak, Sliwka, Gambus, Celmer
*
* Licensed under the Surveys License, (the "License");
* you may not use this file except in compliance with the License.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*/
package com.example.qrpoll;

import java.util.ArrayList;
import java.util.Arrays;




import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;
/**
*Pelnoekranowe activity sluzace do wyswietlania danych z bazy na liscie
*/
public class HistoryLayout extends Activity {
	private ListView lv;
	private TextView title;
	
	/**
	*tworzenie widoku,ustawienie adaptera do listy
	*/
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_history_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
		title=(TextView)findViewById(R.id.title);
		title.setText("Meetings History");
		lv=(ListView)findViewById(R.id.listView1);
		lv.setBackgroundColor(Color.parseColor("#334455"));
		SqlHandler sql=new SqlHandler(getApplication());
		sql.open();
		ArrayList<Item>list=sql.getAll();
		HistoryAdapter adapter=new HistoryAdapter(this,list);
		
		lv.setAdapter(adapter);
	}

}

