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
/**
 * Klasa do reprezentowania pojedynczej odpowiedzi. Kazde pytanie sklada sie z listy odpowiadajacych mu odpowiedz (czyli obiektow ponizszej klasy)
 * @author Piotrek
 *
 */
public class Choice {
	
	private String pk;
	private String choice_text;
	/**
	 * Konstuktor, ustawia id i tresc odpowiedzi
	 *@param pk Id 
	 *@param choice_text tresc odpowiedzi
	 */
	Choice(String pk, String choice_text){
		this.pk=pk;
		this.choice_text = choice_text;
	}
	/**
	 * @return id odpowiedzi
	 */
	public String getPk() {
		return pk;
	}
	/**
	 * 
	 * @return tresc odpowiedzi
	 */
	public String getChoice_text() {
		return choice_text;
	}
	

	/**
	*metoda obliczajace kod hash
	*/
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((choice_text == null) ? 0 : choice_text.hashCode());
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result ;
		return result;
	}

	
	/**
	*Metoda porownujaca 2 obiekty typu Choice
	*@param obj obiekt z ktorym bedziemy porownywac
	*/
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Choice other = (Choice) obj;
		if (choice_text == null) {
			if (other.choice_text != null)
				return false;
		} else if (!choice_text.equals(other.choice_text))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		
		return true;
	}

}
