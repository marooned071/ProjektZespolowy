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
 * @author Piotrek
 *
 */
public class MyHttpURLConnection {
	/**
	 * pobieranie zawartosci strony
	 * @param url adres url strony z ktora chcemy sie polaczyc
	 * @return zawartosc strony
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception404
	 */
	static public String get(String url) throws ClientProtocolException, IOException, Exception404{
		HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
		HttpGet httpget = new HttpGet(url); // Set the action you want to do
		HttpResponse response = httpclient.execute(httpget); // Executeit
		HttpEntity entity = response.getEntity(); 
		
		int code = response.getStatusLine().getStatusCode();
		if(code==404) throw new Exception404(); //jesli kod odpowiedzi jest 404, wyrzuc Exception404 (taka strona nie istanieje)
	
		InputStream is = entity.getContent(); // Create an InputStream with the response
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) // Read line by line
		    sb.append(line + "\n");
		
		is.close(); // Close the stream
		String resString = sb.toString(); // Result is here
		
		return resString;	
	}
	
}

