package com.kulkarni.gasmileagecalculator.data;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

public class FillupData {
	private static final String TAG = FillupData.class.getSimpleName();

	public static Vector<Fillup> fillups;
	Context context;
	DbOpenHelper dbHelper;

	private static double average_mileage = 0.0;
	private static double total_distance = 0.0;
	private static double total_volume = 0.0;
	private static double total_used_volume = 0.0;

	public FillupData(Context context, DbOpenHelper dbHelper) {
		this.context = context;
		this.dbHelper = dbHelper;

		SQLiteDatabase db;

		try {
			db = dbHelper.getReadableDatabase();
			db.close();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		if (fillups == null) {
			fillups = new Vector<Fillup>();
		}
	}

	public static double getAverage_mileage() {
		return average_mileage;
	}

	public static double getTotal_distance() {
		return total_distance;
	}

	public static double getTotal_volume() {
		return total_volume;
	}

	public static double getTotal_used_volume() {
		return total_used_volume;
	}

	public int size() {
		return fillups.size();
	}

	public void calculate_total_distance() {
		Fillup lastFillup = fillups.lastElement();
		Fillup firstFillup = fillups.firstElement();
		total_distance = lastFillup.get_odometer_reading()
				- firstFillup.get_odometer_reading();
	}

	public void calculate_total_fuel_volume() {
		int size = fillups.size();
		double volume = 0.0;

		for (int i = 0; i < size; i++) {
			volume += fillups.elementAt(i).get_fuel_volume();
		}

		total_volume = volume;
	}

	public void calculate_total_used_fuel_volume() {
		int size = fillups.size();
		double volume = 0.0;

		for (int i = 1; i < size; i++) {
			volume += fillups.elementAt(i).get_fuel_volume();
		}

		total_used_volume = volume;
	}

	public void calculate_overall_mileage() {
		average_mileage = total_distance / total_used_volume;
	}

	public double get_mileage_for_fillup(int position) throws Exception {
		int size = fillups.size();

		if (position < 0 || position > size) {
			throw new Exception(
					"position is not within the size of the fillup vector");
		} else if (position == 0) {
			return -1.0;
		}

		Fillup currentFillup = fillups.elementAt(position);
		Fillup previousFillup = fillups.elementAt(position - 1);

		double distance = 0.0;

		if (currentFillup.is_fillup_topped_up()
				&& previousFillup.is_fillup_topped_up()) {
			distance = Fillup.get_distance(currentFillup, previousFillup);
			return (distance / currentFillup.get_fuel_volume());
		}

		if (currentFillup.is_fillup_topped_up()
				&& !previousFillup.is_fillup_topped_up()) {
			double volume = 0.0;

			for (int i = position - 1; i >= 0; i--) {
				Fillup thisFillup = fillups.elementAt(i);

				volume += fillups.elementAt(i + 1).get_fuel_volume();

				if (thisFillup.is_fillup_topped_up()) {
					distance = Fillup.get_distance(currentFillup, thisFillup);
					return (distance / volume);
				}
			}

			return -2.0;
		}

		if (!currentFillup.is_fillup_topped_up()) {
			return -3.0;
		}

		return Double.NaN;
	}

	public double get_total_cost() {
		int size = fillups.size();
		double cost = 0.0;

		for (int i = 0; i < size; i++) {
			cost += fillups.elementAt(i).get_fuel_cost();
		}

		return cost;
	}

	public double get_used_fuel_cost() {
		int size = fillups.size();
		double cost = 0.0;

		for (int i = 1; i < size; i++) {
			cost += (fillups.elementAt(i).get_fuel_volume() * fillups
					.elementAt(i - 1).get_fuel_cost());
		}

		return cost;
	}

	public Fillup getItem(int position) {
		if (position >= 0)
			return fillups.get(position);
		else
			return null;
	}

	public void addFillup(Fillup newFillup) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		newFillup.addToDb(db);

		db.close();
	}

	public Cursor getFillups() {
		String[] columns = { Fillup.C_ID,        Fillup.C_CAR_ID,    Fillup.C_FILLUP_DATE,
							 Fillup.C_FUEL_COST, Fillup.C_FUEL_RATE, Fillup.C_FUEL_VOLUME,
							 Fillup.C_TOPPED_UP };

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.query(Fillup.TABLE, columns, null, null, null, null,
				Fillup.C_FILLUP_DATE + " DESC");
		return c;
	}
}
