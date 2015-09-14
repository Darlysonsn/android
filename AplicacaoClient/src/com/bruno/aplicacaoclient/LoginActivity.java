package com.bruno.aplicacaoclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.net.URISyntaxException;


import org.json.JSONException;


public class LoginActivity extends Fragment {

	private AutoCompleteTextView usuarioView;
	private EditText senhaView;
	private Autenticacao autenticacao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		 View view = inflater.inflate(R.layout.activity_login, container, false);

		usuarioView = (AutoCompleteTextView) view.findViewById(R.id.usuario);

		senhaView = (EditText) view.findViewById(R.id.senha);

		final Button loginButton = (Button) view.findViewById(R.id.conectar);
		
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				//Executa thread 
				Runnable runnable = new Runnable() {
			      @Override
			      public void run() {
 
						try {
							autenticacao =  new Autenticacao(getActivity().getApplicationContext());
							autenticacao.setUsuario(usuarioView.getText().toString());
							autenticacao.setSenha(senhaView.getText().toString());
							autenticacao.executaAutenticacao();
					    	
							 Intent intent = new Intent(getActivity().getBaseContext(), NavigationActivity.class );
						     startActivity(intent);
							
						} catch (URISyntaxException e) {
							Log.d("ERRO ", "ERRO"+ e.getMessage());
						} catch (JSONException e) {
							Log.d("JSON ", "JSON ERRO "+ e.getMessage());
						}
			        
			        }
			    };
			    new Thread(runnable).start();
			}
		});
		return view;



	}
	
}
