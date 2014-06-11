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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
/**
*klasa adaptera do listy z pytaniami,dla danego id pobiera przypisane pytania
*/
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Context _context;
	private Poll poll;
	public AboutDevice sr;
	private Map<String,Question> questionMap;
	private Map<Integer,List<String>> mapaButton;
	private List<String> questionList;
    private List<String> _listDataHeader; // header titles
  private HashMap<String, List<String>> _listDataChild;
    
	/**
	*metoda dodajaca pytania do listy
	*/
    public void setQuestions(){
    	questionList=new ArrayList<String>();
    	for(String key:questionMap.keySet()){
    		questionList.add(key);
    	}
    }
    
    /**
	* ustawienie mapy pytan
	*/
    public void setPoll(Poll poll){
    	this.poll=poll;
    	questionMap=this.poll.getQuestion_map();
    	
    }
    /**
     *konstruktor, ustawia naglowki, wywoluje ustawienie pytan i odpowiedzi 
     */
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData,Poll poll) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        sr=new AboutDevice(context);
        mapaButton=new HashMap<Integer,List<String>>();
        setPoll(poll);
        setQuestions();
    }
 
    /**
     * zwraca item o danej pozycji w grupie
     */
    public Object getChild(int groupPosition, int childPosititon) {
        return null;
    }
 
    /**
     * zwraca id itemu w danej grupie
     */
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    /**
     * ustawienie wyswietlania pytan, przypisanie akcji do przyciskow "Wyslij"
     */
    public View getChildView(final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	String headerTitle = (String) getGroup(groupPosition);
     
        	String key=questionList.get(groupPosition);
        	Question q=questionMap.get(key);
        	Map<String, Choice> choiceMap= q.getChoice_map();
        	
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
            LinearLayout linear = (LinearLayout) convertView
                    .findViewById(R.id.explinear);
            LinearLayout l=new LinearLayout(this._context);
            l.setOrientation(LinearLayout.VERTICAL);
            
            final ArrayList<String> list = new ArrayList<String>();
            for(String choice: choiceMap.keySet()){
            	Choice c = choiceMap.get(choice);
            	CheckBox rb = new CheckBox(this._context);
            	final int rbID = Integer.parseInt(c.getPk());
            	rb.setText(c.getChoice_text());
            	rb.setOnCheckedChangeListener(new OnCheckedChangeListener()
            	{

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						//Toast.makeText(_context,groupPosition+"", Toast.LENGTH_SHORT).show();
						if(list.contains("id:"+rbID)){
							
							list.remove("id:"+rbID);
						}else{
							list.add("id:"+rbID);
						}
						
					}

            	});
            	l.addView(rb);
            }
            mapaButton.put(groupPosition, list);
            Button rb=new Button(this._context);
            rb.setText("Zaglosuj");
            rb.setId(groupPosition);
            rb.setOnClickListener(new ButtonListener());
            l.addView(rb);
            
            linear.addView(l);

 
        return convertView;
    }
 
    /**
     * zwraca ilosc itemow w danej grupie
     */
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    /**
     * zwraca ilosc grup
     */
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    /**
     * zwraca id grupy na danej pozycji
     */
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    /**
     * zwraca view grupy o danym id
     */
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        
        return convertView;
    }
 
 
    
    /**
	* Klasa implementujaca onClickListner, sluzy do przypisanie akcji pod przyciski do wysylania odpowiedzi
	*/
    private class ButtonListener implements OnClickListener{
    	
    	/**
		* Metoda, ktora reaguje na klikniecie na dany przycisk
		*/
    	public void onClick(View arg0) {
    		List<String>list=mapaButton.get(arg0.getId());
    		if(list.size()!=0){
    			String vote=null;
    			for(int i=0;i<list.size();i++){
    				String id=list.get(i).substring(3);
    				vote+=id+","; 			
    			}
    			vote=vote.substring(4,vote.length()-1);
    		AboutDevice rs=new AboutDevice(_context);
    		poll.vote(sr.createIdToSend()+"/"+vote);
    		}else{
    			Toast.makeText(_context.getApplicationContext(),"Nic nie wybrano", Toast.LENGTH_SHORT).show();
    		}
    	}
    }



	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
