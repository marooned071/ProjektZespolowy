package com.example.qrpoll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Glowne activity aplikacji, odpowiada za wyswietlanie informacji, pytan wraz z odpowiedziami i mozliwosci glosowania
 * @author Piotrek, Sliwka
 *
 */
public class PollActivity extends Activity {
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    
    private List<String> questions;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView version;
    private int backButtonCount=0;
    private RatingBar rb;
    

	private Poll poll = null;
	/**
	 * Przygotowanie naglowkow do wyswietlenia
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader=questions;
        
        for(int i =0;i<listDataHeader.size();i++){
        	List<String> list = new ArrayList<String>();
        	list.add("");
        	listDataChild.put(listDataHeader.get(i),list);
        }

    }
    
    
    @Override
    /**
     * tworzenie menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    /**
     * reagowanie na klikniecie na przyciski w menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //start historii
        if (id == R.id.historia) {
        	Intent it=new Intent(getApplication(),HistoryLayout.class);
			startActivity(it);
        	
        	return true;
        }else if(id==R.id.odswiez){
        	
        	refresh();
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	/**
	 * ustawienie layoutu
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_poll);
		
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
		tv4=(TextView)findViewById(R.id.textView4);
		version=(TextView)findViewById(R.id.poll_textViewVersionLabel);
		
		rb=(RatingBar)findViewById(R.id.ratingBar1);
		
		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				int rate=(int)(2*rating);
				SurveyResponse sr=new SurveyResponse(getApplicationContext());
			
				switch(rate){
				case 0:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(10));
					
					break;
				case 1:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(9));
					break;
				case 2:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(8));
					break;
				case 3:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(7));
					break;
				case 4:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(6));
					break;
				case 5:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(5));
					break;
				case 6:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(4));
					break;
				case 7:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(3));
					break;
				case 8:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(2));
					break;
				case 9:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(1));
					break;
				case 10:
					poll.vote(sr.createIdToSend()+"/"+poll.ratingquestions.get(0));
					break;
				}
			}
		});
		
		Intent intent = getIntent();
	    String url = intent.getStringExtra("scanResult");
	    url = "http://"+url;
		
		getPoll(url);
		try{
		prepareListData();
	
		listAdapter=new ExpandableListAdapter(this,listDataHeader,listDataChild,poll);
        expListView.setAdapter(listAdapter);
		Refresh refresh=(Refresh) new Refresh(poll,this).execute();
		String message =intent.getStringExtra("message");
		if(!message.equals("none")){
			Toast.makeText(getApplicationContext(),
			        "Zaktualizowano pytania. Aktualna wersja: "+poll.getVersion(), Toast.LENGTH_LONG).show();
		}
		}catch(Exception e){
			Intent it=new Intent(getApplication(),MainActivity.class);
			startActivity(it);
			finish();
		}
	
	    
		
	}

	
	/**
	 * tworzy nowa ankiete wg podanego url, ustawia odpowiednie informacje na komponentach
	 * @param url - adres do ankiety np: http://loony-waters-2513.herokuapp.com/qrpolls/meeting/b9ffd9db1af0b14ed74e92e8d3273f3e/
	 */
	public void getPoll(String url){
		try {
			poll = new Poll(url,getApplicationContext());
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(),
			        "Blad ankiety", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return;
		}
		questions=new ArrayList<String>();
		Map<String,Question> questionMap=this.poll.getQuestion_map();
		for(String key:questionMap.keySet()){
			Question q=questionMap.get(key);
			
    		questions.add(q.getQuestion_text() +"   Max: "+ q.getMax());
			
    	}
		tv1.setText(poll.getSubject());
		tv2.setText(poll.getHash_id());
		tv3.setText(poll.getStart_date());
		tv4.setText(poll.getRoom());
		version.setText("Version: "+poll.getVersion());
		SqlHandler sql=new SqlHandler(getApplication());
        sql.open();
        sql.insertSpotkania(poll.getHash_id(), poll.getSubject(), poll.getRoom(), poll.getStart_date());
        sql.close();
		}
	
	/**
	 * metoda wywolywana przy nacisnieciu przycisku back
	 */
	public void onBackPressed() {
		
		if(backButtonCount>=1){
			finish();
		}else{
			Toast.makeText(getApplicationContext(),
			        "Nacisnij jeszcze raz back aby wyjsc z aplikacji", Toast.LENGTH_SHORT).show();
			backButtonCount++;
		}
		
	        
	}
	/**
	 * metoda wywolywana przez klikniecie "odswiez", sprawdzajaca czy ankieta nie zostala zupdatowana
	 */
	public void refresh(){
		try {
			Poll p=new Poll(poll.getAddress(),getApplicationContext());
			if(poll.getVersion()!=p.getVersion()){
				Intent it=new Intent(getApplication(),PollActivity.class);
				it.putExtra("scanResult", poll.getAddress().substring(7));
				it.putExtra("message", "nowa wersja");
				startActivity(it);
				finish();
			}else{
				Toast.makeText(getApplicationContext(),
				       "Nie ma nowszej wersji", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			Toast.makeText(getApplicationContext(),
				       "Brak polaczenia", Toast.LENGTH_SHORT).show();
		}
	}
}
