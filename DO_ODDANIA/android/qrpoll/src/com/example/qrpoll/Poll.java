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
 * Klasa odpowiedzialna za obsluge ankiety:wywoluje pobranie ze strony, przypisuje odpowiedzi do pytan, wysyla glosy na serwer
 * 
 * @author Piotrek
 * @author Sliwka (poprawki)
 */
public class Poll {


	private String address; 
	private String hash_id;
	private String subject;
	private String room;
	private String start_date;
	private int version;
	private Map<String,Question> question_map;
	private Context context;
	
	public int ratingID=0;
	public List<String> ratingquestions=new ArrayList<String>();
    /**
     * Konstruktor,Przetwarzanie danych zawartych w formacie JSON, laczenie pytan z odpowiedziami
     * @param address adres strony z ktorej chcemy pobrac dane
     * @param context
     * @throws JSONException
     */
	public Poll(String address,Context context) throws JSONException{
		
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		this.address = address;
		this.context = context;
		
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
			jo_inside = jo.getJSONObject("info"); 
			this.hash_id = jo_inside.get("hash_id").toString();
			this.subject = jo_inside.get("subject").toString();
			this.room = jo_inside.get("room").toString();
			this.start_date =  jo_inside.get("time").toString();
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
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
		
		question_map = new HashMap<String,Question>(); 
		for(int i =0;i<array.length();i++){
			jo = array.getJSONObject(i);
			pk = jo.get("pk").toString(); 
			jo_inside = jo.getJSONObject("fields");
			question_text = jo_inside.get("question_text").toString();
			String rating=jo_inside.get("isRating").toString();
			String max=jo_inside.get("question_choices_max").toString();
			if(rating.equals("false")){
				question_map.put(pk,new Question(pk, question_text,rating,max));
			}else{
				ratingID=Integer.parseInt(pk);
			}
			
			
		}
		
		
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
		
		for(int i =0;i<array.length();i++){
			jo = array.getJSONObject(i);
			pk = jo.get("pk").toString();
			jo_inside = jo.getJSONObject("fields");
			question_pk = jo_inside.get("question").toString();
			choice_text = jo_inside.get("choice_text").toString();
			if(!question_pk.equals(ratingID+"")){			
			question_map.get(question_pk).addChoice(pk, choice_text);
			}else{
				ratingquestions.add(pk);
			}
		}
					
		
	}
	
	/**
	 * Metoda odpowiadajaca za glosowanie
	 * @param pk numer odpowiedzi na ktora chcemy zaglosowac
	 */
	public void vote(String pk){
		String full_address = address+"api/vote/"+pk+"/";		
		Log.d("moje",full_address);
		try {
			String response=MyHttpURLConnection.get(full_address);
			if(response.contains("error\": \"false\"")){
				Toast.makeText(context.getApplicationContext(),"Zaglosowano", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context.getApplicationContext(),"Blad, wybrano zbyt wiele odpowiedzi", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception404 e) {
			Toast.makeText(context.getApplicationContext(),"Nie znaleziono strony", Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(),"Blad Polaczenia", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return url spotkania
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return kod hash spotkania
	 */
	public String getHash_id() {
		return hash_id;
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return temat spotkania
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return pokoj, w ktorym odbywa sie spotkanie
	 */
	public String getRoom() {
		return room;
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return poczatek spotkania
	 */
	public String getStart_date() {
		return start_date;
	}
	/**
	 * Dostep do zmiennych prywatnych
	 * @return wersja ankiety
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * Dostep do zmiennych prywatnych, 
	 * @return mapa zawierajaca pytania z pytania
	 */
	public Map<String, Question> getQuestion_map() {
		return question_map;
	}
	

}
