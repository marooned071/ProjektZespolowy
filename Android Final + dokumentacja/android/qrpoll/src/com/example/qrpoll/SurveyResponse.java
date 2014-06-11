package com.example.qrpoll;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.telephony.TelephonyManager;
/**
 * Klasa do pobierania IMEI i wysylania go na serwer
 * (do przetestowania, moj tel z androidem sie zepsul wiec nie mam jak dobrze przetestowac tego
 * @author Sliwka
 *
 */
public class SurveyResponse {
	
	private TelephonyManager tm;
	private Context context;
	
	public SurveyResponse(){	}
	
	public SurveyResponse(Context context){
		this.context=context;
		tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public SurveyResponse(TelephonyManager tm){
		this.tm=tm;
	}
	
	public void setContext(Context context){
		this.context=context;
	}
	/**
	 * ustawia telephony manager
	 */
	public void setTm(){
		tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	/**
	 * zwraca nr imei telefonu
	 * @return
	 */
	public String getImei(){
		return tm.getDeviceId();
	}
	
	/**
	 * zwraca identyfikator karty sim
	 * @return
	 */
	public String getSimId(){
		return tm.getSimSerialNumber();
	}
	/**
	 * Tworzy id zlozone z 8 znakow imei i 8 znakow nr karty sim
	 * @return
	 */
	public String createIdToSend(){
		String imei=getImei().substring(0, 8);
		String sim=getSimId().substring(0, 8);
		return imei+sim;
	}

	
}
