package com.example.qrpoll;

public class Choice {
	
	private String pk;
	private String choice_text;
	private int votes;
	
	Choice(String pk, String choice_text, int votes){
		this.pk=pk;
		this.choice_text = choice_text;
		this.votes = votes;
	}

	public String getPk() {
		return pk;
	}

	public String getChoice_text() {
		return choice_text;
	}

	public int getVotes() {
		return votes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((choice_text == null) ? 0 : choice_text.hashCode());
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + votes;
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
		if (votes != other.votes)
			return false;
		return true;
	}

}
