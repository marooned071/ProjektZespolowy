package com.example.qrpoll;

import android.support.v4.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
/**
 * Activity, ktore laduje sie przy starcie aplikacji, umozliwia przejscie do skanowania oraz wyswietlenie historii
 * @author Sliwka,Piotrek
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	
	
	private Button scanBtn;
	private Button button1;
	private TextView formatTxt, contentTxt;
	
    @Override
    /**
     * 
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        scanBtn = (Button)findViewById(R.id.scan_button);
        button1=(Button)findViewById(R.id.button1);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        
        scanBtn.setOnClickListener(this);
        SqlHandler sql=new SqlHandler(getApplication());
        sql.open();
        sql.close();
        if(!checkNetwork()){
        	if(!checkWifi()){
        		AlertDialog.Builder adb=new AlertDialog.Builder(this);
        		adb.setTitle("Brak polaczenia");
        		adb.setMessage("Nacisnij ok aby wlaczyc WIFI").setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WifiManager wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
						wifi.setWifiEnabled(true);
					}
				});
        		AlertDialog ad=adb.create();
        		ad.show();
        	}
        }
    }

	@Override
	/**
	 * przypisanie akcji do przycisku skanowania
	 */
	public void onClick(View v) {
		if(v.getId()==R.id.scan_button){
			//scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
		
	}
	/**
	 * metoda przetwarzajaca wynik skanowania
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		try{
		if(requestCode == IntentIntegrator.REQUEST_CODE){
			//retrieve scan result
			IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			
			if (scanningResult != null) {
				//we have a result
				String scanContent = scanningResult.getContents();
				toPollActivity(scanContent);
				
				
			}
			else{
			    Toast toast = Toast.makeText(getApplicationContext(),
			        "No scan data received!", Toast.LENGTH_SHORT);
			    toast.show();
			}
			
			
						
		}
		}catch(NullPointerException e){
		    Toast toast = Toast.makeText(getApplicationContext(),
			        "Przerwano skanowanie", Toast.LENGTH_SHORT);
			    toast.show();
		}
	}
	
	/**
	 * startuje nowe activity
	 * @param scanResult zeskanowany adres url
	 */
	public void toPollActivity(String scanResult){
		if(!scanResult.isEmpty()){
			Intent intent = new Intent(this, PollActivity.class);
			intent.putExtra("scanResult", scanResult);
			intent.putExtra("message", "none");
			if(checkWifi()||checkNetwork()){
				try{
				startActivity(intent);
				finish();
				}catch(RuntimeException e){
					Toast toast = Toast.makeText(getApplicationContext(),
					        "Nie znaleziono strony o danym adresie!", Toast.LENGTH_SHORT);
					    toast.show();
				}
				
			}else{
				Toast toast = Toast.makeText(getApplicationContext(),
				        "Brak polaczenia,nie mozna kontynuowac!", Toast.LENGTH_SHORT);
				    toast.show();
			}
		
		}else{
			Toast toast = Toast.makeText(getApplicationContext(),
			        "No scan data received!", Toast.LENGTH_SHORT);
			    toast.show();
		}
	}
	/**
	 * sprawdzenie stanu wifi
	 * @return
	 */
	public boolean checkWifi(){
		WifiManager wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		return wifi.isWifiEnabled();
	}
	/**
	 * sprawdzenie danych pakietowych
	 * @return
	 */
	public boolean checkNetwork(){
		ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return ni.isConnected();
	}
	
    @Override
    /**
     * tworzenie menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    /**
     * przypisanie akcji do przyciskow menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //start historii
        if (id == R.id.action_settings) {
        	Intent it=new Intent(getApplication(),HistoryLayout.class);
			startActivity(it);
        	
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
