package com.example.zoteroapp.services;

import java.util.List;

import android.net.ParseException;

public abstract class ServerCred<E> {
	
	
	    
	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected XMLResponseParser xmlParser = new XMLResponseParser();
	protected XMLSingleItemParser itemParser = new XMLSingleItemParser();
	protected JsonParser JSONParser = new JsonParser();
	protected JsonCollectionParser JSONCollectrionParser = new JsonCollectionParser();
	public abstract List<E> find(String query) throws ParseException;
	public abstract List<E> find2(String query);
	public abstract List<E> findCollection(String query);
	
	

}
