package com.kulkarni.gasmileagecalculator.data;

import java.util.Date;

import android.location.Location;

public class Fillup {
	
	// private members
	int      _fillup_id;
	int      _car_id;
	Date     _fillup_date;
	double   _fillup_fuel_rate;
	double   _fillup_fuel_volume;
	double   _fillup_fuel_cost;
	double   _fillup_odometer_reading;
	boolean  _fillup_topped_up;
	String   _fillup_notes;
	
	// Future members
	Location _fillup_location;
	
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


	//Static methods
	public static double get_distance (Fillup currentFillup, Fillup previousFillup) {
		return (currentFillup._fillup_odometer_reading - previousFillup._fillup_odometer_reading);
	}
	
	public static double get_point_mileage (Fillup currentFillup, Fillup previousFillup) {
		double distance = get_distance(currentFillup, previousFillup);
		return (distance / currentFillup._fillup_fuel_volume);
	}
}
