package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupData;
import com.kulkarni.gasmileagecalculator.data.VehicleData;
import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

import android.app.Application;

public class RefuelerApplication extends Application {

	DbOpenHelper dbHelper;
	FillupData fillups;
	VehicleData vehicles;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		dbHelper = new DbOpenHelper(getApplicationContext());
		fillups  = new FillupData(getApplicationContext(), dbHelper);
		vehicles = new VehicleData(getApplicationContext(), dbHelper); 
	}
	
	public DbOpenHelper getDbHelper() {
		return dbHelper;
	}

	public FillupData getFillups() {
		return fillups;
	}
	
	public VehicleData getVehicles() {
		return vehicles;
	}
	
}
