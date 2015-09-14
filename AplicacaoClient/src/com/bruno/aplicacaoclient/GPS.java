package com.bruno.aplicacaoclient;

import java.net.URISyntaxException;
import org.json.JSONException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class GPS  implements LocationListener {
	
	
    protected Context context;
    protected LocationManager locationManager;
    private static boolean GPSEnabled = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0; 
    protected Location location;
    double latitude; 
    double longitude;
    private GoogleMap map;
    private PolylineOptions polygonOptions;
    private LatLng posicaoAnterior;
    private Localizacao obLocalizacao;
    
    public GPS(LocationManager locationManagerGPS){
    	
    	this.locationManager = locationManagerGPS;
    	
    	
    }
    protected boolean configuraGPS() {
   	    	
    	try {
                        
        GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        
        if(!GPSEnabled) {
        		return false;
              
        }
        
    	} catch (Exception e)  {
    		
             Toast.makeText(context, "ERRO:"+e.getMessage(), Toast.LENGTH_LONG).show();
             }
    		return true;
    	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;

			Runnable runnable = new Runnable() {
			      @Override
			      public void run() {

						try {
							  obLocalizacao =  new Localizacao();
							  obLocalizacao.setLatitude(getLatitude());
							  obLocalizacao.setLongitude(getLongitude());

							  
							  APIClient apiClient =  new APIClient(context);
							  apiClient.setLocalizacao(obLocalizacao);
							  apiClient.criar();
							  
						} catch (URISyntaxException e) {
							
							Log.d("ERRO ", "ERRO"+ e.getMessage());
						} catch (JSONException e) {
							Log.d("JSON ", "JSON ERRO "+ e.getMessage());
						}
			        
			        }
			    };
			    new Thread(runnable).start();
		 
		
	          LatLng posicaoAtual = new LatLng(getLatitude(),getLongitude());
	        
	  	      polygonOptions = new PolylineOptions();
	 	   	  polygonOptions.color(Color.RED);
	 	   	  polygonOptions.width(7);
		   	  
	
	 	   	  polygonOptions.add(posicaoAnterior, posicaoAtual);
	 	      map.addPolyline(polygonOptions);
	 		 	      
	 	 	  map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getLatitude(),getLongitude()),map.getCameraPosition().zoom));
      		  map.animateCamera(CameraUpdateFactory.newLatLng(posicaoAtual));

	       
	 	      posicaoAnterior = posicaoAtual;
 	   	  
		
 	 
	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}

	
	protected Location buscaLocalizacao(){
		
					
				if (GPSEnabled) { 
								   
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					  
					
					if (locationManager != null)
	 		    	{
	 		    		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	 		    			 		    		
	 		    		if (location != null)
	 				    {
	 		    	    	
	 		    	    	Log.d("LOCALIZACAO GPS", " ENTRA AQUI LOCALIZACAO POR GPS : " + location);
	 		    	    	latitude = location.getLatitude();
	 		    	    	longitude = location.getLongitude();
	 		    	    	
	 		    	    	
	 				    }
	 		    		
	 		    		posicaoAnterior = new  LatLng(latitude, longitude);
	 		    		
	 		    		
			    	}				
				}
								
				return location;
				
				
	}
	

	protected void paraGPS(){
        

		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
		
	}
	
	 protected double getLatitude()
	    {
		if (location != null)
		{
		    latitude = location.getLatitude();
		}
		return latitude;
	    }

	  protected double getLongitude()
	    {
		if (location != null)
		{
		    longitude = location.getLongitude();
		}
		return longitude;
	    }

	  	public void setMapa (GoogleMap map) {
	  		
	  		this.map = map;
	  	}
	  	public GoogleMap getMapa () {
	  		
	  		return this.map;
	  	}
	  	protected void setContext (Context context) {
	  		
	  		this.context = context;
	  	}
	  	protected Context getContext() {
	  		
	  		return this.context;
	  	}
	
	}
