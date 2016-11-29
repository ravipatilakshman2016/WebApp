package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModelLab extends ApiReturnModel {

private List<Object> results = new ArrayList<Object>();

	

	public List<Object> getResults() {
		return results;
	}



	public void setResults(List<Object> results) {
		this.results = results;
	}



	public ApiReturnModelLab(Integer code,String success,String message,List<Object> results) {
		super(code,success,message);
		this.results = results;
	}
	
}
