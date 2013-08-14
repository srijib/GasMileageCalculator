package com.kulkarni.gasmileagecalculator.data;

import java.util.Date;

import android.location.Location;

public class Fillup {
	
	// private members
	public int      _fillup_id;
	public int      _car_id;
	public Date     _fillup_date;
	public double   _fillup_fuel_rate;
	public double   _fillup_fuel_volume;
	public double   _fillup_fuel_cost;
	public double   _fillup_odometer_reading;
	public boolean  _fillup_topped_up;
	public String   _fillup_notes;
	
	// Future members
	public Location _fillup_location;
	
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
		
		if (currentFillup._fillup_topped_up == false)
			return -1.0;
		
		double distance = get_distance(currentFillup, previousFillup);
		return (distance / currentFillup._fillup_fuel_volume);
	}
}
