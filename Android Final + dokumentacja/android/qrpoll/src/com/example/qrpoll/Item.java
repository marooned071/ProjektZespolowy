package com.example.qrpoll;
/**
 * itemy do wyswietlania w historii
 * @author Sliwka
 *
 */
public class Item{
	private String name;
	private String value;
	private String date;
	private String room;
	public Item(String name,String value,String date,String room){
		this.name=name;
		this.value=value;
		this.date=date;
		this.room=room;
	}
	/**
	 * 
	 * @return nazwa spotkania
	 */
	public String getName(){
		return name;
	}
	/**
	 * 
	 * @return hash spotkania
	 */
	public String getValue(){
		return value;
	}
	/**
	 * 
	 * @return adres gdzie odbywa sie spotkanie
	 */
	public String getRoom(){
		return room;
	}
	/**
	 * 
	 * @return data spotkania
	 */
	public String getDate(){
		return date;
	}
}
