package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupData;

import android.app.Application;

public class RefuelerApplication extends Application {

	FillupData fillups;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		fillups = new FillupData(getApplicationContext());
	}

	public FillupData getFillups() {
		return fillups;
	}
}
