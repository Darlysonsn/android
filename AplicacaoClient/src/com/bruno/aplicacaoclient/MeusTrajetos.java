package com.bruno.aplicacaoclient;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;
 
public class MeusTrajetos extends ActionBarActivity  {
	
     private android.app.Fragment Fragment;
     private PolylineOptions polygonOptions;
     private Context ctx;
     private Toolbar mToolbar;
     protected JSONArray localizacao;
     private LatLng posicaoAnterior;
     private LatLng posicaoAtual;
     private String[] local;
	 private String stUsuario;    

     
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_meus_trajetos);
         
  	    
 		ctx = getApplicationContext();
        iniciaToolBar();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
   	    Fragment = getFragmentManager().findFragmentById(R.id.map);
  		 final MapFragment mapa = ((MapFragment) Fragment);
 		 mapa.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
 		 
 		 carregaMapa(mapa.getMap());
     }
     
     private void iniciaToolBar() {
 	    mToolbar = (Toolbar) findViewById(R.id.tool_bar);
 	    setSupportActionBar(mToolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 	    setTitle("Trajeto");
 	    mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
 	  }
 
     private boolean carregaMapa(GoogleMap map){
 	   	  
 	  	 try {

		    Intent i = getIntent();
	  	    int inCodLocalizacao = i.getIntExtra("cod_localizacao",0);
	 	  		
	 	    Preferencias preferencias =  new Preferencias (ctx);
	            stUsuario     = preferencias.lePreferencias("usuario");
 		    localizacao = new APIClientTask(ctx).execute("localizacao/usuario?usuario="+stUsuario+"&localizacao="+inCodLocalizacao,"ler").get();
	 			
	 	    if (localizacao.length() == 0 ){
	 		Toast.makeText(ctx, "Não há dados para este trajeto", Toast.LENGTH_LONG).show();
				return false;
 			}
	 		} catch (InterruptedException e) {
	 			e.printStackTrace();
	 		} catch (ExecutionException e) {
	 			e.printStackTrace();
	 		}
	     	
	     	int j=0;
	 	    for (int i=0; i< localizacao.length()-1; i++){
	 	    	 
	 	    	j = i +1;
				try {
					local = localizacao.getJSONObject(i).getString("localizacao").split(",");
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
	 	    	Double latitude       = Utils.getLocalizacaoFormatada(local[0]);
	 	    	Double longitude      = Utils.getLocalizacaoFormatada(local[1]);

	 	    	polygonOptions = new PolylineOptions();
		 	   	polygonOptions.color(Color.RED);
		 	   	polygonOptions.width(7);
		 	   	 		   	  
	 	    	
	    		posicaoAnterior = new  LatLng(latitude, longitude);
	    		
	    		if (i==0){
					 map.addMarker(new MarkerOptions()
		 	        .position(posicaoAnterior)
		 	        .title("Início"));
				}
	    		
	    		try {
					local = localizacao.getJSONObject(j).getString("localizacao").split(",");
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    		
	    	  	latitude       = Utils.getLocalizacaoFormatada(local[0]);
	 	    	longitude      = Utils.getLocalizacaoFormatada(local[1]);

	    		posicaoAtual    = new  LatLng(latitude, longitude);
	    		
	    		
	 	   		  polygonOptions.add(posicaoAnterior, posicaoAtual);
	 	   		  map.addPolyline(polygonOptions);
	 	   		  
		    	if (i==(localizacao.length()-2) ){
						 map.addMarker(new MarkerOptions()
			 	        .position(posicaoAtual)
			 	        .title("Fim"));
		    	}
	 	   		  
	 	   		
	 	   	  }
	 	    

       		   map.moveCamera(CameraUpdateFactory.newLatLngZoom(posicaoAtual,17));
       		   map.animateCamera(CameraUpdateFactory.newLatLng(posicaoAtual));
       		   
       		   return true;
	    }
 
   
}
