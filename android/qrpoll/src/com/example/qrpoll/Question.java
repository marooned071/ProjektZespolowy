package com.example.qrpoll;


import java.util.HashMap;
import java.util.Map;

public class Question {
	
	private String pk;
	private String question_text;
	private Map<String,Choice> choice_map;
	
	Question(String pk, String question_text){
		this.pk = pk;
		this.question_text = question_text;
		this.choice_map = new HashMap<String,Choice>(); //Map<PK,Choice>
	}
	
	
	public void addChoice(String pk, String choice_text, int votes){
		choice_map.put(pk,(new Choice(pk, choice_text, votes)));
	}

	public String getPk() {
		return pk;
	}

	public String getQuestion_text() {
		return question_text;
	}


	public Map<String, Choice> getChoice_map() {
		return choice_map;
	}


	@Override
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


	@Override
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
