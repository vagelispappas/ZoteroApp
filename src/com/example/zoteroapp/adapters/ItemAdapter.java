package com.example.zoteroapp.model;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zoteroapp.R;
import com.example.zoteroapp.m_screen;
import com.example.zoteroapp.services.HttpRetriever;

public class ItemAdapter extends ArrayAdapter<Item>{
	
	
	
	HttpRetriever httpRetriever = new HttpRetriever();
	List<Item> itemList = new ArrayList<Item>();
	List<String> parents = new ArrayList<String>();
	Activity context;
	String type;
	String childNum;
	String compare;
	TableRow tbl_bttn_list_row;
	
	Button sub_coll;
	TextView subcollection_notice;
	m_screen ms; 
	
	public ItemAdapter(Activity context, int resource, List<Item> items  ) {
		super(context, resource, items);
		Log.i(getClass().getCanonicalName(), "Item adapter");
		// TODO Auto-generated constructor stub
		this.context = context;
		this.itemList = items;
		
		
	}
	
	@SuppressLint("InflateParams")
	public View getView(final int position , View convert , ViewGroup group){
		View view = convert;
		
		
		
		if(view==null) {
			Log.i(getClass().getCanonicalName(), "Item adapter inflating...");
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.lista_item, null);
			
		}
		Item item = itemList.get(position);
		

		if(item!=null){

			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			tv_name.setText(item.getTitle());
			
			

			TextView tv_date = (TextView)view.findViewById(R.id.tv_date);
			String s;
			s = item.getPublished().toString();
			String new_s;
			new_s = s.substring(0, 10);
			
			
			

			ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
			imageView.setImageResource( item.imageRetriever( type));
			
			ImageView imageView2 = (ImageView)view.findViewById(R.id.imageView2);
			
			sub_coll = (Button)view.findViewById(R.id.bttn_sub_collection);
			
			subcollection_notice = (TextView)view.findViewById(R.id.tv_subcollection);
			
			try{
				//if(MainZoteroActivity.flag<=2){
					imageView2.setVisibility(View.GONE);
					tv_date.setText(String.valueOf("Created: "+new_s));
					
					if(!"0".equals(item.getChildrenNum())&& item.getItemType() == null ){
						
						sub_coll.setVisibility(View.VISIBLE);
						imageView2.setVisibility(View.GONE);	
					}
					else if("0".equalsIgnoreCase(item.getChildrenNum())&& item.getItemType() == null){
						imageView2.setVisibility(View.GONE);
						sub_coll.setVisibility(View.GONE);
						subcollection_notice.setVisibility(View.GONE);
					}
					else if(!"0".equalsIgnoreCase(item.getChildrenNum()) && item.getItemType() !=null){
						//imageView2.setVisibility(View.VISIBLE);
						//imageView2.setBackgroundResource(R.drawable.connecter);
					}
					
					else{
					    imageView2.setVisibility(View.GONE);
						sub_coll.setVisibility(View.GONE);
						subcollection_notice.setVisibility(View.GONE);
					}
					
				//}
				//else if(MainZoteroActivity.flag>2) {
					
					//sub_coll.setVisibility(View.GONE);
					//subcollection_notice.setVisibility(View.GONE);
					
					imageView2.setImageResource(item.connecterRetrieve(childNum));
					String itemAuthor = item.getAuthor().toString();
					if(String.valueOf(itemAuthor) != null){
						Log.i(getClass().getSimpleName(),"Into if String.valueOf(itemAuthor) != null ");
						tv_date.setText(String.valueOf("Creator: "+itemAuthor));
						
					//}
					
					
				}
			}catch(NullPointerException e){
				
				e.printStackTrace();
			}

		}
		
		return view;
	}

 

	

}
