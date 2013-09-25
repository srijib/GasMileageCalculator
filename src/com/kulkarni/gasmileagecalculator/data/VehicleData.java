package com.kulkarni.gasmileagecalculator.data;

import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VehicleData {

	private static final String TAG = VehicleData.class.getSimpleName();

	private Context context;
	private DbOpenHelper dbHelper;

	public VehicleData(Context context, DbOpenHelper dbHelper) {
		this.context = context;
		this.dbHelper = dbHelper;
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
	
	public Cursor getVehicleNicknames () {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String[] columns = new String[] { Vehicle.C_ID, Vehicle.C_NICKNAME };
		Cursor c = db.query(Vehicle.TABLE, columns, null, null, null, null, Vehicle.C_NICKNAME + " ASC");
		
		return c;
	}

	public String getVehicleNickname(int vehicle_id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String[] columns = { Vehicle.C_NICKNAME };
		String[] whereArgs = new String[] { Integer.toString(vehicle_id) };
		Cursor c = db.query(Vehicle.TABLE, columns, Vehicle.C_ID + " = ?", whereArgs, null, null, null);
		
		int nicknameIndex = c.getColumnIndexOrThrow(Vehicle.C_NICKNAME);
		String nickname = c.getString(nicknameIndex);
		
		return nickname;
	}
}
