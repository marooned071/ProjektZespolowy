package com.example.qrpoll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpURLConnection {
	private static final String USER_AGENT = "Mozilla/5.0";
	
	static public String get(String url) throws Exception, Exception404 {	 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if(responseCode==404) throw new Exception404();
		
	//	System.out.println("\nSending 'GET' request to URL : " + url);
	//	System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
  
		return response.toString();
	}

}

class Exception404 extends Exception{
	Exception404(){
		super();
	}
}
