package com.kulkarni.gasmileagecalculator.data;

import java.util.Date;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Calendar;

public class Fillup {
	private static final String TAG = Fillup.class.getSimpleName();
	
	// private members
	private int      _id;
	private int      _car_id;
	private Date     _date;
	private double   _fuel_rate;
	private double   _fuel_volume;
	private double   _fuel_cost;
	private double   _fuel_mileage;
	private double   _odometer_reading;
	private boolean  _topped_up;
	private String   _notes;
	private Location _location;
	
	public static final String TABLE = "tblFillup";
	public static final String C_ID = BaseColumns._ID;
	public static final String C_LAST_MODIFIED = "lastModified";
	public static final String C_CAR_ID = "carId";
	public static final String C_FILLUP_DATE = "fillupDate";
	public static final String C_FUEL_RATE = "fuelRate";
	public static final String C_FUEL_VOLUME = "fuelVolume";
	public static final String C_FUEL_COST = "fuelCost";
	public static final String C_ODOMETER = "odometer";
	public static final String C_TOPPED_UP = "toppedUp";
	public static final String C_NOTES = "notes";
	public static final String C_LOCATION = "fillupLocation";
	
	public static final String createTable =
			"CREATE TABLE " + TABLE + "(" +
			C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			C_LAST_MODIFIED + " INTEGER NOT NULL, " +
			C_CAR_ID + " INTEGER NOT NULL, " +
			C_FILLUP_DATE + " INTEGER NOT NULL, " +
			C_FUEL_RATE + " REAL NOT NULL, " +
			C_FUEL_VOLUME + " REAL NOT NULL, " +
			C_FUEL_COST + " REAL NOT NULL, " +
			C_ODOMETER + " REAL NOT NULL, " +
			C_TOPPED_UP + " INTEGER NOT NULL, " +
			C_NOTES + " TEXT" +
			");";
	
	// Empty constructor
	public Fillup () {
	}
	
	public Fillup (Date date, double fuelRate, double fuelVolume, double odometer, boolean toppedUp) {
		_date        = date;
		_fuel_rate   = fuelRate;
		_fuel_volume = fuelVolume;
		_fuel_cost   = fuelRate * fuelVolume;
		_odometer_reading = odometer;
		_topped_up   = toppedUp;
	}
	
	public Fillup (Date date, double fuelRate, double fuelVolume, double odometer, boolean toppedUp, Location location, String notes) {
		this (date, fuelRate, fuelVolume, odometer, toppedUp);
		_location = location;
		_notes    = notes;
	}


	public int get_fillup_id() {
		return _id;
	}

	public int get_car_id() {
		return _car_id;
	}

	public Date get_fillup_date() {
		return _date;
	}

	public double get_fuel_rate() {
		return _fuel_rate;
	}

	public double get_fuel_volume() {
		return _fuel_volume;
	}

	public double get_fuel_cost() {
		return _fuel_cost;
	}

	public double get_odometer_reading() {
		return _odometer_reading;
	}

	public boolean is_fillup_topped_up() {
		return _topped_up;
	}

	public String get_notes() {
		return _notes;
	}

	public Location get_fillup_location() {
		return _location;
	}

	//Static methods
	public static double get_distance (Fillup currentFillup, Fillup previousFillup) {
		return (currentFillup._odometer_reading - previousFillup._odometer_reading);
	}
	
	public static double get_point_mileage (Fillup currentFillup, Fillup previousFillup) {
		
		if (currentFillup._topped_up == false)
			return -1.0;
		
		double distance = get_distance(currentFillup, previousFillup);
		return (distance / currentFillup._fuel_volume);
	}

	public void addToDb(SQLiteDatabase db) {
		
		ContentValues values = new ContentValues();
		values.put(C_CAR_ID, _car_id);
		values.put(C_FILLUP_DATE, _date.getTime());
		values.put(C_FUEL_COST, _fuel_cost);
		values.put(C_FUEL_RATE, _fuel_rate);
		values.put(C_FUEL_VOLUME, _fuel_volume);
		values.put(C_ODOMETER, _odometer_reading);
		values.put(C_TOPPED_UP, _topped_up ? 1 : 0);
		
		Calendar c = Calendar.getInstance();
		Date modifiedDate = c.getTime();
		
		values.put(C_LAST_MODIFIED, modifiedDate.getTime());
		
		try {
			db.insertOrThrow(Fillup.TABLE, null, values);
			Log.d(TAG, "Added new fillup");
		} catch (SQLException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}
}
