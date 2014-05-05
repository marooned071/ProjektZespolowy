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
	public String getName(){
		return name;
	}
	public String getValue(){
		return value;
	}
	public String getRoom(){
		return room;
	}
	public String getDate(){
		return date;
	}
}
