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
		super.onCreate();
		
		dbHelper = new DbOpenHelper (this);
		
		fillups  = new FillupData(dbHelper);
		vehicles = new VehicleData(dbHelper); 
	}

	public FillupData getFillups() {
		return fillups;
	}
	
	public VehicleData getVehicles() {
		return vehicles;
	}
	
	public DbOpenHelper getDbHelper() {
		return dbHelper;
	}
}
