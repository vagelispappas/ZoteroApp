package com.example.zoteroapp.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.zoteroapp.MainZoteroActivity;
import com.example.zoteroapp.model.Item;

public class JsonParser {
	String doi = null;
	String key = null;
	String title = null;
	String itemType = null;
	String accessDate = null;
	String url = null;
	String date_added = null;
	String children = null;
	String linkMode = null;
	String contentType = null;
	String charset = null;
	String filename = null;
	String abstractNote = null;
	String pages = null;
	String ISSN = null;
	String ISBN = null;
	String publisher = null;
	
	String blog_title = null;
	String short_title = null;
	String date_modified = null;
	
	String f_name;
	String l_name;
	String creator_type;
	String md5;


	public List<Item> parseJSON(String jsonToParse) {
		Log.d(getClass().getCanonicalName(), "parse()");

		List<Item> items = new ArrayList<Item>();
		JSONObject jobj = null;
		JSONArray jArray = null;
		JSONObject data=null;
		Log.i(getClass().getSimpleName(),"the xmlToparse "+jsonToParse);

		try {
			if(MainZoteroActivity.flag<7){

				jobj = new JSONObject(jsonToParse);

				JSONObject subobj = jobj.getJSONObject("data");
				JSONObject subobj2 = jobj.getJSONObject("meta");
				items.add(readJSON(subobj,subobj2));
			}

			if(MainZoteroActivity.flag==7 ){

				//JSONArray jArray = jobj.getJSONArray(xmlToParse);
				jArray = new JSONArray(jsonToParse);
				for(int i=0;i<jArray.length();i++){

					JSONObject obj = jArray.getJSONObject(i);
					data = obj.getJSONObject("data");
					items.add(readJSON(data));
				}
			}




		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getCause();

		}
		return items;
	}

	public Item readJSON(JSONObject data,JSONObject meta) throws JSONException{


		try{
			//JSONObject jobj = new JSONObject();

			//JSONObject subObj = jobj.getJSONObject("data");

			if(data.has("title")){
				title = data.optString("title");
			}
			if(data.has("key")){
				key = data.optString("key");
			}
			if(data.has("itemType")){
				itemType = data.optString("itemType");
			}
			if(data.has("accessDate")){
				accessDate = data.optString("accessDate");
				//accessDate.substring(0, 5);
			}

			if(data.has("date")){
				date_added = data.optString("date");
			}
			else if(data.has("dateAdded")){
				date_added = data.optString("dateAdded");
			}

			if(data.has("url")){
				url = data.optString("url");
			}
			if(data.has("DOI")){
				doi = data.optString("DOI");
			}
			if(data.has("abstractNote")){
				abstractNote = data.optString("abstractNote");
			}
			if(data.has("pages")){
				pages = data.optString("pages");
			}
			if(data.has("ISSN")){
				ISSN = data.optString("ISSN");
			}
			if(data.has("ISBN")){
				ISBN = data.optString("ISBN");
			}
			if(data.has("publisher")){
				publisher = data.optString("publisher");
			}
			/////////////////////////
			if(meta.has("numChildren")){
				children = meta.optString("numChildren");
			}
			///////////////////////////
			if(data.has("contentType")){
				contentType = data.optString("contentType");
			}
			if(data.has("charset")){
				contentType = data.optString("charset");
			}
			if(data.has("linkMode")){
				linkMode = data.optString("linkMode");
			}
			if(data.has("md5")){
				md5 = data.optString("md5");
			}

			if(data.has("tags")){

				Log.d(getClass().getCanonicalName(), "exei tags");

				JSONArray jArray = data.getJSONArray("tags");
				for(int i=0;i<jArray.length();i++){
					@SuppressWarnings("unused")
					JSONObject obj = jArray.getJSONObject(i);				 
				}

			}



			Log.d(getClass().getCanonicalName(),"Title: "+title);
			Log.d(getClass().getCanonicalName(),"Key: "+key);
			Log.d(getClass().getCanonicalName(),"ItemType: "+itemType);
			Log.d(getClass().getCanonicalName(),"DateAdded: "+date_added);
			Log.d(getClass().getCanonicalName(),"URL: "+url);
			Log.d(getClass().getCanonicalName(),"DOI: "+doi);
			Log.d(getClass().getCanonicalName(),"LinkMode: "+linkMode);
			Log.d(getClass().getCanonicalName(),"Contenttype: "+contentType);
			Log.d(getClass().getCanonicalName(),"CharSet: "+charset);
			Log.d(getClass().getCanonicalName(),"FileName: "+filename);
			Log.d(getClass().getCanonicalName(),"AbstactNote: "+abstractNote);
			Log.d(getClass().getCanonicalName(),"md5: "+md5);
			Log.d(getClass().getCanonicalName(),"Number of children: "+children);
			Log.d(getClass().getCanonicalName(),"ISSN: "+ISSN);
			Log.d(getClass().getCanonicalName(),"ISBN: "+ISBN);
			Log.d(getClass().getCanonicalName(),"Number of pages : "+pages);
			Log.d(getClass().getCanonicalName(),"Publisher :"+publisher);

			if(data.has("creators")){
				JSONArray jArray = data.getJSONArray("creators");
				for(int i=0;i<jArray.length();i++){
					JSONObject obj = jArray.getJSONObject(i);

					f_name = obj.optString("firstName");
					l_name = obj.optString("lastName");
					creator_type = obj.optString("creatorType");


					Log.d(getClass().getSimpleName(),"Firstname:  "+f_name);
					Log.d(getClass().getSimpleName(),"Lastname:  "+l_name);
					Log.d(getClass().getSimpleName(),"creator Type:  "+creator_type);
				}

			}

		}catch(NullPointerException e){
			e.printStackTrace();
		}


		return new Item(title , itemType , date_added , key , children , f_name ,l_name, doi , url , linkMode , contentType ,
				charset , filename,abstractNote,ISSN ,ISBN,pages,publisher,blog_title,short_title,date_modified);
	}
	public Item readJSON(JSONObject data) throws JSONException{

		if(data.has("title")){
			title = data.getString("title");
		}
		if(data.has("key")){
			key = data.getString("key");
		}
		if(data.has("itemType")){
			itemType = data.getString("itemType");
		}
		if(data.has("accessDate")){
			accessDate = data.getString("accessDate");
			//accessDate.substring(0, 5);
		}

		if(data.has("date")){
			date_added = data.getString("date");
		}
		else if(data.has("dateAdded")){
			date_added = data.getString("dateAdded");
		}

		if(data.has("date")){
			url = data.getString("url");
		}
		if(data.has("DOI")){
			doi = data.getString("DOI");
		}
		if(data.has("abstractNote")){
			abstractNote = data.getString("abstractNote");
		}
		if(data.has("contentType")){
			contentType = data.getString("contentType");
		}
		if(data.has("charset")){
			contentType = data.getString("charset");
		}
		if(data.has("linkMode")){
			linkMode = data.getString("linkMode");
		}
		if(data.has("md5")){
			md5 = data.getString("md5");
		}
		if(data.has("tags")){

			Log.d(getClass().getCanonicalName(), "exei tags");

			JSONArray jArray = data.getJSONArray("tags");
			for(int i=0;i<jArray.length();i++){
				JSONObject obj = jArray.getJSONObject(i);
				Log.d(getClass().getCanonicalName(), "exei tags: "+obj);
			}

		}



		Log.d(getClass().getCanonicalName(),"Title: "+title);


		Log.d(getClass().getCanonicalName(),"Key: "+key);
		Log.d(getClass().getCanonicalName(),"ItemType: "+itemType);
		Log.d(getClass().getCanonicalName(),"DateAdded: "+date_added);
		Log.d(getClass().getCanonicalName(),"URL: "+url);
		Log.d(getClass().getCanonicalName(),"DOI: "+doi);
		Log.d(getClass().getCanonicalName(),"LinkMode: "+linkMode);
		Log.d(getClass().getCanonicalName(),"Contenttype: "+contentType);
		Log.d(getClass().getCanonicalName(),"CharSet: "+charset);
		Log.d(getClass().getCanonicalName(),"FileName: "+filename);
		Log.d(getClass().getCanonicalName(),"AbstactNote: "+abstractNote);
		Log.d(getClass().getCanonicalName(),"md5: "+md5);
		Log.d(getClass().getCanonicalName(),"Number of children: "+children);

		if(data.has("creators")){
			JSONArray jArray = data.getJSONArray("creators");
			for(int i=0;i<jArray.length();i++){
				JSONObject obj = jArray.getJSONObject(i);

				f_name = obj.getString("firstName");
				l_name = obj.getString("lastName");
				creator_type = obj.getString("creatorType");


				Log.d(getClass().getSimpleName(),"Firstname:  "+f_name);
				Log.d(getClass().getSimpleName(),"Lastname:  "+l_name);
				Log.d(getClass().getSimpleName(),"creator Type:  "+creator_type);
			}
		}




		return new Item(title , itemType , date_added , key , children , f_name ,l_name, doi , url , linkMode , contentType ,
				charset , filename,abstractNote,ISSN,ISBN,pages,publisher,blog_title,short_title,date_modified);
	}

}