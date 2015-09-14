package com.bruno.aplicacaoclient;


public class Localizacao {
	
	protected Double dbLatitude;
	protected Double dbLongitude;
	
	public void setLatitude(Double dbLatitude ){
		this.dbLatitude  =  dbLatitude;
	}
	public Double getLatitude(){
		return this.dbLatitude;
	}
	
	public void setLongitude(Double dbLongitude){
		this.dbLongitude=  dbLongitude;
	}
	public Double getLongitude(){
		return this.dbLongitude;
	}

}
