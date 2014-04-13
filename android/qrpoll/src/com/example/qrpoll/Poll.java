package qrpolls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Klasa odpowiedzialna za obsluge ankiety.
 * 
 * @author Piotrek
 * 
 */
public class Poll {

	public static void main(String[] args) {
		Poll p = null;
		try {
			p =new Poll("http://loony-waters-2513.herokuapp.com/qrpolls/meeting/b9ffd9db1af0b14ed74e92e8d3273f3e/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.toString());
	}
	
	

	private String address; // pelen adres strony
	private String hash_id;
	private String subject;
	private String room;
	private String start_date;
	private int version;
	private Map<String,Question> question_map;
	
	

	Poll(String address) throws ParseException {
		this.address = address;
	
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

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(s);
		JSONArray array = (JSONArray) obj;
		JSONObject jo = (JSONObject) array.get(0);
		JSONObject jo_inside = (JSONObject) jo.get("info"); //inside bo ten obiekt jest zagniezdzony 
		
		this.hash_id = jo_inside.get("hash_id").toString();
		this.subject = jo_inside.get("subject").toString();
		this.room = jo_inside.get("room").toString();
		this.start_date =  jo_inside.get("start_date").toString();
		
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
		
		obj = parser.parse(s);
		array = (JSONArray) obj;
		jo = (JSONObject) array.get(0);
		
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
		
		obj = parser.parse(s);
		array = (JSONArray) obj;
		
		question_map = new HashMap<String,Question>(); //mapa z pytaniami <PK,Questuion>
		String pk, question_text;
		Iterator it;	
		it = array.iterator();
		
		/*
		 * Iteruje po wszystkich pytaniach oraz dodaje je na mape z tymi pytaniami
		 */
		while(it.hasNext()){
			jo = (JSONObject) it.next();
			pk = jo.get("pk").toString(); //pk - public key
			jo_inside = (JSONObject) jo.get("fields");
			question_text = jo_inside.get("question_text").toString();
			question_map.put(pk,new Question(pk, question_text));
			
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
		
		obj = parser.parse(s);
		array = (JSONArray) obj;
		
		it = array.iterator();
		String question_pk, choice_text;
		int votes;
		
		/*
		 * Iteruje po wszystkich odpowiedzaich przypisanych do danego spotkania oraz przypisuje je do odpowiednich pytan
		 */
		while(it.hasNext()){
			jo = (JSONObject) it.next();
			pk = jo.get("pk").toString();
			jo_inside = (JSONObject) jo.get("fields");
			question_pk = jo_inside.get("question").toString();
			choice_text = jo_inside.get("choice_text").toString();
			votes = Integer.parseInt(jo_inside.get("votes").toString());
			question_map.get(question_pk).addChoice(pk, choice_text, votes);
		}
		

			
		
	}
	
	/**
	 * Porownuje najnowsza wersje na serwerze z obecna.
	 * @return
	 */
	public boolean isCurrentVersion(){
		JSONParser parser = new JSONParser();
		String s ="";
		
		try {
			s = MyHttpURLConnection.get(address+"api/version");
		} catch (Exception404 e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object obj = null;
		try {
			obj = parser.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray array = (JSONArray) obj;
		JSONObject jo = (JSONObject) array.get(0);
		
		int newVersion = Integer.parseInt(jo.get("version").toString());
		
		if(this.version==newVersion)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "Poll [hash_id=" + hash_id + ", subject=" + subject + ", room="
				+ room + ", start_date=" + start_date + ", version=" + version
				+ "]";
	}

	public int getVersion() {
		return version;
	}

	public Map<String, Question> getQuestion_map() {
		return question_map;
	}
	
	
	

	
	

}
