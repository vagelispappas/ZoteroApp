package com.example.zoteroapp.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import android.net.ParseException;
import android.util.Log;

import com.example.zoteroapp.MainZoteroActivity;
import com.example.zoteroapp.m_screen;
import com.example.zoteroapp.model.Item;

public class ItemSeeker extends ServerCred<Item> {
	ServerCred<Item> cred;
	List<Item> itemList=null;
	List<Item> itemList2;
	public String response;
	public String url;
	
	public List<Item> find(String query) throws ParseException{
		Log.d(getClass().getName(), "find()");

		try {
			itemList = retrieveItemList(query);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return itemList;

	}
	public List<Item>find2(String query){
		
		Log.d(getClass().getName(), "find2()");

		try {
			
				try {
					itemList2 = retrieveItem(query);
				} catch (org.apache.http.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return itemList2;
	}
	
	public List<Item> findCollection(String query){
		
		Log.e(getClass().getCanonicalName(), "findCollection()");
		
		try {
			itemList = retrieveCollection(query);
		}
		catch (NullPointerException | JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return itemList;
		
	}
	public List<Item> retrieveItemList(String query) throws ParseException, XmlPullParserException, IOException{
		//InputStream response =null;
		//String response;
		//String url;
		Log.d(getClass().getName(), "retrieveItemList");
		try{
			
			if(/*MainZoteroActivity.flag==1*/MainZoteroActivity.flag==0){
				//constructs the URL query
				url = "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/collections/top?format=atom";
				
			}
			
			else if(MainZoteroActivity.flag==3){
				url = m_screen.on_click_URL;
			}
			
			else if(MainZoteroActivity.flag==6){
				url=query;
			}
			
			else if(MainZoteroActivity.flag==8){
				//url = "https://api.zotero.org/users/1737513/items?key=KLhSEwv3q04CyXOUnzqeOKND";
				Log.d(getClass().getSimpleName()," Flag "+MainZoteroActivity.flag);
				url = query;

			}

			//cred.constructURL(query);
			Log.d(getClass().getSimpleName(), url);
			//executes HTTP request using HTTP retriever
			// response = httpRetriever.retrieveStream(url);
			response = httpRetriever.retrieve(url);

		}catch(NullPointerException e){
			e.printStackTrace();
		}
		//if it is successful the XML response feeded to the parser
		return xmlParser.parse(response);
	}
	
	public List<Item> retrieveItem(String query) throws  IOException,  org.apache.http.ParseException, XmlPullParserException{
		try{
			if(MainZoteroActivity.flag==5 || MainZoteroActivity.flag==6){
				url = m_screen.on_Single_item_click;
			}
			
			
			else if(MainZoteroActivity.flag==7){
				Log.i(getClass().getSimpleName(),"flag: "+MainZoteroActivity.flag);
				url = m_screen.on_Child_info_click;
			}
			

		}catch(NullPointerException n){
			n.printStackTrace();
		}

		//cred.constructURL(query);
		Log.d(getClass().getSimpleName(), url);
		//executes HTTP request using HTTP retriever
		// response = httpRetriever.retrieveStream(url);
		response = httpRetriever.retrieve(url);


		return  JSONParser.parseJSON(response); //itemParser.parse(response);
	}
	public List<Item> retrieveCollection(String query) throws NullPointerException, JSONException{
		Log.e(getClass().getCanonicalName(), "retrieveCollection()");
		try{
			if(MainZoteroActivity.flag==1 ){
				//constructs the URL query
				url = "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/collections/top?key="+MainZoteroActivity.USERKEY;
				
			}
			else if(MainZoteroActivity.flag==8){
				url = query;
			}
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
		
		response = httpRetriever.retrieve(url);
		
		return  JSONCollectrionParser.parseCollJson(response);
		//return new JsonParser().parseJSON(response);
	}
	
	

}
