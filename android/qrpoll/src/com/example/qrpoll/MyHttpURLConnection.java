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



public class MyHttpURLConnection {
	
//	static public String get(String url) throws Exception, Exception404 {	 
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		// optional default is GET
//		con.setRequestMethod("GET");
//		//add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		int responseCode = con.getResponseCode();
//		if(responseCode==404) throw new Exception404();
//		
//	//	System.out.println("\nSending 'GET' request to URL : " + url);
//	//	System.out.println("Response Code : " + responseCode);
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
// 
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();
//  
//		return response.toString();
//	}
	
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

