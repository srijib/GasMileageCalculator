package com.kulkarni.gasmileagecalculator.data;

import java.util.Vector;

public class FillupVector {
	Vector<Fillup> fillups;
	
	public double get_total_distance () {
		int size = fillups.size();
		
		return (fillups.elementAt(size - 1)._fillup_odometer_reading - fillups.elementAt(0)._fillup_odometer_reading);
	}
	
	public double get_total_fuel_volume () {
		int size = fillups.size();
		double volume = 0.0;
		
		for (int i = 0; i < size; i++) {
			volume += fillups.elementAt(i)._fillup_fuel_volume;
		}
		
		return volume;
	}
	
	public double get_total_used_fuel_volume () {
		int size = fillups.size();
		double volume = 0.0;
		
		for (int i = 1; i < size; i++) {
			volume += fillups.elementAt(i)._fillup_fuel_volume;
		}
		
		return volume;
	}
	
	public double get_overall_mileage () {
		return (get_total_distance() / get_total_used_fuel_volume());
	}
	
	public double get_total_cost () {
		int size = fillups.size();
		double cost = 0.0;
		
		for (int i = 0; i < size; i++) {
			cost += fillups.elementAt(i)._fillup_fuel_cost;
		}
		
		return cost;
	}
	
	public double get_used_fuel_cost () {
		int size = fillups.size();
		double cost = 0.0;
		
		for (int i = 1; i < size; i++) {
			cost += (fillups.elementAt(i)._fillup_fuel_volume * fillups.elementAt(i - 1)._fillup_fuel_cost);
		}
		
		return cost;
	}
}
