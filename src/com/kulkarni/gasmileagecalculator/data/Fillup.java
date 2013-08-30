package com.kulkarni.gasmileagecalculator.data;

import java.util.Date;

import android.location.Location;

public class Fillup {
	private static final String TAG = Fillup.class.getSimpleName();
	
	// private members
	private int      _fillup_id;
	private int      _car_id;
	private Date     _fillup_date;
	private double   _fillup_fuel_rate;
	private double   _fillup_fuel_volume;
	private double   _fillup_fuel_cost;
	private double   _fillup_odometer_reading;
	private boolean  _fillup_topped_up;
	private String   _fillup_notes;
	private Location _fillup_location;
	
	public static final String tableName = "tblFillup";
	public static final String createTable =
			"CREATE TABLE " + tableName +
			" _id INTEGER PRIMARY KEY AUTOINCREMENT," +
			" _modified_time INTEGER NOT NULL" +
			" car_id INTEGER NOT NULL," +
			" fillup_date INTEGER NOT NULL," +
			" fuel_rate REAL NOT NULL," +
			" fuel_volume REAL NOT NULL," +
			" fuel_cost REAL NOT NULL," +
			" odometer REAL NOT NULL," +
			" topped_up INTEGER NOT NULL," +
			" notes TEXT" +
			");";
	
	// Empty constructor
	public Fillup () {
	}
	
	public Fillup (Date date, double fuelRate, double fuelVolume, double odometer, boolean toppedUp) {
		_fillup_date        = date;
		_fillup_fuel_rate   = fuelRate;
		_fillup_fuel_volume = fuelVolume;
		_fillup_fuel_cost   = fuelRate * fuelVolume;
		_fillup_odometer_reading = odometer;
		_fillup_topped_up   = toppedUp;
	}
	
	public Fillup (Date date, double fuelRate, double fuelVolume, double odometer, boolean toppedUp, Location location, String notes) {
		this (date, fuelRate, fuelVolume, odometer, toppedUp);
		_fillup_location = location;
		_fillup_notes    = notes;
	}


	public int get_fillup_id() {
		return _fillup_id;
	}

	public int get_car_id() {
		return _car_id;
	}

	public Date get_fillup_date() {
		return _fillup_date;
	}

	public double get_fillup_fuel_rate() {
		return _fillup_fuel_rate;
	}

	public double get_fillup_fuel_volume() {
		return _fillup_fuel_volume;
	}

	public double get_fillup_fuel_cost() {
		return _fillup_fuel_cost;
	}

	public double get_fillup_odometer_reading() {
		return _fillup_odometer_reading;
	}

	public boolean is_fillup_topped_up() {
		return _fillup_topped_up;
	}

	public String get_fillup_notes() {
		return _fillup_notes;
	}

	public Location get_fillup_location() {
		return _fillup_location;
	}

	//Static methods
	public static double get_distance (Fillup currentFillup, Fillup previousFillup) {
		return (currentFillup._fillup_odometer_reading - previousFillup._fillup_odometer_reading);
	}
	
	public static double get_point_mileage (Fillup currentFillup, Fillup previousFillup) {
		
		if (currentFillup._fillup_topped_up == false)
			return -1.0;
		
		double distance = get_distance(currentFillup, previousFillup);
		return (distance / currentFillup._fillup_fuel_volume);
	}
}
