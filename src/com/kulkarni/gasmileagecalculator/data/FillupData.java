package com.kulkarni.gasmileagecalculator.data;

import java.util.Vector;

import com.kulkarni.gasmileagecalculator.data.Fillup;

public class FillupData {
	public static Vector<Fillup> fillups;
	
	public FillupData() {
		// TODO Auto-generated constructor stub
		if (fillups == null) {
			fillups = new Vector<Fillup>();
		}
	}
	
	public int size() {
		return fillups.size();
	}
	
	public double get_total_distance () {
		Fillup lastFillup  = fillups.lastElement();
		Fillup firstFillup = fillups.firstElement();
		return (lastFillup.get_fillup_odometer_reading() - firstFillup.get_fillup_odometer_reading());
	}
	
	public double get_total_fuel_volume () {
		int size = fillups.size();
		double volume = 0.0;
		
		for (int i = 0; i < size; i++) {
			volume += fillups.elementAt(i).get_fillup_fuel_volume();
		}
		
		return volume;
	}
	
	public double get_total_used_fuel_volume () {
		int size = fillups.size();
		double volume = 0.0;
		
		for (int i = 1; i < size; i++) {
			volume += fillups.elementAt(i).get_fillup_fuel_volume();
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
			cost += fillups.elementAt(i).get_fillup_fuel_cost();
		}
		
		return cost;
	}
	
	public double get_used_fuel_cost () {
		int size = fillups.size();
		double cost = 0.0;
		
		for (int i = 1; i < size; i++) {
			cost += (fillups.elementAt(i).get_fillup_fuel_volume() * fillups.elementAt(i - 1).get_fillup_fuel_cost());
		}
		
		return cost;
	}

	public Fillup getItem(int position) {
		// TODO Auto-generated method stub
		if (position >= 0)
			return fillups.get(position);
		else
			return null;
	}
}
