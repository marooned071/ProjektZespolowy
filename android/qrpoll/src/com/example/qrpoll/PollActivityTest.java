package com.example.qrpoll;

import java.util.Map;

import org.json.JSONException;

import com.google.zxing.integration.android.IntentIntegrator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class PollActivityTest extends ActionBarActivity implements OnClickListener{
	
	
	private Poll p;
	private Button sendAnswerButton;
	private RadioButton r1;
	private RadioButton r2;
	private RadioButton r3;
	private Choice c1;
	private Choice c2;
	private Choice c3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poll_test);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	    Intent intent = getIntent();
	    String url = intent.getStringExtra("scanResult");
	    url = "http://"+url;
	    makePoll(url);
	    
	    sendAnswerButton = (Button)findViewById(R.id.button_sendAnswer);
	    sendAnswerButton.setOnClickListener(this);

	}
	
	
	public void makePoll(String url){
		
		
	    try {
			p = new Poll(url,getApplicationContext());
		} catch (JSONException e) {
			Log.d("moje", "JSON SIE ZJEBAL");
			e.printStackTrace();
			return;
		}
	    
	    final LinearLayout lm = (LinearLayout) findViewById(R.id.linear_question);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        
        Map<String,Question> questionMap = p.getQuestion_map();
        
        Question q;
        for(String key: questionMap.keySet()){
        	q = questionMap.get(key);
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            
            TextView questionText = new TextView(this);
            questionText.setText(q.getQuestion_text());
            ll.addView(questionText);
            
            
            lm.addView(ll);  
        	
        }
	    
	    
		
	}
	
	/**
	 * Tworzy ankiete z przyciskow ktore sa zapisane na sztywno
	 * @param url
	 */
	public void makeTestpoll(String url){
		Log.d("moje", "MAKE POLL");
		
		Log.d("moje", url);
		Log.d("moje", url);
		

		
	    try {
			p = new Poll(url,getApplicationContext());
		} catch (JSONException e) {
			Log.d("moje", "JSON SIE ZJEBAL");
			e.printStackTrace();
			return;
		}
	    
	    TextView textView_version = (TextView) findViewById(R.id.textView_version);
	    TextView textView_question =(TextView) findViewById(R.id.textView_question);
	    
	    Map<String,Question> questionMap = p.getQuestion_map();
	    
	    Question q2 = questionMap.get("2"); //pobieram pytanie o id 2 ("Jak sie podoba?", ma trzy odpowiedzi: Nudy, Srednio, Fajnie)
	    
	    textView_question.setText(q2.getQuestion_text()); // ustawiam textView question na tekst pytania
	    
	    Map<String,Choice> q2Choices = q2.getChoice_map();
	    
	    r1 = (RadioButton) findViewById(R.id.radioButton_choice1);
	    r2 = (RadioButton) findViewById(R.id.radioButton_choice2);
	    r3 = (RadioButton) findViewById(R.id.radioButton_choice3);
	    
	    c1 = q2Choices.get("4");
	    c2 = q2Choices.get("5");
	    c3 = q2Choices.get("6");
	    
	    r1.setText(c1.getChoice_text());
	    r2.setText(c2.getChoice_text());
	    r3.setText(c3.getChoice_text());
	    
	    
	    

	}
	

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poll, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_poll_test, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button_sendAnswer){
			if(r1.isChecked()){
				p.vote(c1);
			}
			else if(r2.isChecked()){
				p.vote(c2);
			}
			else if(r3.isChecked()){
				p.vote(c3);
			}
			
			//scan

		}
		
	}
	


}




