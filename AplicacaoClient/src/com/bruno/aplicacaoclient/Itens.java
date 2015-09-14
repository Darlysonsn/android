package com.bruno.aplicacaoclient;

public class Itens {
	
	 private String title;
	    private int imageUrl;
	    private int inCodLocalizacao;
	     
	    public Itens(String title,int imageUrl, int inCodLocalizacao){
	         
	        this.title = title;
	        this.imageUrl = imageUrl;
	        this.inCodLocalizacao = inCodLocalizacao;
	    }
	    
	    public void setTitle(String title){
	    	
	    	this.title = title;
	    	
	    }
	    
	    public String getTitle(){
	    	
	    	return this.title;
	    	
	    }
	    
	    public void setImageUrl(Integer imageURL){
	    	
	    	this.imageUrl = imageURL;
	    	
	    }
	    
	    public int getImageUrl(){
	    	
	    	return this.imageUrl;
	    	
	    }
	    
	    public void setCodLocalizacao(Integer inCodLocalizacao){
	    	
	    	this.inCodLocalizacao = inCodLocalizacao;	    	
	    }
	    
	    public int getCodLocalizacao(){
	    	
	    	return this.inCodLocalizacao;
	    	
	    }
}
