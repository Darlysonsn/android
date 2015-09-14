package com.bruno.aplicacaoclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class APIClient {

	
	private String endereco;
	private String stAutorizacao;
	private String stUsuario;
	private Context context;
	private JSONArray localizacao;
	private Localizacao obLocalizacao;

	public APIClient (Context context) { 
		this.context = context;
		this.endereco = "192.168.1.5";
		localizacao  =  new JSONArray();
		obLocalizacao  =  new Localizacao();
	}
	
	protected void setLocalizacao (Localizacao localizacao) {
		this.obLocalizacao = localizacao;
	}
	protected Localizacao getLocalizacao () {
		return this.obLocalizacao;
	}
	
	protected boolean criar() throws URISyntaxException, JSONException {
		
		try { 
		
	    	Preferencias preferencias =  new Preferencias (context);
	    	stAutorizacao = preferencias.lePreferencias("access_token");
	    	stUsuario     = preferencias.lePreferencias("usuario");
	    	
	    	
	    	localizacao = verificaLocalizacao("api/localizacao/usuario?usuario=");
	    	
	    	String recurso = "localizacao/1/usuario/1";

   		    String url = "http://"+endereco+":9000/api/"+recurso;
	    	
			MultipartEntityBuilder entity = MultipartEntityBuilder.create();
			entity.addTextBody("cod_localizacao", localizacao.getJSONArray(0).toString());
			entity.addTextBody("iniciada", "true");
			entity.addTextBody("cod_usuario", stUsuario);
			entity.addTextBody("localizacao", "("+getLocalizacao().dbLatitude+","+this.getLocalizacao().dbLongitude+")");

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity.build());

			httpPost.setHeader("Authorization", "Bearer "+stAutorizacao);
		    httpPost.setHeader("Accept","application/json");

	
			HttpClient httpCliente = new DefaultHttpClient();			
			httpCliente.execute(httpPost);
		} 
		catch (IOException e) {
		    Log.d("Ocorreu erro", e.toString());	
		}
		return false;
	}
	
	protected JSONArray ler(String recurso) throws URISyntaxException, JSONException {
		
		try { 
			
			String url = "http://"+endereco+":9000/api/"+recurso;
			
			HttpClient httpCliente = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept","*/*");
			//Le token 
	    	Preferencias preferencias =  new Preferencias (context);
	    	stAutorizacao = preferencias.lePreferencias("access_token");
    	
			httpGet.setHeader("Authorization", "Bearer "+stAutorizacao);
			HttpResponse response  = httpCliente.execute(httpGet);
			
			HttpEntity entity = response.getEntity();			
		    if (entity != null) {
		    	InputStream imInstream = entity.getContent();
		    	String stJson  = Utils.getStringFromInputStream(imInstream);
		    	JSONObject obJson = new  JSONObject(stJson).getJSONObject("_embedded");
		    	localizacao = obJson.getJSONArray("localizacao");
		      	return localizacao;
		    }
		        
		} 
		catch (IOException e) {
		    Log.d("Ocorreu erro", e.toString());	
		}
		return localizacao;
	}
	
	protected JSONArray verificaLocalizacao(String recurso) throws URISyntaxException, JSONException {
		
		try { 
			
			Preferencias preferencias =  new Preferencias (context);
	    	stAutorizacao = preferencias.lePreferencias("access_token");
	    	stUsuario     = preferencias.lePreferencias("usuario");
    	
	    	
			String url = "http://"+endereco+":9000/"+recurso+stUsuario+"&verifica=aberta";
			HttpClient httpCliente = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept","*/*");
			httpGet.setHeader("Authorization", "Bearer "+stAutorizacao);
			HttpResponse response  = httpCliente.execute(httpGet);
			
			HttpEntity entity = response.getEntity();			
		    if (entity != null) {
		    	InputStream imInstream = entity.getContent();
		    	String stJson  = Utils.getStringFromInputStream(imInstream);
		    	JSONObject obJson = new  JSONObject(stJson).getJSONObject("_embedded");
		    	
		    	localizacao = obJson.getJSONArray("localizacao");
		    	return localizacao;
		    	
		    }

		        
		} 
		catch (IOException e) {
		    Log.d("Ocorreu erro", e.toString());	
		}
		return localizacao;
	}
	
	protected JSONArray usuario(String recurso) throws URISyntaxException, JSONException {
		
		try { 
			
			String url = "http://"+endereco+":9000/api/"+recurso;
			
			HttpClient httpCliente = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept","*/*");

	    	Preferencias preferencias =  new Preferencias (context);
	    	stAutorizacao = preferencias.lePreferencias("access_token");
    	
			httpGet.setHeader("Authorization", "Bearer "+stAutorizacao);
			
			HttpResponse response  = httpCliente.execute(httpGet);
			
			HttpEntity entity = response.getEntity();			
		    if (entity != null) {
		    	InputStream imInstream = entity.getContent();
		    	String stJson  = Utils.getStringFromInputStream(imInstream);
		    	JSONObject obJson = new  JSONObject(stJson);
		    	localizacao.put(0, Integer.valueOf(obJson.getString("cod_usuario")));
		      	return localizacao;
	            
		    	
		    }
		        
		} 
		catch (IOException e) {
		    Log.d("Ocorreu erro", e.toString());	
		}
		return localizacao;
	}


	
	
	
}
