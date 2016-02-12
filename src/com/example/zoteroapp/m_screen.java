package com.example.zoteroapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoteroapp.model.CollectionAdapter;
import com.example.zoteroapp.model.Item;
import com.example.zoteroapp.model.ItemAdapter;
import com.example.zoteroapp.services.HttpRetriever;
import com.example.zoteroapp.services.ItemSeeker;
import com.example.zoteroapp.services.ServerCred;
import com.example.zoteroapp.tabs.FragmentTab;


public class m_screen extends ListActivity implements OnClickListener   {

	Item item;

	String zapi_key;
	String url_zapi_key;
	String parent_coll_key;
	String currentCollKey;
	Editable getEditText;
	String attachment_URL;
	String item_name;
	//subcollKey
	String parentCollKey;

	String url_itemMember_zapi_key;
	ArrayList<String> member_zapi = new ArrayList<String>(); 

	public static String on_click_URL;
	public static String on_Single_item_click;
	public static String on_Child_info_click;
	public static String currentCollectionKey;

	//ItemTask2 task2 = new ItemTask2();
	public int global_position;
	int index;
	String s;
	HttpRetriever httpRetriever = new HttpRetriever();

	ArrayList<String> zapi=new ArrayList<String>();

	ArrayList<String> titles_member_items_list = new ArrayList<String>();
	String for_titles_member_items;

	ArrayList<String> single_zapi=new ArrayList<String>();

	ServerCred<Item> itemseeker = new ItemSeeker();

	ArrayList<String> zapi_key_list = new ArrayList<String>();
	ArrayList<String> zapi_key_list2  =new ArrayList<String>();
	ArrayList<String> parent_coll_key_list = new ArrayList<String>();
	ArrayList<String> parent_coll_title_list = new ArrayList<String>();
	ArrayList<String> sub_coll_key_list = new ArrayList<String>();
	ArrayList<String> sub_coll_title_list = new ArrayList<String>();

	ArrayList<Item> test_list = new ArrayList<Item>();

	ArrayList<String> search_list = new ArrayList<String>();

	ItemAdapter item_adapter;
	CollectionAdapter coll_adapter;
	ItemAdapter item_adapter2;


	String url = null;

	ListView l,subcoll_list;

	TableRow tbl_titl , tbl_title , tbl_url , tbl_author , tbl_doi , tbl_ISSN , tbl_ISBN , tbl_pages ,
	tbl_publisher , tbl_abstract , tbl_blog_title , tbl_short_title ,
	tbl_date_added , tbl_date_modified;

	TextView tv_type ,tv_url,firstname,lastname,tv_abstract ,tv_title,tv_doi,
	tv_ISSN,tv_ISBN,tv_pages,tv_publisher,tv_blog_title,tv_short_title,tv_date_added,
	tv_date_modified;
	TextView tv_label_author,tv_titl,tv2_name;

	Button bttn_child_info , bttn_see_Attach;

	TableRow tbl_child_info , tbl_see_attach , tbl_label_titl;

	ArrayList<Item> item_list;
	ArrayList<Item> subcollection_list;
	ArrayList<Item> copy_list;

	FragmentTab fragmentTab = null; 
	FragmentTransaction ft = null; 

	EditText et_search;
	String subparam;

	public boolean et_search_visibility = false;

	ImageView imageView;

	ListView list1 ;
	Button bttn_subColl;
	int [] location = new int[2];


	ListPopupWindow listPopUp;
	//String subCollName[];
	ArrayList<Item> subCollList;
	HttpRetriever http = new HttpRetriever();



	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_screen);


		imageView = (ImageView)findViewById(R.id.img_search);
		imageView.setClickable(true);
		imageView.setOnClickListener(this);
		imageView.setVisibility(View.VISIBLE);
		et_search = (EditText)findViewById(R.id.et_search);
		et_search.setClickable(true);
		et_search.setOnClickListener(this);

		bttn_subColl = (Button)findViewById(R.id.bttn_sub_collection);

		tv_label_author  = (TextView)findViewById(R.id.label_author);

		item_list = new ArrayList<Item>();

		parent_coll_key_list= new ArrayList<String>();
		parent_coll_title_list= new ArrayList<String>();

		sub_coll_key_list = new ArrayList<String>();
		sub_coll_title_list = new ArrayList<String>();

		subcollection_list = new ArrayList<Item>();


		item_adapter = new ItemAdapter(this, android.R.layout.simple_list_item_1, item_list);
		
		list1 = (ListView)findViewById(R.id.list1);

		//coll_adapter = new CollectionAdapter(this,android.R.layout.simple_expandable_list_item_1, item_list);		
		//list1.setDividerHeight(5);

		subcoll_list = (ListView)findViewById(R.id.lv_subcollections);

		item_list = (ArrayList<Item>) getIntent().getSerializableExtra("items");

		try{
			copy_list= new ArrayList<Item>(item_list.size());
			copy_list.addAll(item_list);
		}catch(Exception ex){
			ex.printStackTrace();
		}


		zapi_key_list=(ArrayList<String>)getIntent().getStringArrayListExtra("zapi_keys");

		//the list with parents collection key
		try{
			parent_coll_key_list = (ArrayList<String>)getIntent().getStringArrayListExtra("parent collections keys");
			parent_coll_title_list = (ArrayList<String>)getIntent().getStringArrayListExtra("parent collections titles");

		}catch(NullPointerException e){
			e.printStackTrace();
		}


		if(zapi_key_list!=null){
			for(int i=0;i<zapi_key_list.size();i++){
				continue;
			}
			try{
				Log.d(getClass().getSimpleName(),"stoixia tis zapi_key_list"+zapi_key_list);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}else{
			try{
				Log.d("zapi_key_list einai adeia ","teleiws");
				MainZoteroActivity.flag = 1;
				new RefreshTask().execute();
			}catch (NullPointerException n){
				n.printStackTrace();
			}
		}


		//list1.setAdapter(item_adapter);
		//list1.setAdapter(coll_adapter);
		//setListAdapter(coll_adapter);

		//click on the first listview with collections	
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				//arg1.setSelected(true);	

				MainZoteroActivity.flag=3;
				if(et_search.getVisibility()==View.VISIBLE){
					et_search.setVisibility(View.GONE);
				}

				if(imageView.getVisibility()==View.GONE){
					imageView.setVisibility(View.VISIBLE);
				}


				//disappear the fragment
				if(fragmentTab!=null && ft!=null){
					try{
						fragmentTab.getActivity().getFragmentManager().beginTransaction().remove(fragmentTab).commit();
					}catch(NullPointerException e){
						e.printStackTrace();
					}
				}

				Log.d(getClass().getSimpleName(),"The posotion "+position);

				url_zapi_key = item_list.get(position).getZapi_key().toString();//zapi_key_list.get(position);

				currentCollectionKey(url_zapi_key);


				Log.d(getClass().getSimpleName(),"url_ zapi key "+url_zapi_key);

				String coll_url = "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/collections/";
				String key_url = "/items/top?format=atom&key="+MainZoteroActivity.USERKEY;	//?key=hIpXYNj8Ct1e7JQznLTKlxLL

				on_click_URL= coll_url+url_zapi_key+key_url;

				currentCollectionKey = url_zapi_key;
				Log.d(getClass().getSimpleName(), "the url for member items: "+on_click_URL+" and the flag"+MainZoteroActivity.flag);

				new ItemTask2().execute();
				//task2.execute();
			}
		});// list onclick  

		try{
			if(item_list!=null && !item_list.isEmpty()){

				coll_adapter.notifyDataSetChanged();
				coll_adapter.clear();

				for (int i = 0; i < item_list.size(); i++) {

					coll_adapter.add(item_list.get(i));
				}

			}
			coll_adapter.notifyDataSetChanged();
		}catch(Exception ex){
			ex.printStackTrace();
		}

		et_search.setVisibility(View.GONE);

		et_search.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					getEditText = et_search.getText();
					perforSearch(getEditText);
					InputMethodManager imm = (InputMethodManager)getSystemService(
							Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
				}
				return false;
			}
		});

	}//////////////////////////////////////////end onCreate()

	public  void SubCollections(String s){

		@SuppressWarnings("unused")
		String parentKey;
		int j=0;
		for(int i=0; i<parent_coll_title_list.size();i++){

			if(s.equalsIgnoreCase(parent_coll_title_list.get(i))){
				Toast.makeText(getApplicationContext(),"find match",Toast.LENGTH_SHORT).show(); 
				i=j;
				parentKey = parent_coll_key_list.get(j);
				Toast.makeText(getApplicationContext(),parent_coll_key_list.get(j),Toast.LENGTH_SHORT).show();
				//ActiveSubCollections(parentKey);
			}
		}
	}


	public void ActiveSubCollections(String key ){

		String s = key;

		MainZoteroActivity.flag = 8;

		//Toast.makeText(getApplicationContext(), "ActiveSubCollections"+key+" "+MainZoteroActivity.flag, Toast.LENGTH_SHORT).show();
		new SubCollectionTask().execute(s);

	}

	public void  refresh(){
		onRestart();	

	}

	public void onRestart(){
		super.onRestart();
		Intent i = new Intent(m_screen.this,m_screen.class);  
		startActivity(i);
		finish();
		onStart();
	}
	public void onStart(){
		super.onStart();
		Log.e(getClass().getSimpleName(),"  onStart()");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_zotero, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.item_refresh){
			refresh();
		} 
		return true;

	}


	public void onResume(){
		super.onResume();
		Log.e(getClass().getSimpleName(),"  onResume()");
		populateList();

	}//end onResume

	public void populateList(){
		coll_adapter = new CollectionAdapter(this,android.R.layout.simple_expandable_list_item_1, item_list);		
		list1.setAdapter(coll_adapter);
		list1.setDividerHeight(5);
	}


	//swap framelayout with fragment
	public void activeFragment(final ArrayList<Item> list ,final ArrayList<String> keys,final ArrayList<String> itemTypes){

		//new Item().imageRetriever();
		ItemAdapter adapter = new ItemAdapter(this,android.R.layout.simple_list_item_2 , list);
		l = (ListView)findViewById(android.R.id.list);
		setListAdapter(adapter);

		//click on the second listview
		l.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				//arg1.setSelected(true);				

				MainZoteroActivity.flag=5;


				//set up the fragment
				fragmentTab = new FragmentTab();

				ft = getFragmentManager().beginTransaction();

				ft.replace(R.id.framelayout1, fragmentTab);
				ft.commit();

				String url_member= "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/items/";
				String key_member="?format=json&key="+MainZoteroActivity.USERKEY;

				//String url_member= "https://api.zotero.org/users/1737513/items/";
				//String key_member="?key=hIpXYNj8Ct1e7JQznLTKlxLL";


				url_itemMember_zapi_key = keys.get(position);

				Item i = list.get(position);
				item_name = i.getTitle();


				//construct the json url
				on_Single_item_click = url_member+url_itemMember_zapi_key+key_member;
				Log.d(getClass().getCanonicalName(),on_Single_item_click);
				new ItemTask3().execute();

			}
		});
	}//end activeFregment

	public void childInfo(String key){
		Log.d(getClass().getSimpleName(), "childInfo()");
		String childkey = key;

		bttn_child_info = (Button)findViewById(R.id.bttn_childInfo);

		tbl_child_info = (TableRow)findViewById(R.id.tblRow_BttnChild);
		tbl_child_info.setVisibility(View.VISIBLE);

		String url_member= "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/items/";
		String child = "/children";
		String format="?format=json&key="+MainZoteroActivity.USERKEY;

		on_Child_info_click = url_member+childkey+child+format;

		bttn_child_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainZoteroActivity.flag=7;
				Log.i(getClass().getSimpleName(),"flag: "+MainZoteroActivity.flag);
				new ChildTask().execute();
			}
		});


	}

	private	class ItemTask2 extends AsyncTask<String,Void, ArrayList<Item>>{

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			Log.d(getClass().getName(), "doInbackground()");
			// TODO Auto-generated method stub
			String query = params.toString();

			ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.find(query);
			return it_seeker;


		}//end doInbackground
		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==3){
						titles_member_items_list.clear();				
						zapi.clear();
						try{


							for(Item i:result){

								s= i.getZapi_key();
								for_titles_member_items = i.getItemType();

								Log.d(getClass().getSimpleName(),"Member item zapi key  "+s);
								Log.i(getClass().getSimpleName(),"and member zapi ITEMTYPE "+for_titles_member_items );
								zapi.add(s);
								titles_member_items_list.add(for_titles_member_items);
							}//end first for

							Log.d(getClass().getSimpleName(),"List member item zapi key  "+zapi);

							activeFragment(result, zapi,titles_member_items_list);
						}catch(NullPointerException ex){
							ex.printStackTrace();
						}

					}//end if
				}//end run

			});
		}//end onPostexecute()
	}//end ItemTask2


	private class ItemTask3 extends AsyncTask<String , Void, ArrayList<Item>> {

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String query = params.toString();
			ArrayList<Item> seeker = (ArrayList<Item>) itemseeker.find2(query);
			return seeker;
		}
		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@SuppressLint("DefaultLocale")
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==5 || MainZoteroActivity.flag==6 ){	
						try{

							Log.i(getClass().getCanonicalName()," result "+result);
							for(Item item:result){

								tbl_ISSN = (TableRow)findViewById(R.id.tblRow_ISSN);
								tbl_ISBN = (TableRow)findViewById(R.id.tblRow_ISBN);
								tbl_pages = (TableRow)findViewById(R.id.tblRow_pages);
								tbl_publisher = (TableRow)findViewById(R.id.tblRow_publisher);
								tbl_blog_title = (TableRow)findViewById(R.id.tblRow_blog_title);
								tbl_short_title = (TableRow)findViewById(R.id.tblRow_short_title);
								tbl_date_added = (TableRow)findViewById(R.id.tblRow_date_added);
								tbl_date_modified = (TableRow)findViewById(R.id.tblRow_date_modified);
								tbl_doi = (TableRow)findViewById(R.id.tblRow_DOI);
								tbl_author = (TableRow)findViewById(R.id.tblRow_author);
								tbl_abstract=(TableRow)findViewById(R.id.tblRow_abstract);
								tbl_url = (TableRow)findViewById(R.id.tblRow_url);

								tv_type = (TextView)findViewById(R.id.tv_title);
								tv_url = (TextView)findViewById(R.id.tv_url);
								//firstname = (TextView)findViewById(R.id.tv_firstname);
								tv_title = (TextView)findViewById(R.id.tv_titl);
								lastname = (TextView)findViewById(R.id.tv_lastname);
								tv_abstract = (TextView)findViewById(R.id.tv_abstract);
								tv_doi = (TextView)findViewById(R.id.tv_DOI);
								tv_ISSN = (TextView)findViewById(R.id.tv_ISSN);
								tv_ISBN = (TextView)findViewById(R.id.tv_ISBN);
								tv_pages = (TextView)findViewById(R.id.tv_pages);
								tv_publisher = (TextView)findViewById(R.id.tv_publisher);
								tv_blog_title = (TextView)findViewById(R.id.tv_blog_title);
								tv_short_title = (TextView)findViewById(R.id.tv_short_title);
								tv_date_added = (TextView)findViewById(R.id.tv_date_added);
								tv_date_modified = (TextView)findViewById(R.id.tv_date_modified);

								tv_title.setText(" "+item_name);

								tv_type.setText(String.valueOf(String.valueOf("  "+item.getItemType())));


								if(item.getUrl()==null){
									tbl_url.setVisibility(View.GONE);
								}
								else if(item.getUrl().isEmpty()){
									tbl_url.setVisibility(View.GONE);

								}
								else if(!item.getUrl().isEmpty()){
									tbl_url.setVisibility(View.VISIBLE);

									tv_url.setText(String.valueOf("  "+item.getUrl().toString()));
									tv_url.setTypeface(null, Typeface.ITALIC);
									tv_url.setTextColor(Color.RED);
								}
								else{
									tbl_url.setVisibility(View.GONE);

								}

								//firstname.setText(String.valueOf(item.getFirstName().toString()))
								if(item.getLastName()==null || item.getFirstName() == null){
									continue;
								}
								else{
									lastname.setText(String.valueOf(" "+item.getLastName().toString())+"  "+item.getFirstName().toString());

								}
								///////////////////////////////
								if(item.getDOI() == null){
									tbl_doi.setVisibility(View.GONE);	

								}
								else if(item.getDOI().isEmpty()){

									tbl_doi.setVisibility(View.GONE);
								}

								else if(!item.getDOI().isEmpty()){
									tbl_doi.setVisibility(View.VISIBLE);
									tv_doi.setText(String.valueOf(":  "+item.getDOI()));									}

								else{
									tbl_doi.setVisibility(View.GONE);		
								}

								////////////////////////////////////////////////////////
								if(item.getISSN()==null){								
									tbl_ISSN.setVisibility(View.GONE);

								}
								else if(item.getISSN().isEmpty()){
									tbl_ISSN.setVisibility(View.GONE);

								}
								else if(!item.getISSN().isEmpty()){
									tbl_ISSN.setVisibility(View.VISIBLE);
									tv_ISSN.setText(":  "+item.getISSN().toString());
								}
								else{
									tbl_ISSN.setVisibility(View.GONE);
								}
								///////////////////////////////////////////////////////
								if(item.getISBN() == null){
									tbl_ISBN.setVisibility(View.GONE);
								}
								else if(item.getISBN().isEmpty()){
									tbl_ISBN.setVisibility(View.GONE);

								}
								else if(!item.getISBN().isEmpty()){
									tbl_ISBN.setVisibility(View.VISIBLE);
									tv_ISBN.setText(":  "+item.getISBN().toString());
								}
								else{
									tbl_ISBN.setVisibility(View.GONE);
								}
								//////////////////////////////////////////////////////////
								if(item.getPages()==null){

									tbl_pages.setVisibility(View.GONE);
								}
								else if(item.getPages().isEmpty()){
									tbl_pages.setVisibility(View.GONE);
								}
								else if(!item.getPages().isEmpty()){
									tbl_pages.setVisibility(View.VISIBLE);
									tv_pages.setText(":  "+item.getPages().toString());
								}
								else{
									tbl_pages.setVisibility(View.GONE);
								}
								/////////////////////////////////////////////////////////////

								if(item.getPublisher()==null){

									tbl_publisher.setVisibility(View.GONE);
								}
								else if(item.getPublisher().isEmpty()){
									tbl_publisher.setVisibility(View.GONE);
								}
								else if(!item.getPublisher().isEmpty()){
									tbl_publisher.setVisibility(View.VISIBLE);
									tv_publisher.setText(":  "+item.getPublisher().toString());
								}
								else{
									tbl_publisher.setVisibility(View.GONE);
								}
								////////////////////////////////////////////////////////////////////////////////////
								if(item.getShortTitle()==null){
									tbl_short_title.setVisibility(View.GONE);

								}
								else if(item.getShortTitle().isEmpty()){
									tbl_short_title.setVisibility(View.GONE);

								}
								else if(!item.getShortTitle().isEmpty()){
									tbl_short_title.setVisibility(View.VISIBLE);
									tv_short_title.setText(":  "+item.getShortTitle().toString());
								}
								else{
									tbl_short_title.setVisibility(View.GONE);
								}
								/////////////////////////////////////////////////////////////
								if(item.getBlog_title()==null){
									tbl_blog_title.setVisibility(View.GONE);

								}
								else if(item.getBlog_title().isEmpty()){
									tbl_blog_title.setVisibility(View.GONE);

								}
								else if(!item.getBlog_title().isEmpty()){
									tbl_blog_title.setVisibility(View.VISIBLE);
									tv_blog_title.setText(":  "+item.getBlog_title().toString());
								}
								else{
									tbl_blog_title.setVisibility(View.GONE);
								}
								/////////////////////////////////////////////
								if( item.getDate_added() == null ){
									tbl_date_added.setVisibility(View.GONE);
								}
								else if( item.getDate_added().isEmpty()){
									tbl_date_added.setVisibility(View.GONE);

								}
								else if(!item.getDate_added().isEmpty()){
									tbl_date_added.setVisibility(View.VISIBLE);
									tv_date_added.setText(":  "+item.getDate_added().toString());
								}
								else{
									tbl_date_added.setVisibility(View.GONE);

								}
								///////////////////////////////////////////////////////
								if(item.getDate_modified() == null ){
									tbl_date_modified.setVisibility(View.GONE);

								}
								else if(item.getDate_modified().isEmpty()){
									tbl_date_modified.setVisibility(View.GONE);
								}
								else if(!item.getDate_modified().isEmpty()){
									tbl_date_modified.setVisibility(View.VISIBLE);
									tv_date_modified.setText(":  "+item.getDate_modified().toString());
								}
								else{
									tbl_date_modified.setVisibility(View.GONE);

								}
								/////////////////////////////////////////////////////////////////////////////////////
								if(item.getAbstractNote() == null){
									tbl_abstract.setVisibility(View.GONE);

								}
								else if(item.getAbstractNote().isEmpty()){
									tbl_abstract.setVisibility(View.GONE);

								}
								else if(!item.getAbstractNote().isEmpty() ){

									tbl_abstract.setVisibility(View.VISIBLE);

									tv_abstract.setText(String.valueOf(item.getAbstractNote().toString()));
									tv_abstract.setMovementMethod(new ScrollingMovementMethod());
								}
								else{
									tbl_abstract.setVisibility(View.GONE);
								}
								//////////////////////////////////////////////////////////////////////////////


								url = item.getUrl().toString();
								int childNum = Integer.parseInt(item.getChildrenNum());

								Log.d(getClass().getCanonicalName()," childNum (integer)  "+childNum);


								if(childNum>0){
									Log.d(getClass().getCanonicalName(),"childnum >0");				
									childInfo(item.getZapi_key().toString());
								}

								tv_url.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
									}
								});
							}
						}
						catch(NullPointerException n){
							n.printStackTrace();

						}

					}
				}

			});
		}

	}//end ItemTask3
	private class ChildTask extends AsyncTask<String, Void, ArrayList<Item>>{

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String query = params.toString();
			ArrayList<Item> seeker = (ArrayList<Item>) itemseeker.find2(query);
			return seeker;
		}
		protected void  onPostExecute(final ArrayList<Item> result)  {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==7){
						try{
							Log.i(getClass().getCanonicalName()," result "+result);
							tbl_label_titl = (TableRow)findViewById(R.id.tblRow_titl);
							tbl_label_titl.setVisibility(View.VISIBLE);
							for(Item item:result){

								Log.d(getClass().getCanonicalName()," firstname  "+item.getFirstName().toString());
								Log.d(getClass().getCanonicalName()," Num of children  "+item.getChildrenNum().toString());

								tv_titl = (TextView)findViewById(R.id.tv_titl);
								tv_type = (TextView)findViewById(R.id.tv_title);
								tv_url = (TextView)findViewById(R.id.tv_url);
								//firstname = (TextView)findViewById(R.id.tv_firstname);
								lastname = (TextView)findViewById(R.id.tv_lastname);
								tv_abstract = (TextView)findViewById(R.id.tv_abstract);

								//Log.d(getClass().getSimpleName(),"the zapi key einai: "+item.zapi_key.toString());

								tv_titl.setText(String.valueOf(item.getTitle()));
								tv_type.setText(String.valueOf(String.valueOf(item.getItemType())));

								tv_url.setText(String.valueOf(item.getUrl().toString()));
								tv_url.setTypeface(null, Typeface.ITALIC);
								tv_url.setTextColor(Color.RED);


								//firstname.setText(String.valueOf(item.getFirstName().toString()));

								lastname.setText(String.valueOf(item.getLastName().toString())+"  "+item.getFirstName().toString());

								tv_abstract.setText(String.valueOf(item.getAbstractNote().toString()));
								tv_abstract.setMovementMethod(new ScrollingMovementMethod());

								url = item.getUrl().toString();
								int childNum = Integer.parseInt(item.getChildrenNum());

								Log.d(getClass().getCanonicalName()," childNum (integer)  "+childNum);

								if(!"Snapshot".equalsIgnoreCase(item.getTitle().toString())){
									tbl_see_attach = (TableRow)findViewById(R.id.tblRow_Bttn_Attach);
									tbl_see_attach.setVisibility(View.VISIBLE);
								}

								final String item_key = item.getZapi_key();

								bttn_see_Attach = (Button)findViewById(R.id.bttn_attachment);

								bttn_see_Attach.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub

										String url = "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/items/" ;
										String key	="/file?key="+MainZoteroActivity.USERKEY; //?key=KLhSEwv3q04CyXOUnzqeOKND
										attachment_URL = url+item_key+key;
										startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(attachment_URL)));
									}
								});

								if(childNum>0){
									Log.d(getClass().getCanonicalName(),"childnum >0");				
									childInfo(item.getZapi_key().toString());
								}

								tv_url.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
									}
								});
							}
						}catch(NullPointerException e){
							e.printStackTrace();
						}
					}
				}
			});


		}

	}
	private class RefreshTask extends AsyncTask<String, Void , ArrayList<Item>>{


		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			Log.d(getClass().getName(), "RefreshTask doInbackground()");
			// TODO Auto-generated method stub
			String query = params.toString();
			//ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.find(query);
			ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.findCollection(query);
			return it_seeker;
		}

		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==1){

						Intent intent = new Intent(m_screen.this , m_screen.class);
						intent.putExtra("items", result);

						for(Item i:result){
							s= i.getZapi_key();
							Log.d(getClass().getSimpleName(),"zapi key  "+s);
							zapi.add(s);
						}
						try{


							for(Item item:result){

								if(!"0".equals(item.getChildrenNum())){
									parent_coll_key_list.add(item.getZapi_key().toString());
									parent_coll_title_list.add(item.getTitle().toString());
								}
							}
						}catch(NullPointerException n){
							n.printStackTrace();
						}
						Log.d("H lista me ta zapi key"+zapi, s);
						intent.putStringArrayListExtra("zapi_keys", zapi);
						startActivity(intent);
					}

				}

			});

		}

	}
	public Item getItem(int level , int hasChild){
		Item item = new Item();

		//String name = item.getTitle().toString();
		item.setTitle("TEST");

		item.hasChild = hasChild;
		item.level = level;
		item.isOpened = false;

		return item;
	}
	public void removeChilds(int index){
		int removeIndex = index+1;
		int index1 = index;
		for(int i = 0;i<item_list.size();i++){
			if(item_list.get(index1).getZapi_key().equalsIgnoreCase(item_list.get(removeIndex).getParentCollectionKey())){
				if(item_list.get(removeIndex).isOpened){
					removeChilds(removeIndex);
				}
				item_list.remove(removeIndex);
			}
		}

		coll_adapter.updateList(item_list);

	}

	public void subCollClick(View v){
		try{
			Button button = (Button)v;
			index = (Integer)button.getTag();
			String current_key = item_list.get(index).getZapi_key().toString();

			if(button.getText().toString().equals("-")){
				item_list.get(index).isOpened=false;
				button.setText("+");
				int removeIndex = index + 1 ;
				for(int i=0;i<item_list.size();i++) {
					if(item_list.get(index).getZapi_key().equalsIgnoreCase(item_list.get(removeIndex).getParentCollectionKey())){
						if(item_list.get(removeIndex).isOpened){
							removeChilds(removeIndex);
						}
						item_list.remove(removeIndex);
					}
				}
				coll_adapter.updateList(item_list);

			}
			else{
				item_list.get(index).isOpened=true;
				button.setText("-");
				ActiveSubCollections(current_key);
			}

			//Toast.makeText(getApplicationContext(),"This is current parent zapi key :"+ current_key, Toast.LENGTH_LONG).show();
			Log.e(getClass().getCanonicalName(),"List size :"+ item_list.size());

		}catch(Exception e){
			coll_adapter.notifyDataSetChanged();
			Log.d("Error=", ""+e.getMessage());
			//e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.img_search){
			if(et_search_visibility == false){
				et_search.setVisibility(View.VISIBLE);
				et_search_visibility = true;
			}
			else{
				getEditText = et_search.getText();
				//perforSearch(getEditText);
				et_search_visibility = false;
				InputMethodManager imm = (InputMethodManager)getSystemService(
						Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
			}

		}

	}


	public String currentCollectionKey(String collKEY){

		return collKEY;
	}
	public void searchResults(final ArrayList<Item> list, final ArrayList<String> keys){
		ListView searchview;
		ItemAdapter search_adapter = new ItemAdapter(this,android.R.layout.simple_list_item_2 ,  list);
		searchview = (ListView)findViewById(android.R.id.list);
		searchview.setEmptyView(findViewById(android.R.id.list).findViewById(android.R.id.empty));
		setListAdapter(search_adapter);

		//searchview.setAdapter(search_adapter);

		searchview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				MainZoteroActivity.flag = 6;

				//disappear the fragment
				if(fragmentTab!=null && ft!=null){
					try{
						fragmentTab.getActivity().getFragmentManager().beginTransaction().remove(fragmentTab).commit();
					}catch(NullPointerException e){
						e.printStackTrace();
					}
				}


				//if(fragmentTab==null && ft==null){
				fragmentTab = new FragmentTab();
				ft = getFragmentManager().beginTransaction();

				ft.replace(R.id.framelayout1, fragmentTab);
				ft.commit();
				//}
				String url_member= "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/items/";
				String key_member="?format=json&key="+MainZoteroActivity.USERKEY;
				//"key=hIpXYNj8Ct1e7JQznLTKlxLL";


				url_itemMember_zapi_key = keys.get(position);


				on_Single_item_click = url_member+url_itemMember_zapi_key+key_member;

				new ItemTask3().execute();
			}
		});

	}
	public void perforSearch(Editable et){
		Toast.makeText(getApplicationContext(), et.toString(),Toast.LENGTH_LONG).show();

		String search_text = et.toString();

		MainZoteroActivity.flag=6;
		new SearchTask().execute(search_text , currentCollectionKey);
	}

	private class SearchTask extends AsyncTask<String , Void, ArrayList<Item>>{

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub

			Log.d(getClass().getSimpleName(), "search task doInBackgrond");

			String text = params[0];
			String key = params[1];
			@SuppressWarnings("unused")
			String search_url;
			String q = "?q=";
			String items = "/items/";
			String coll_url= "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/collections/";

			String item_url=  "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/items/"+q+text+"&format=atom&key="+MainZoteroActivity.USERKEY;


			search_url = coll_url+key+items+q+text;
			ArrayList<Item> search_seeker = (ArrayList<Item>) itemseeker.find(item_url);

			return search_seeker;

		}
		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==6){
						String key=null;
						for(Item item:result){
							key = item.getZapi_key();
							search_list.add(key);
							Log.d(getClass().getSimpleName(),"search key: "+key.toString());

						}
						Log.d(getClass().getSimpleName(),"search key lis : "+search_list);
						searchResults(result,search_list);
					}
				}
			});
		}

	}//end search task
	private class SubCollectionTask extends AsyncTask<String, Void, ArrayList<Item>>{

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String key = params[0];
			String base = "https://api.zotero.org/users/"+MainZoteroActivity.USERID+"/collections/";
			String collection = "/collections?key="+MainZoteroActivity.USERKEY;

			String SubCollections_URL = base+key+collection;
			ArrayList<Item> subcoll_seeker = (ArrayList<Item>) itemseeker.findCollection(SubCollections_URL);

			return subcoll_seeker;
		}

		protected void onPostExecute(final ArrayList<Item> result){
			runOnUiThread(new Runnable() {



				//@SuppressLint("NewApi")
				@Override
				public void run() {
					if(MainZoteroActivity.flag==8){
						// TODO Auto-generated method stub
						//subCollName = new String[result.size()];
						try{	
							subCollList = new ArrayList<Item>();
							@SuppressWarnings("unused")
							int j=0;
							Log.i(getClass().getCanonicalName(),"result--------------- "+result);

							for(Item item:result){ 

								item_list.add(index+1, item);
								//index++;
								coll_adapter.updateList(item_list);
							}

							
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			});
		}
	}//end subcollectionTask
	
	
	/*public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}*/

}//end m_screen





