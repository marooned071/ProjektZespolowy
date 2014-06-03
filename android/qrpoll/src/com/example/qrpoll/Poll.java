package com.example.qrpoll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;


/**
 * Klasa odpowiedzialna za obsluge ankiety.
 * 
 * @author Piotrek
 * 
 */
public class Poll {


	private String address; // pelen adres strony
	private String hash_id;
	private String subject;
	private String room;
	private String start_date;
	private int version;
	private Map<String,Question> question_map;
	private Context context;
	
	public int ratingID=0;
	public List<String> ratingquestions=new ArrayList<String>();

	public Poll(String address,Context context) throws JSONException{
		
		Log.d("moje", "POLL KONSTRUKTOR");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //dwie magiczne linie kotre ratuja przed android.os.NetworkOnMainThreadException
		StrictMode.setThreadPolicy(policy); //TO:DO przerobic na AsyncTask http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
		
		this.address = address;
		this.context = context;
		//INFORMACJE O ANKIECIE
		/*
		[
		   {
		      "info":{
		         "start_date":"01-10-2011",
		         "subject":"Byc czy miec?",
		         "room":"3B",
		         "hash_id":"b9ffd9db1af0b14ed74e92e8d3273f3e"
		      }
		   }
		]
		 */
		
		String full_address = address + "api/info";
		String s = "";
		try {
			s = MyHttpURLConnection.get(full_address);
		} catch (Exception404 e) {
			System.out.println("404!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray array;
		JSONObject jo;
		JSONObject jo_inside;
		
		try{
			array = new JSONArray(s);
			jo = array.getJSONObject(0);
			jo_inside = jo.getJSONObject("info"); //inside bo ten obiekt jest zagniezdzony 
			this.hash_id = jo_inside.get("hash_id").toString();
			this.subject = jo_inside.get("subject").toString();
			this.room = jo_inside.get("room").toString();
			this.start_date =  jo_inside.get("start_date").toString();
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		// WERSJA ANKIETY
		
		/*
		 * 	[
		 *	   {
		 *	      "version":1
		 *	   }
		 *	]
		 */
		
		
		full_address = address + "api/version";
		
		try {
			s = MyHttpURLConnection.get(full_address);
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.d("moje", s);
			

		array = new JSONArray(s);
		jo = array.getJSONObject(0);
		version = Integer.parseInt(jo.get("version").toString());
		
		
		//PYTANIA:
		/*
		[
		   {
		      "pk":3,
		      "model":"qrpolls.question",
		      "fields":{
		         "question_text":"Lubisz mnie?",
		         "poll":2
		      }
		   },
		   {
		      "pk":2,
		      "model":"qrpolls.question",
		      "fields":{
		         "question_text":"Jak sie podoba?",
		         "poll":2
		      }
		   }
		]
		*/
		
		full_address = address + "api/questions";
		
		try {
			s = MyHttpURLConnection.get(full_address);
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
					
		array = new JSONArray(s);
		
		String pk, question_text;
		
		/*
		 * Iteruje po wszystkich pytaniach oraz dodaje je na mape z tymi pytaniami
		 */
		question_map = new HashMap<String,Question>(); //mapa z pytaniami <PK,Questuion>
		
		for(int i =0;i<array.length();i++){
			jo = array.getJSONObject(i);
			pk = jo.get("pk").toString(); //pk - public key
			jo_inside = jo.getJSONObject("fields");
			question_text = jo_inside.get("question_text").toString();
			String rating=jo_inside.get("isRating").toString();
			if(rating.equals("false")){
				question_map.put(pk,new Question(pk, question_text,rating));
			}else{
				ratingID=Integer.parseInt(pk);
			}
			
			
		}
		
		
		//ODPOWIEDZI
		
		/*
		 * [
			   {
			      "fields":{
			         "question":3,
			         "votes":0,
			         "choice_text":"Nie"
			      },
			      "pk":8,
			      "model":"qrpolls.choice"
			   },
			   {
			      "fields":{
			         "question":3,
			         "votes":0,
			         "choice_text":"Tak"
			      },
			      "pk":7,
			      "model":"qrpolls.choice"
			   },
			]
		 */
		
		full_address = address + "api/choices";
		
		try {
			s = MyHttpURLConnection.get(full_address);
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		array = new JSONArray(s);
		
		String question_pk, choice_text;
		int votes;
		
		/*
		 * Iteruje po wszystkich odpowiedzaich przypisanych do danego spotkania oraz przypisuje je do odpowiednich pytan
		 */
		for(int i =0;i<array.length();i++){
			jo = array.getJSONObject(i);
			pk = jo.get("pk").toString();
			jo_inside = jo.getJSONObject("fields");
			question_pk = jo_inside.get("question").toString();
			choice_text = jo_inside.get("choice_text").toString();
			votes = Integer.parseInt(jo_inside.get("votes").toString());
			
			if(!question_pk.equals(ratingID+"")){			//ustawione na sztywno, w wypadku pozniejszych problemow zmienic
			question_map.get(question_pk).addChoice(pk, choice_text, votes);
			}else{
				ratingquestions.add(pk);
			}
		}
					
		
	}
	
	
	public void vote(Choice c){
		String full_address = address+"api/vote/"+c.getPk()+"/";		
		Log.d("moje",full_address);
		try {
			MyHttpURLConnection.get(full_address);
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void vote(String pk){
		String full_address = address+"api/vote/"+pk+"/";		
		Log.d("moje",full_address);
		try {
			String response=MyHttpURLConnection.get(full_address);
			if(response.contains("error\": \"false\"")){
				Toast.makeText(context.getApplicationContext(),"Zaglosowano", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context.getApplicationContext(),"Blad Glosowania", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

	
	
	
	
	
	
	
//	/**
//	 * Porownuje najnowsza wersje na serwerze z obecna.
//	 * @return
//	 */
//	public boolean isCurrentVersion(){
//		JSONParser parser = new JSONParser();
//		String s ="";
//		
//		try {
//			s = MyHttpURLConnection.get(address+"api/version");
//		} catch (Exception404 e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Object obj = null;
//		try {
//			obj = parser.parse(s);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JSONArray array = (JSONArray) obj;
//		JSONObject jo = (JSONObject) array.get(0);
//		
//		int newVersion = Integer.parseInt(jo.get("version").toString());
//		
//		if(this.version==newVersion)
//			return true;
//		else
//			return false;
//	}
//
//	@Override
//	public String toString() {
//		return "Poll [hash_id=" + hash_id + ", subject=" + subject + ", room="
//				+ room + ", start_date=" + start_date + ", version=" + version
//				+ "]";
//	}
//
//	public int getVersion() {
//		return version;
//	}
//
//	public Map<String, Question> getQuestion_map() {
//		return question_map;
//	}
//	
//	
	

	public String getAddress() {
		return address;
	}
	
	public String getHash_id() {
		return hash_id;
	}

	public String getSubject() {
		return subject;
	}

	public String getRoom() {
		return room;
	}

	public String getStart_date() {
		return start_date;
	}

	public int getVersion() {
		return version;
	}

	public Map<String, Question> getQuestion_map() {
		return question_map;
	}
	

}
