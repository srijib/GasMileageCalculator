package com.kulkarni.gasmileagecalculator.data;

import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class VehicleData {

	private static final String TAG = VehicleData.class.getSimpleName();

	private Context context;
	private DbOpenHelper dbHelper;

	public VehicleData(Context context) {
		this.context = context;
		dbHelper = new DbOpenHelper(context);
	}

	public void insertVehicle(Vehicle vehicle) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		vehicle.insert(db);
		db.close();
	}
	
	public static void insertDefaultVehicle (SQLiteDatabase db) {
		Vehicle defaultVehicle = Vehicle.getDefaultVehicle();
		defaultVehicle.insert(db);
	}
}
