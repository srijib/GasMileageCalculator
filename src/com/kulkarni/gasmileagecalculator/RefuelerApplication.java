package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupData;
import com.kulkarni.gasmileagecalculator.data.VehicleData;

import android.app.Application;
import android.content.Context;

public class RefuelerApplication extends Application {

	FillupData fillups;
	VehicleData vehicles;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Context context = this;
		
		fillups  = new FillupData(context);
		vehicles = new VehicleData(context); 
	}

	public FillupData getFillups() {
		return fillups;
	}
	
	public VehicleData getVehicles() {
		return vehicles;
	}
}
