package com.bruno.aplicacaoclient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conexao {

	
	private Context context;
	
	public Conexao(Context context){
		
		this.context = context;
		
	}
		
	protected boolean verificaConexaoDisponivel() {
		
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		boolean isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = networkInfo.isConnected();
			
		if (isMobileConn || isWifiConn ) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
