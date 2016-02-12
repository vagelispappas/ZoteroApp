package com.example.zoteroapp.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.zoteroapp.model.Item;

public class XMLJsonParser {

	public List<Item> parseJSON(String xmlToParse) {
		Log.d(getClass().getCanonicalName(), "parse()");
		
		List<Item> items = new ArrayList<Item>();
		JSONObject jobj;
		Log.i(getClass().getSimpleName(),"the xmlToparse "+xmlToParse);
		try {
			
			jobj = new JSONObject(xmlToParse);
			
			JSONObject subobj = jobj.getJSONObject("data");
			
			items.add(readJSON(subobj));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	public Item readJSON(JSONObject data) throws JSONException{
		String key = null;
		String title = null;
		String itemType = null;
		String accessDate = null;
		String children = null;
		try{
			//JSONObject jobj = new JSONObject();

			//JSONObject subObj = jobj.getJSONObject("data");

			key = data.getString("key");
			title = data.getString("title");
			itemType = data.getString("itemType");
			accessDate = data.getString("accessDate");
			children = null;
			accessDate.substring(0, 5);

			Log.d(getClass().getCanonicalName(),"Title: "+title);
			Log.d(getClass().getCanonicalName(),"Key: "+key);
			Log.d(getClass().getCanonicalName(),"ItemType: "+itemType);
			Log.d(getClass().getCanonicalName(),"AccessDate: "+accessDate.substring(0, 4));

			JSONArray jArray = data.getJSONArray("creators");
			for(int i=0;i<jArray.length();i++){
				JSONObject obj = jArray.getJSONObject(i);

				String f_name = obj.getString("firstName");
				String l_name = obj.getString("lastName");
				String c_type = obj.getString("creatorType");


				Log.d(getClass().getSimpleName(),"Firstname:  "+f_name);
				Log.d(getClass().getSimpleName(),"Lastname:  "+l_name);
				Log.d(getClass().getSimpleName(),"creator Type:  "+c_type);
			}



		}catch(NullPointerException e){
			e.printStackTrace();
		}


		return new Item(title,accessDate,key,itemType,children,null,null);
	}

}