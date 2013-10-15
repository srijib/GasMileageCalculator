package com.kulkarni.gasmileagecalculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.data.FillupData;
import com.kulkarni.gasmileagecalculator.data.VehicleData;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {
	
	private static final String TAG = HistoryAdapter.class.getSimpleName();

	private Activity activity;
	private RefuelerApplication app;
	private FillupData fillups;
	private VehicleData vehicles;
	private static LayoutInflater inflater = null;

	public HistoryAdapter(Activity a) {
		activity = a;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		app = (RefuelerApplication) activity.getApplication();
		fillups = app.getFillups();
		vehicles = app.getVehicles();
	}

	@Override
	public int getCount() {
		return fillups.size();
	}

	@Override
	public Object getItem(int position) {
		return fillups.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(R.layout.history_adapter, null);
		}

		TextView textDate = (TextView) view.findViewById(R.id.txt_date);
		TextView textMPG = (TextView) view.findViewById(R.id.txt_mileage);
		TextView textVehicle = (TextView) view.findViewById(R.id.txt_vehicle);
		TextView textVolume = (TextView) view
				.findViewById(R.id.txt_fuel_volume);
		TextView textRate = (TextView) view.findViewById(R.id.txt_fuel_rate);
		TextView textCost = (TextView) view.findViewById(R.id.txt_fillup_cost);

		Fillup fillup = fillups.getItem(position);
		Fillup previousFillup = fillups.getItem(position - 1);

		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		textDate.setText(sdf.format(fillup.get_fillup_date()));

		textVolume.setText(String.format("%.2f gal",
				fillup.get_fuel_volume()));
		textRate.setText(String.format("$%.2f/gal",
				fillup.get_fuel_rate()));
		textCost.setText(String.format("$%.2f", fillup.get_fuel_cost()));

		if (previousFillup != null && fillups.size() > 1) {
			try {
				double mileage = fillups.get_mileage_for_fillup(position);
				double average = FillupData.getAverage_mileage();

				textMPG.setText(String.format("%.2f mpg", mileage));
				if (mileage > average)
					textMPG.setTextColor(activity.getResources().getColor(
							R.color.fillup_above_avg));
				else if (mileage < average)
					textMPG.setTextColor(activity.getResources().getColor(
							R.color.fillup_below_avg));
				else
					textMPG.setTextColor(activity.getResources().getColor(
							R.color.fillup_avg));
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		} else {
			textMPG.setText("N/A");
			textMPG.setTextColor(activity.getResources().getColor(
					android.R.color.darker_gray));
		}
		
		
		String nickname = vehicles.getVehicleNickname(fillup.get_car_id());
		if (!nickname.equals(null))
			textVehicle.setText(nickname.toUpperCase(Locale.getDefault()));
		else
			textVehicle.setText(activity.getResources().getString(R.string.vehicle_nickname).toUpperCase(Locale.getDefault()));
		
		return view;
	}

}
