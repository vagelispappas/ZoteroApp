package com.example.zoteroapp.model;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zoteroapp.R;



public class CollectionAdapter extends ArrayAdapter<Item> {

	Context context;
	ArrayList<Item> itemList ;//= new ArrayList<Item>();
	LayoutInflater inflater;
	View V = null;
	
	String type;


	@SuppressLint("InflateParams")
	public CollectionAdapter(Context context, int resource,ArrayList<Item> items){
		super(context, resource, items);

		this.context = context;
		this.itemList = items;

		inflater = LayoutInflater.from(this.context);
		V = inflater.inflate(R.layout.lista_item, null);

		Log.i(getClass().getCanonicalName(), "collection adapter ");
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder;


		Log.i(getClass().getCanonicalName(), "collection adapter before inflating...");

		Item CC =  getItem(position);
		try{
			if(view == null){

				Log.i(getClass().getCanonicalName(), "collection adapter inflating...");
				//inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//view = inflater.inflate(R.layout.lista_item, null);

				view = View.inflate(context, R.layout.lista_item, null);
				

				holder = new ViewHolder();

				holder.tv_title = (TextView)view.findViewById(R.id.tv_name);
				holder.tv_date = (TextView)view.findViewById(R.id.tv_date);
				holder.tv_dummy = (TextView)view.findViewById(R.id.tv_dummy);
				holder.bttn_subcoll = (Button)view.findViewById(R.id.bttn_sub_collection);
				
				
				
				holder.image= (ImageView)view.findViewById(R.id.imageView1);

				

				view.setTag(holder);
			}
			else{
				Log.i(getClass().getCanonicalName(), "holder is not null");

				//holder = (ViewHolder) view.getTag();
			}

			holder = (ViewHolder) view.getTag();



			holder.tv_title.setTag(position);
			//holder.tv_date.setTag(position);
			
			holder.bttn_subcoll.setTag(position);
			



			holder.tv_title.setText(CC.getTitle().toString());
			//holder.tv_date.setText(CC.getParentCollectionKey().toString());
			holder.image.setImageResource( CC.imageRetriever( type));
			//holder.bttn_subcoll.setText("+");

			if(!"0".equals(CC.getNumberOfCollections())/*&& CC.getItemType() == null*/ ){
				//Log.i(getClass().getCanonicalName(), "This is the if condition----- : "+CC.getNumberOfCollections());
				holder.bttn_subcoll.setVisibility(View.VISIBLE);
				if(!CC.isOpened){
					holder.bttn_subcoll.setText("+");
				}
				else{
					holder.bttn_subcoll.setText("-");
				}
				holder.bttn_subcoll.setEnabled(true);
			}
			else if("0".equalsIgnoreCase(CC.getNumberOfCollections())/*&& CC.getItemType() == null*/){
				//Log.i(getClass().getCanonicalName(), "This is the else if condition----- : "+CC.getNumberOfCollections());
				holder.bttn_subcoll.setVisibility(View.GONE);
			}
			else{
				//Log.i(getClass().getCanonicalName(), "This is the else  condition----- : "+CC.getNumberOfCollections());
				holder.bttn_subcoll.setVisibility(View.GONE);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		

		return view;
	
}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.itemList==null?0:this.itemList.size();
	}

	@Override
	public Item getItem(int position) {
		// TODO Auto-generated method stub
		return this.itemList==null?null:this.itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void updateList(ArrayList<Item> data){
		
		itemList = data;
		notifyDataSetChanged();
		
	}

	static class ViewHolder{
		TextView tv_title;
		TextView tv_date;
		TextView tv_dummy;
		Button bttn_subcoll;
		ImageView image;
	

	}


}
