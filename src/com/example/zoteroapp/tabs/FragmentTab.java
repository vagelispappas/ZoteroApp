package com.example.zoteroapp.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zoteroapp.R;

public class FragmentTab extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 View view = inflater.inflate(R.layout.tab1, container, false);
        
         
		return view;// super.onCreateView(inflater, container, savedInstanceState);
	}
	

}
