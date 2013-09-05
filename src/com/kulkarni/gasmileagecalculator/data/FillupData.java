package com.kulkarni.gasmileagecalculator.data;

import java.util.Vector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

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
	
	public double get_mileage_for_fillup (int position) {
		int size = fillups.size();
		
		if (position < 0 || position > size) {
			throw new Exception ("position is not within the size of the fillup vector");
		}
		else if (position == 0) {
			return -1.0;
		}
		
		Fillup currentFillup  = fillups.elementAt(i);
		Fillup previousFillup = fillups.elementAt(i - 1);
		
		double distance = 0.0;
		
		if (currentFillup.is_fillup_topped_up() && previousFillup.is_fillup_topped_up()) {
			distance = Fillup.get_distance (currentFillup, previousFillup);
			return (distance / currentFillup.get_fillup_fuel_volume ());
		}
		
		if (currentFillup.is_fillup_topped_up() && !previousFillup.is_fillup_topped_up()) {
			double volume = 0.0;
			
			for (int i = position - 1; i >= 0; i--) {
				Fillup thisFillup = fillups.elementAt(i);
				
				volume += fillups.elementAt(i + 1);
				
				if (thisFillup.is_fillup_topped_up()) {
					distance = Fillup.get_distance (currentFillup, thisFillup);
					return (distance / volume);
				}
			}
			
			return -2.0;
		}
		
		if (!currentFillup.is_fillup_topped_up()) {
			return -3.0;
		}
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
	
	public void addFillup (Fillup newFillup, Context context) {
		DbOpenHelper dbHelper = new DbOpenHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
	}
}
