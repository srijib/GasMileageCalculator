package com.kulkarni.gasmileagecalculator;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.data.FillupData;
import com.kulkarni.gasmileagecalculator.data.VehicleData;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HistoryCursorAdapter extends CursorAdapter implements ListAdapter {

	private static final String TAG = HistoryCursorAdapter.class
			.getSimpleName();

	Context context;
	RefuelerApplication app;
	Resources resources;
	LayoutInflater inflater;
	FillupData fd;
	VehicleData vd;

	TextView txtFillupDate;
	TextView txtMileage;
	TextView txtVehicle;
	TextView txtVolume;
	TextView txtFuelRate;
	TextView txtFuelCost;

	// Column Indices
	int cDateIndex;
	int cVehicleIndex;
	int cVolumeIndex;
	int cRateIndex;
	int cCostIndex;

	public HistoryCursorAdapter(Context context, RefuelerApplication app, Cursor c) {
		super(context, c, 0);

		this.context = context;
		this.app     = app;

		try {
			resources = context.getResources();
			inflater = LayoutInflater.from(context);

			fd = app.getFillups();
			vd = app.getVehicles();

			cDateIndex = c.getColumnIndexOrThrow(Fillup.C_FILLUP_DATE);
			cVehicleIndex = c.getColumnIndexOrThrow(Fillup.C_CAR_ID);
			cVolumeIndex = c.getColumnIndexOrThrow(Fillup.C_FUEL_VOLUME);
			cRateIndex = c.getColumnIndexOrThrow(Fillup.C_FUEL_RATE);
			cCostIndex = c.getColumnIndexOrThrow(Fillup.C_FUEL_COST);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		txtFillupDate = (TextView) view.findViewById(R.id.txt_date);
		txtMileage = (TextView) view.findViewById(R.id.txt_mileage);
		txtVehicle = (TextView) view.findViewById(R.id.txt_vehicle);
		txtVolume = (TextView) view.findViewById(R.id.txt_fuel_volume);
		txtFuelRate = (TextView) view.findViewById(R.id.txt_fuel_rate);
		txtFuelCost = (TextView) view.findViewById(R.id.txt_fillup_cost);

		long time = cursor.getLong(cDateIndex);
		Date fillupDate = new Date(time);
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		txtFillupDate.setText(sdf.format(fillupDate));

		int vehicle_id = cursor.getInt(cVehicleIndex);
		String vehicle_nickname = vd.getVehicleNickname(vehicle_id);
		txtVehicle.setText(vehicle_nickname.toUpperCase(Locale.US));

		double volume = cursor.getDouble(cVolumeIndex);
		String vol_txt = String.format(Locale.getDefault(), "%.2f %s", volume,
				resources.getString(R.string.volume_unit));
		txtVolume.setText(vol_txt);

		double rate = cursor.getDouble(cRateIndex);
		String rate_txt = String.format(Locale.getDefault(), "%s%.2f per %s",
				resources.getString(R.string.currency_symbol), rate,
				resources.getString(R.string.volume_unit));
		txtFuelRate.setText(rate_txt);

		double cost = cursor.getDouble(cCostIndex);
		String cost_txt = String.format(Locale.getDefault(), "%s%.2f",
				resources.getString(R.string.currency_symbol), cost);
		txtFuelCost.setText(cost_txt);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		try {
			View v = inflater.inflate(R.layout.history_adapter, parent, false);
			return v;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}

}
