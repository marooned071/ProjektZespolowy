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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * klasa odpowiadajaca za polaczenie sie z serwerem i pobranie zawartosci strony
 * na serwerze strony zwracaja dane przy uzyciu JSON
 * @author Piotrek
 *
 */
public class MyHttpURLConnection {
	/**
	 * pobieranie zawartosci strony z serwera
	 * @param url adres url strony z ktora chcemy sie polaczyc
	 * @return zawartosc strony
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception404 
	 */
	static public String get(String url) throws ClientProtocolException, IOException, Exception404{
		HttpClient httpclient = new DefaultHttpClient(); 
		HttpGet httpget = new HttpGet(url); 
		HttpResponse response = httpclient.execute(httpget); 
		HttpEntity entity = response.getEntity(); 
		
		int code = response.getStatusLine().getStatusCode();
		if(code==404) throw new Exception404(); 
	
		InputStream is = entity.getContent(); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) 
		    sb.append(line + "\n");
		
		is.close(); 
		String resString = sb.toString(); 
		
		return resString;	
	}
	
}

