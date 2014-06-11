package com.example.qrpoll;

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
 * Klasa do pobierania IMEI/nr karty sim 
 * @author Sliwka
 *
 */
public class AboutDevice {
	
	private TelephonyManager tm;
	private Context context;
	
	/**
	 * Konstruktor, ustawia kontekst oraz wyciaga z niego Managera do pobierania informacji
	 *@param context
	 */
	public AboutDevice(Context context){
		this.context=context;
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
	 * zwraca numer seryjny karty sim
	 * @return
	 */
	public String getSimId(){
		return tm.getSimSerialNumber();
	}
	/**
	 * Tworzy id zlozone z 8 znakow imei i 8 znakow nr karty sim
	 * @return identyfikator uzytkownika, ktory zostawiany bedzie przy glosowaniu
	 */
	public String createIdToSend(){
		String imei=getImei().substring(0, 8);
		String sim=getSimId().substring(0, 8);
		return imei+sim;
	}

	
}
