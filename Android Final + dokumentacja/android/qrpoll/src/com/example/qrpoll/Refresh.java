package com.example.qrpoll;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Sliwka
 *klasa obslugujaca sprawdzanie wersji ankiety
 */
public class Refresh extends AsyncTask<Void,Void,Void>{
	private Poll poll;
	private Activity activity;
	private PollActivity pollAct;
	private int version;
	/**
	 * glowny konstruktor
	 * @param poll Aktualna ankieta
	 * @param activity activity ktore wywoluje asynctaska
	 */
	public Refresh(Poll poll,Activity activity){
		this.activity=activity;
		this.poll=poll;
		this.pollAct=(PollActivity)activity;
	}
	/**
	 * sprawdzanie czy nie zmienila sie wersja ankiety
	 */
	@Override
	protected Void doInBackground(Void... arg0) {
		while (!this.isCancelled()){
			try {
				Thread.sleep(30000);
				
				//Toast.makeText(activity.getApplicationContext()," watek dziala", Toast.LENGTH_SHORT).show();
				Log.d("", "wypisz do logu");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String adress=poll.getAddress();
			int version=poll.getVersion();
			Poll p=null;
			if(checkWifi()||checkNetwork()){
			try {
				p=new Poll(adress,activity.getApplicationContext());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(version!=p.getVersion()){
				this.version=version;
				pollAct.refresh();
				this.cancel(true);
			}
			}else{
				//Toast.makeText(activity.getApplicationContext(),
				  //      "Blad Polaczenia", Toast.LENGTH_SHORT).show();
				
				    
			}
		}
		return null;
	}
	/**
	 * sprawdzenie stanu wifi
	 * @return
	 */
	public boolean checkWifi(){
		WifiManager wifi=(WifiManager)activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		return wifi.isWifiEnabled();
	}
	/**
	 * sprawdzenie danych pakietowych
	 * @return
	 */
	public boolean checkNetwork(){
		ConnectivityManager cm=(ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return ni.isConnected();
	}
}
