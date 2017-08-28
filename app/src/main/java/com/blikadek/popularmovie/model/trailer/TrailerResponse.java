package com.blikadek.popularmovie.model.trailer;

import java.util.List;

public class TrailerResponse {
	private int id;
	private List<TrailerItem> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<TrailerItem> results){
		this.results = results;
	}

	public List<TrailerItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"TrailerResponse{" +
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}