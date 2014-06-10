package com.example.qrpoll.test;


import java.io.IOException;
import com.example.qrpoll.Exception404;
import com.example.qrpoll.MyHttpURLConnection;

import junit.framework.TestCase;

public class MyHttpURLConnectionTest extends TestCase {

	public void testGet() {
		String s = "xD";

		try {
			s = MyHttpURLConnection.get("http://loony-waters-2513.herokuapp.com/qrpolls/meeting/b9ffd9db1af0b14ed74e92e8d3273f3e/api/version/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String siema = "siema+\n nara";
		Debug.out(s);
		Debug.out(siema);
		Debug.out("[{\"version\": 1}]");
		Debug.out(s);
		Debug.out(s.equals("[{\"version\": 1}]"));
		
		assertEquals("[{\"version\": 1}]", s); //nie ogarniam dla czego to nie dziala 
		
	}
	

}




