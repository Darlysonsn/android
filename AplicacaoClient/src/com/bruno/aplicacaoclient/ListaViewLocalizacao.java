package com.bruno.aplicacaoclient;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class ListaViewLocalizacao extends ActionBarActivity { 

	  private Toolbar mToolbar;
	  protected APIClient  apiClient;
	  private RecyclerView recyclerView;
	  protected JSONArray localizacao;
	  private String stUsuario;    
      Context context;

     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

      
             setContentView(R.layout.visualiza_localizacoes);

             context = getBaseContext();
	         
             iniciaToolBar();
             
             recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
             
             buscaLocalizacoes();
             recyclerView.setLayoutManager(new LinearLayoutManager(this));
             
         }
     
     private void iniciaToolBar() {
    	    mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    	    setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	    setTitle("Meus trajetos");
    	    mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    	  }
     
     private void buscaLocalizacoes() {
     
	    	Preferencias preferencias =  new Preferencias (context);
        	stUsuario     = preferencias.lePreferencias("usuario");
	    	
	    	
    	 try {
			localizacao = new APIClientTask(context).execute("localizacao/usuario?usuario="+stUsuario,"ler").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	 preencheLista();
	    
	    
     }
     
     private void preencheLista(){
    
	      Itens itemsData[] = new Itens[localizacao.length()];
	      for (int i=0; i< itemsData.length; i++){
	    	  try {
				itemsData[i] = new Itens("LOCALIZACAO "+localizacao.getJSONObject(i).getString("cod_localizacao"), R.drawable.localizacao, localizacao.getJSONObject(i).getInt("cod_localizacao"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	      }
		  
		  Adapter mAdapter = new Adapter(context, itemsData);
	      recyclerView.setAdapter(mAdapter);
	      recyclerView.setItemAnimator(new DefaultItemAnimator());

     }
	         
 }