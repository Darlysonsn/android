package com.bruno.aplicacaoclient;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
 

public class Principal extends FragmentActivity {
    private LoginActivity loginActivity;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        if (savedInstanceState == null) {
        	loginActivity = new LoginActivity();
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, loginActivity).commit();
        } else {
        		loginActivity = (LoginActivity) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }
 
  
}
