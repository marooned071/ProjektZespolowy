package com.example.qrpoll;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Adapter do wyswietlania historii, 
 * @author Sliwka
 * layout/row.xml
 */
public class HistoryAdapter extends ArrayAdapter<Item>{
	private final Context context;
	private final ArrayList<Item>items;
	public HistoryAdapter(Context context,ArrayList<Item> items){
		super(context,R.layout.row,items);
		this.context=context;
		this.items=items;
	}
	public View getView(int position,View convertView,ViewGroup parent){
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row,parent,false);
		TextView labelview=(TextView)rowView.findViewById(R.id.label);
		TextView valueview=(TextView)rowView.findViewById(R.id.value);
		TextView dateview=(TextView)rowView.findViewById(R.id.date);
		TextView roomView=(TextView)rowView.findViewById(R.id.room);
		labelview.setText(items.get(position).getName());
		valueview.setText(items.get(position).getValue());
		dateview.setText(items.get(position).getDate());
		roomView.setText(items.get(position).getRoom());
		labelview.setTextColor(Color.BLACK);
		valueview.setTextColor(Color.BLACK);
		dateview.setTextColor(Color.BLACK);
		roomView.setTextColor(Color.BLACK);
		/*ShapeDrawable.ShaderFactory sf=new ShapeDrawable.ShaderFactory() {
			
			@Override
			public Shader resize(int width, int height) {
				LinearGradient lg=new LinearGradient(0,0,width,height,new int[]{
						Color.rgb(125, 151, 146),Color.rgb(125,172,146),Color.rgb(151,151,146),Color.rgb(151,151,146)},new float[]{
						0,0.5f,.55f,1
				},Shader.TileMode.REPEAT);
				return lg;
			}
		};
		PaintDrawable p=new PaintDrawable();
		p.setShape(new RectShape());
		p.setShaderFactory(sf);*/
		labelview.setBackgroundColor(Color.rgb(170,170,170));
		valueview.setBackgroundColor(Color.rgb(175,175,175));
		dateview.setBackgroundColor(Color.rgb(180,180,180));
		roomView.setBackgroundColor(Color.rgb(187,187,187));
		return rowView;
		
	}
	
}