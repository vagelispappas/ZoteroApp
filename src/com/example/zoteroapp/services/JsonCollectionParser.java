package com.example.zoteroapp.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.zoteroapp.model.Item;



public class JsonCollectionParser {

	String numCollection=null;
	String numItems=null;

	String key=null;
	String name=null;
	String parentCollection=null;
	
	public JsonCollectionParser() {
		// TODO Auto-generated constructor stub
	}

	public List<Item> parseCollJson(String jsonToParse) throws JSONException , NullPointerException{

		List<Item> items = new ArrayList<Item>();
		
		
		JSONObject meta = null;
		JSONObject data = null;
		Log.i(getClass().getCanonicalName(),"the JsonCollToparse "+jsonToParse);
		JSONArray array = null;
		try{
			
				array = new JSONArray(jsonToParse);
				
				for(int i =0;i<array.length();i++){
					JSONObject info = array.getJSONObject(i);
					
					 meta = info.getJSONObject("meta");
					 data = info.getJSONObject("data");
					 
					 Log.i(getClass().getCanonicalName(),"the meta :"+meta);
					 Log.i(getClass().getCanonicalName(),"the data :"+data);
					 
					 name = data.optString("name");
					 Log.i(getClass().getCanonicalName(),"name of collection :"+name);
					 items.add(readJSON(meta, data)); 										
				}
				 Log.i(getClass().getCanonicalName(),"the items list :"+items);
			
		}catch(JSONException e){
			
			e.getCause();
			
		}
		return items;
		
	}

	public Item readJSON(JSONObject meta , JSONObject data) throws JSONException {
		
		 Log.i(getClass().getCanonicalName(),"readJSON()");
		 
		Log.i(getClass().getCanonicalName(),"readJSON()  try ");
		if(meta.has("numCollections")){
			
			numCollection = meta.optString("numCollections");
			
			Log.i(getClass().getCanonicalName(),"Number Of collections :"+numCollection.toString());
		}
		else{
			Log.i(getClass().getCanonicalName(),"can not find Number Of collections :");

		}

		if(data.has("key")){
			key = data.optString("key");
			Log.i(getClass().getCanonicalName(),"Item key :"+key);
		}
		if(data.has("name")){
			name = data.optString("name");
			Log.i(getClass().getCanonicalName(),"Name Of collection :"+name);
		}
		if(data.has("parentCollection")  ){
			parentCollection = data.optString("parentCollection");
			Log.i(getClass().getCanonicalName(),"ParentCollection key :"+parentCollection);
		}

		return new Item(name , key, numCollection,parentCollection);
	}

}
