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

public class HistoryLayout extends Activity {
	private ListView lv;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_history_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
		title=(TextView)findViewById(R.id.title);
		title.setText("Meetings History");
		/*View title=getWindow().findViewById(R.id.title);
		View titleBar=(View)title.getParent();
		titleBar.setBackgroundColor(Color.BLUE);*/
		lv=(ListView)findViewById(R.id.listView1);
		lv.setBackgroundColor(Color.rgb(170,170,170));
		SqlHandler sql=new SqlHandler(getApplication());
		sql.open();
		ArrayList<Item>list=sql.getAll();
		HistoryAdapter adapter=new HistoryAdapter(this,list);
		
		lv.setAdapter(adapter);
	}
	private ArrayList<Item>generateData(){
		ArrayList<Item>items=new ArrayList<Item>();
		items.add(new Item("Item1","Value1","Date1","Room1"));
		items.add(new Item("Item2","Value2","Date1","Room1"));
		items.add(new Item("Item3","Value3","Date1","Room1"));
		items.add(new Item("Item4","Value4","Date1","Room1"));
		items.add(new Item("Item5","Value5","Date1","Room1"));
		items.add(new Item("Item6","Value6","Date1","Room1"));
		items.add(new Item("Item7","Value7","Date1","Room1"));
		items.add(new Item("Item8","Value8","Date1","Room1"));
		return items;
	}

}

