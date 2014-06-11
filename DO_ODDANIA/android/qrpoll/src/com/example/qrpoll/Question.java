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


import java.util.HashMap;
import java.util.Map;
/**
 * @author Piotrek
 * @author Sliwka
 * klasa reprezentujaca pojedyńcze pytanie wchodzace w sklad ankiety, posiada mape odpowiedzi  
 */
public class Question {
	
	private String pk;
	private String question_text;
	private String isRating;
	private String max;
	private Map<String,Choice> choice_map;
	/**
	 * glowny konstruktor 
	 * @param pk id pytania
	 * @param question_text
	 * @param isRating zmienna, ktora okresla czy pytanie ma byc wyswietlone czy nie 
	 * @param max maksymalna ilosc odpowiedzi na dane pytanie
	 */
	Question(String pk, String question_text,String isRating,String max){
		this.pk = pk;
		this.question_text = question_text;
		this.isRating=isRating;
		this.max=max;
		this.choice_map = new HashMap<String,Choice>(); //Map<PK,Choice>
	}
	
	/**
	 * dodaje odpowiedz o danej tresci i id do pytania
	 * @param pk id pytania
	 * @param choice_text tresc odpowiedzi
	 */
	public void addChoice(String pk, String choice_text){
		choice_map.put(pk,(new Choice(pk, choice_text)));
	}
	/**
	 * 
	 * @return pk - id pytania
	 */
	public String getPk() {
		return pk;
	}
	/**
	 * Maks to maksymalna ilosc odpowiedzi, ktore moga byc zaznaczone przez uzytkownika
	 */
	public String getMax(){
		return max;
	}
	/**
	 * 
	 * @return
	 */
	public String getQuestion_text() {
		return question_text;
	}

	/**
	 * Metoda ktora zwraca mape zawierajaca odpowiedzi polaczone z danym pytaniem
	 * @return zwraca mape pytan
	 */
	public Map<String, Choice> getChoice_map() {
		return choice_map;
	}
	/**
	 * zwraca informacje, czy dane pytanie odpowiedzialne jest za ocene spotkania i nie bedzie wyswietlane w glownym layoucie 
	 */
	public String isRating(){
		return isRating;
	}
	
	/**
	 * 
	 * @return kod hash pytania
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((choice_map == null) ? 0 : choice_map.hashCode());
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result
				+ ((question_text == null) ? 0 : question_text.hashCode());
		return result;
	}


	/**
	 * porownuje dwa obiekty klasy Question
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (choice_map == null) {
			if (other.choice_map != null)
				return false;
		} else if (!choice_map.equals(other.choice_map))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (question_text == null) {
			if (other.question_text != null)
				return false;
		} else if (!question_text.equals(other.question_text))
			return false;
		return true;
	}


}
