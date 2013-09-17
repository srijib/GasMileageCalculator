package com.kulkarni.gasmileagecalculator.data;

import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class Vehicle {
	private final String TAG = Vehicle.class.getSimpleName();

	// Members
	private int vehicle_id;
	private int year;
	private String make;
	private String model;
	private String nickname;

	// Table
	public static final String TABLE = "tblVehicle";
	public static final String C_ID = BaseColumns._ID;
	public static final String C_LAST_MODIFIED = "lastModified";
	public static final String C_YEAR = "year";
	public static final String C_MAKE = "make";
	public static final String C_MODEL = "model";
	public static final String C_NICKNAME = "nickname";

	public static final String createTable = "CREATE TABLE " + TABLE + " ("
			+ C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ C_LAST_MODIFIED + " INTEGER NOT NULL, " + C_YEAR
			+ " INTEGER, " + C_MAKE + " TEXT NOT NULL, " + C_MODEL
			+ " TEXT NOT NULL, " + C_NICKNAME + " TEXT" + ")";

	public int getVehicle_id() {
		return vehicle_id;
	}

	public int getYear() {
		return year;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Vehicle (int year, String make, String model) {
		this.year  = year;
		this.make  = make;
		this.model = model;
		
		nickname = (make + " " + model);
	}
	
	public static Vehicle getDefaultVehicle () {
		Calendar c = Calendar.getInstance();
		int year = c.get (Calendar.YEAR);
		
		Vehicle defaultVehicle = new Vehicle(year, "Default", "Default");
		defaultVehicle.setNickname("Default");
		
		return defaultVehicle;
	}
	
	public void insert(SQLiteDatabase db) {
		ContentValues values = new ContentValues();

		Calendar c = Calendar.getInstance();
		Date modifiedDate = c.getTime();

		values.put(C_LAST_MODIFIED, modifiedDate.getTime());
		values.put(C_YEAR, year);
		values.put(C_MAKE, make);
		values.put(C_MODEL, model);
		values.put(C_NICKNAME, nickname);

		try {
			db.insertOrThrow(TABLE, null, values);
			Log.d(TAG, "Added new vehicle");
		} catch (SQLException e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
