package com.example.zoteroapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoteroapp.model.Item;
import com.example.zoteroapp.services.HttpRetriever;
import com.example.zoteroapp.services.ItemSeeker;
import com.example.zoteroapp.services.ServerCred;

public class MainZoteroActivity extends Activity implements OnClickListener {

	protected static final String TAG = "MainZoteroActivity";



	String debug_version;

	Button bttn_login;
	Button bttn_next , bttn_change_user;
	TextView tv_userID , tv_userKEY;
	EditText et_userID , et_userKEY;

	String q = null;
	ProgressDialog proDialog;
	public String query = null;
	public static int flag =0;

	Intent intent;

	Item item;
	Context context ;

	ArrayList<String> zapi=new ArrayList<String>();

	ArrayList<String> list_parent_collection_key = new ArrayList<String>();
	ArrayList<String> list_parent_collection_title = new ArrayList<String>();

	HttpRetriever retriever = new HttpRetriever();

	String s;
	String parent_coll_key;
	String parent_coll_title;

	Bundle b;
	ServerCred<Item> itemseeker = new ItemSeeker();
	Spinner spinner ;

	public static final String MY_PREFS_NAME = "MyPrefsCred";

	public static String USERID ;
	public static String USERKEY ;

	String key;// = "KLhSEwv3q04CyXOUnzqeOKND";  //Antoniou 6338 1bGOSzqD4zOnMhiaASg7w9pd
	String id ;// = "1737513";

	FileOutputStream fout;
	FileInputStream fin;

	String MYKEY = "USER KEY";
	String MYID = "USER ID";

	String user_key;

	String user_id;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_zotero);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		debug_version = "24/08/14 Test ZoteroApp: 2 try";


		bttn_login = (Button)findViewById(R.id.login);
		bttn_next = (Button)findViewById(R.id.next);
		bttn_change_user = (Button)findViewById(R.id.bttn_change_user);

		bttn_next.setVisibility(View.GONE);

		tv_userID = (TextView)findViewById(R.id.tv_userID);
		tv_userKEY = (TextView)findViewById(R.id.tv_userKEY);

		et_userID = (EditText)findViewById(R.id.et_userID);
		et_userKEY = (EditText)findViewById(R.id.et_userKEY);



		//Toast.makeText(getApplicationContext(), readKEY(), Toast.LENGTH_SHORT).show();

		if(readKEY()==""){
			tv_userKEY.setVisibility(View.VISIBLE);
			et_userKEY.setVisibility(View.VISIBLE);

		}
		else{
			tv_userKEY.setVisibility(View.GONE);
			et_userKEY.setVisibility(View.GONE);
		}

		bttn_next.setOnClickListener(this); 
		bttn_login.setOnClickListener(this);
		bttn_change_user.setOnClickListener(this);



	}// END onCreate()

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}
	}



	public void writeID(String myID){

		try{
			fout = openFileOutput(MYID, Context.MODE_PRIVATE);
			fout.write(myID.getBytes());
			fout.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String readID(){

		StringBuffer buffer = new StringBuffer("");
		try{
			fin = openFileInput(MYID);
			InputStreamReader is = new InputStreamReader(fin);
			BufferedReader reader = new BufferedReader(is);

			String readString = reader.readLine();

			while(readString!=null){
				buffer.append(readString);
				readString = reader.readLine();
			}
			is.close();

			Log.i(getClass().getCanonicalName(),"------------readID--------------"+buffer.toString());

		}catch(Exception e){
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public void writeKEY(String myKEY){

		try{
			fout = openFileOutput(MYKEY, Context.MODE_PRIVATE);
			fout.write(myKEY.getBytes());
			fout.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String readKEY(){

		StringBuffer buffer = new StringBuffer("");
		try{
			fin = openFileInput(MYKEY);
			InputStreamReader is = new InputStreamReader(fin);
			BufferedReader reader = new BufferedReader(is);

			String readString = reader.readLine();

			while(readString!=null){
				buffer.append(readString);
				readString = reader.readLine();
			}
			is.close();

			Log.i(getClass().getCanonicalName(),"------------readKEY--------------"+buffer.toString());

		}catch(Exception e){
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public void onClick(View v){
		if(v.getId()==R.id.login){

			//longToast(debug_version);
			id = et_userID.getText().toString();
			key = et_userKEY.getText().toString();
			//key = et_userKEY.getText().toString();
			//key = "1bGOSzqD4zOnMhiaASg7w9pd";
			USERID = id;



			if(readKEY()==""){
				writeKEY(key);
				USERKEY = readKEY();
			}
			else{

				USERKEY = readKEY();
			}
			flag=1;
			performItem();

		}
		else if(v.getId()==R.id.next){
			flag=2;
			//performItem();
			//readId();
		}
		else if(v.getId()==R.id.bttn_change_user){
			et_userKEY.setVisibility(View.VISIBLE);
			tv_userKEY.setVisibility(View.VISIBLE);
			context = getApplicationContext();
			context.deleteFile(MYKEY);
		}

	}
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	public void performItem(){
		//ItemTask item_task = new ItemTask();
		new ItemTask().execute();
	}



	private class ItemTask extends AsyncTask<String, Void , ArrayList<Item>>{


		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			Log.d(getClass().getName(), "doInbackground()");
			// TODO Auto-generated method stub
			String query = params.toString();

			ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.findCollection(query);
			return it_seeker;
		}

		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					try{
						if(MainZoteroActivity.flag==1){
							intent = new Intent();
						}
					}catch(NullPointerException n){
						n.printStackTrace();
					}

					try{

						if(MainZoteroActivity.flag==1){

							intent = new Intent(MainZoteroActivity.this , m_screen.class);
							intent.putExtra("items", result);

							Log.d(getClass().getSimpleName(),"result---------------  "+result);

							for(Item i:result){
								s= i.getZapi_key();
								Log.d(getClass().getSimpleName(),"zapi key  "+s);
								zapi.add(s);

							}
							for(Item item:result){

								if(!"0".equals(item.getChildrenNum())){

									parent_coll_key = item.getZapi_key();
									parent_coll_title = item.getTitle();

									list_parent_collection_key.add(parent_coll_key);
									list_parent_collection_title.add(parent_coll_title);

									Log.i(TAG,"The parent collection key: "+parent_coll_key);
									Log.i(TAG,"The parent collection title: "+parent_coll_title);

								}
							}
							Log.d("H lista me ta zapi key"+zapi, s);
							intent.putStringArrayListExtra("zapi_keys", zapi);
							intent.putStringArrayListExtra("parent collections keys", list_parent_collection_key);
							intent.putStringArrayListExtra("parent collections titles", list_parent_collection_title);

							//startActivity(intent);
							startNextActivity();
						}

					}catch(Exception ex){
						ex.printStackTrace();
					}
				}

			});

		}

	}
	public void startNextActivity(){

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_zotero, menu);
		return true;
	}



}
