package com.bruno.aplicacaoclient;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

	protected Context context;
	
	public Preferencias ( Context context) {
	
		this.context= context;
	}
	
	public void gravarPreferencias(String preferencia, String valor){
		
		SharedPreferences sharedPref = context.getSharedPreferences(preferencia, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(preferencia, valor);
		editor.commit();
		
	}
	
	public String lePreferencias(String preferencia){
		
		SharedPreferences sharedPref = context.getSharedPreferences(preferencia, Context.MODE_PRIVATE);
		
		return sharedPref.getString(preferencia, ""); 
		
		
	}

}
