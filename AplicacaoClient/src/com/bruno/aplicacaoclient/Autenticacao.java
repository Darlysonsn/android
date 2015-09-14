package com.bruno.aplicacaoclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Base64;
import android.util.Log;


	public class Autenticacao{
		
		
		private String usuario;
		private String senha;
		private String endereco;
		private Context context;
				
		Autenticacao(Context context) { 
			this.context = context;
			this.endereco = "192.168.1.5";
		}
		
		public void setUsuario(String usuario){
			this.usuario = usuario;
		}
		public String getUsuario(){
				return this.usuario;
		}
		

		public void setSenha(String senha){
			this.senha = senha;
		}
		public String getSenha(){
				return this.senha;
		}

		public boolean executaAutenticacao() throws URISyntaxException, JSONException {
		try { 
			URI httpURI =  new URI("http", "", endereco, 9000, "/oauth", "", "");		
			HttpClient httpCliente = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(httpURI);
			String stringAutorizacao = "Basic " + Base64.encodeToString(
	                        (getUsuario() + ":" + getSenha()).getBytes(),
	                        Base64.NO_WRAP); 
			httpPost.setHeader("Authorization", stringAutorizacao);
			List<NameValuePair> httpParametros = new ArrayList<NameValuePair>();
			httpParametros.add(new BasicNameValuePair("grant_type", "client_credentials"));
			httpPost.setEntity(new UrlEncodedFormEntity(httpParametros));
			HttpResponse response  = httpCliente.execute(httpPost);
			HttpEntity entity = response.getEntity();			
              
			if (entity != null) {
			   	InputStream imInstream = entity.getContent();
				String stringJson  = Utils.getStringFromInputStream(imInstream);
			    	JSONObject obJson = new  JSONObject(stringJson);
			    	imInstream.close();
	 			try {
 				     String stringToken = obJson.getString("access_token");
			    	Preferencias preferencias =  new Preferencias (context);
					    		preferencias.gravarPreferencias("access_token", stringToken);
					    		
								APIClientTask apiClientTask = new APIClientTask(context);
								JSONArray localizacao = apiClientTask.execute("usuario/"+getUsuario(),"usuario").get();
					    		preferencias.gravarPreferencias("usuario", localizacao.getString(0));
				    		
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}

				    		
				    	}
				
				        
				} 
				catch (IOException e) {
				    Log.d("Ocorreu erro", e.toString());	
				}
				return false;
		}
		
		
	}


	
