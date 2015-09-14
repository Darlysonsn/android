package com.bruno.aplicacaoclient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

class APIClientTask extends AsyncTask<String, Context, JSONArray> {
	  JSONArray localizacao = new JSONArray();
	  Context mContext = null;
	  private Conexao conexao;
	  public APIClientTask (Context context){
	         mContext = context;
	    }
	@Override
    protected JSONArray doInBackground(String... recurso) {
		
    	  
		APIClient apiClient =  new APIClient(mContext);
		conexao =  new Conexao(mContext);
		if (!conexao.verificaConexaoDisponivel()) {
				return localizacao;	
		}
		
	    String url;
		try {
			url = URLDecoder.decode(recurso[0], "ISO-8859-1");
			if (recurso[1] == "usuario"){
				localizacao  = apiClient.usuario(url);
			} else if (recurso[1] == "ler"){
				localizacao  = apiClient.ler(url);
			}
			return localizacao;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
    	  
       
	return null;
     

	
    } 
   
    
}
