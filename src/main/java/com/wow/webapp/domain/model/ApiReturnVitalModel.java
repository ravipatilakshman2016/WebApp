package com.wow.webapp.domain.model;

import java.util.List;
import com.wow.webapp.domain.account.UserModel;

import com.wow.webapp.domain.pojo.VitalsModel;

public class ApiReturnVitalModel extends ApiReturnModel{

	
	public ApiReturnVitalModel(Integer code,String success,String message) {
		super(code,success,message);
		
	}	
	
	public ApiReturnVitalModel(Integer code,String success,String message,VitalsModel vitalsModel,DoctorModel doctor,UserModel user) {
		super(code,success,message);
		this.vitalsModel = vitalsModel;
		this.doctor = doctor;
		this.user = user;
	}
	
	private VitalsModel vitalsModel;
	
	private DoctorModel doctor;
	private UserModel user;
	public VitalsModel getVitalsModel() {
		return vitalsModel;
	}
	public void setVitalsModel(VitalsModel vitalsModel) {
		this.vitalsModel = vitalsModel;
	}
	public DoctorModel getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorModel doctor) {
		this.doctor = doctor;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	
}
