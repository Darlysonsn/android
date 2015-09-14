package com.bruno.aplicacaoclient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
 
public class MinhaLocalizacao extends ActionBarActivity  {
	
     private LatLng LocalizacaoAtual;
     private android.app.Fragment Fragment;
     private GPS GPS;
     private Button btnIniciar;
     private Button btnFechar;
     private Context ctx;
     private Toolbar toolbar; 
     protected LocationManager locationManager;
     
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.fragment_layout_principal);
  
 	     ctx = getApplicationContext();
 		
         locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
         
    	 toolbar = (Toolbar) findViewById(R.id.tool_bar);
         
         setSupportActionBar(toolbar);
         getSupportActionBar().setIcon(R.drawable.ic_launcher);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	    GPS =  new GPS(locationManager);
        GPS.setContext(ctx);
        GPS.paraGPS();
        
     	btnIniciar  = (Button) findViewById(R.id.btnIniciar);
     	btnFechar   = (Button) findViewById(R.id.btnParar);
        
        Fragment = getFragmentManager().findFragmentById(R.id.map);
  	    final MapFragment mapa = ((MapFragment) Fragment);
 	    mapa.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);

   		btnIniciar.setOnClickListener(new OnClickListener()
   	      {
   	                @Override
   	                public void onClick(View v)
   	                {
   	                	
   	                	mapa.getMap().clear();
   	              		GPS.setMapa(mapa.getMap());
   	                	GPS.getMapa().setMyLocationEnabled(true);
   	 				   
   	                   if (!GPS.configuraGPS()) { 
   	           			   mostraStatusGPS();
   	           		   }
   	           		   
   	           	       GPS.buscaLocalizacao();
   	           	       
   	           		   LocalizacaoAtual =  new LatLng(GPS.getLatitude(),GPS.getLongitude());

   	           		   GPS.getMapa().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(GPS.getLatitude(),GPS.getLongitude()),18));
   	           		   GPS.getMapa().animateCamera(CameraUpdateFactory.newLatLng(LocalizacaoAtual));


   	                }
   	      });



   		btnFechar.setOnClickListener(new OnClickListener()
   	      {
   	                @Override
   	                public void onClick(View v)
   	                {
   	                   	 				   
   	                   if (!GPS.configuraGPS()) { 
   	           			   mostraStatusGPS();
   	           		   }
   	           		   	           	       
   	           		   LocalizacaoAtual =  new LatLng(GPS.getLatitude(),GPS.getLongitude());

   	           		   GPS.getMapa().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(GPS.getLatitude(),GPS.getLongitude()),18));
   	           		   GPS.getMapa().animateCamera(CameraUpdateFactory.newLatLng(LocalizacaoAtual));
	                   GPS.paraGPS();

   	                }
   	      });

		 
  
     }
      @Override
      public void onResume(){
    	  	super.onResume();
      }
      
      @Override
      public void onPause(){
    	  	super.onPause();
	   	    GPS.paraGPS();
      }
      
   
		    
	    private void mostraStatusGPS(){
			  
			  
		        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MinhaLocalizacao.this);
		        alertDialogBuilder.setMessage("GPS desabilitado no seu dispositivo. Gostaria de habilita-lo?")
		        .setCancelable(false)
		        .setPositiveButton("IR para configuração do GPS",
		                new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface dialog, int id){
		                Intent callGPSSettingIntent = new Intent(
		                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		                startActivity(callGPSSettingIntent);
		            }
		        });
		        alertDialogBuilder.setNegativeButton("Cancelar",
		                new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface dialog, int id){
		                dialog.cancel();
		            }
		        });
		        AlertDialog alert = alertDialogBuilder.create(); 
		        alert.show();
		    }
	    
	    
    
}
