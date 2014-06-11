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
 * Klasa reprezentujaca obiekt, ktory bedzie zapisywany w bazie sql, posiadajacy 4 pola: temat, hash,data i pokoj
 * @author Sliwka
 *
 */
public class Item{
	private String name;
	private String value;
	private String date;
	private String room;
	/**
	*konstruktor, ustawia wszyskie wartosci wszystkich pol na podane przez aplikacje 
	*@param temat spotkania
	*@param hash spotkania
	*@param data spotkania
	*@param pomieszczenie, gdzie odbywa sie spotkanie
	*/
	public Item(String name,String value,String date,String room){
		this.name=name;
		this.value=value;
		this.date=date;
		this.room=room;
	}
	/**
	 * dostep do zmiennych prywatnych
	 * @return nazwa spotkania
	 */
	public String getName(){
		return name;
	}
	/**
	 * dostep do zmiennych prywatnych
	 * @return hash spotkania
	 */
	public String getValue(){
		return value;
	}
	/**
	 * dostep do zmiennych prywatnych
	 * @return adres gdzie odbywa sie spotkanie
	 */
	public String getRoom(){
		return room;
	}
	/**
	 * dostep do zmiennych prywatnych
	 * @return data spotkania
	 */
	public String getDate(){
		return date;
	}
}
