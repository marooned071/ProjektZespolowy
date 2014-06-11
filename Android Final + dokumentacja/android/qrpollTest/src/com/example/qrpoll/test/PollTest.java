package com.example.qrpoll.test;

import java.util.Map;

import android.util.Log;

import com.example.qrpoll.Poll;
import com.example.qrpoll.Question;

import junit.framework.TestCase;

public class PollTest extends TestCase {
	
	
	Poll p;
	protected void setUp() throws Exception {
		 p = new Poll("http://loony-waters-2513.herokuapp.com/qrpolls/meeting/b9ffd9db1af0b14ed74e92e8d3273f3e/");
		super.setUp();
	}
	
	public void testGetHash_id() {
		String hash_id = p.getHash_id();
		assertEquals("b9ffd9db1af0b14ed74e92e8d3273f3e", hash_id);
	}

	public void testGetSubject() {
		String subject = p.getSubject();
		assertEquals("Byc czy miec?", subject);
	}

	public void testGetRoom() {
		String room = p.getRoom();
		assertEquals("3B", room);
	}
	
	public void testGetStart_date() {
		String startDate = p.getStart_date();
		assertEquals("01-10-2011",startDate);
	}
	
	public void testGetVersion() {
		int version = p.getVersion();
		assertEquals(1,version);
	}
	
	public void testGetQuestionMapSize() { //paczy ile pytan udalo mu sie sciagnac xD
		Map<String, Question> m = p.getQuestion_map();
		assertEquals(2,m.size());		
	}
	
	
	
	public void testQuestionsAndAnserrs(){
		Map<String, Question> m = p.getQuestion_map();
		assertEquals(2,m.size());
		Question q;
		q= m.get("3"); // pk - klucz publiczny pytanie: Lubisz mnie ? odpowiedzi dwie: tak, nie
		q.getChoice_map().size();
		assertEquals(q.getChoice_map().size(), 2); //choicemap zawiera 2 rekordy
		
		
		
		q= m.get("2"); // pk - klucz publiczny pytanie: Jak sie podoba ? odpowiedzi trzy : Nudy, Sredio, Fajnie
		q.getChoice_map().size();
		assertEquals(q.getChoice_map().size(), 3); 
		
		Log.d("moje", "przed");
		p.vote(q.getChoice_map().get("6"));
		
		
	}
	

	
	
	
	
	

	
}
