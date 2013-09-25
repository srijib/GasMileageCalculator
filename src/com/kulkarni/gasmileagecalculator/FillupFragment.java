/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.data.FillupData;
import com.kulkarni.gasmileagecalculator.data.Vehicle;
import com.kulkarni.gasmileagecalculator.data.VehicleData;
import com.kulkarni.gasmileagecalculator.helpers.TextValidator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Amey Kulkarni
 * 
 */
public class FillupFragment extends Fragment implements View.OnClickListener,
		OnItemSelectedListener {
	private static final String TAG = FillupData.class.getSimpleName();
	private Activity activity;
	private RefuelerApplication app;
	private FillupData fd;
	private VehicleData vd;

	public static Date mDate;
	public boolean notToppedUp = false;
	public int car_id;

	boolean errorRate = false;
	boolean errorVol = false;
	boolean errorOdo = false;

	private TextView dateTextView;
	private Spinner vehicleName;
	private Button enterFillup;
	private EditText fuelRate;
	private EditText fuelVolume;
	private EditText odometer;
	private CheckBox checkTopFillup;

	/**
	 * 
	 */
	public FillupFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fillup_fragment, container,
				false);

		activity = getActivity();
		app = (RefuelerApplication) activity.getApplication();
		fd = app.getFillups();
		vd = app.getVehicles();

		Calendar c = Calendar.getInstance();
		mDate = new Date(c.getTimeInMillis());

		dateTextView = (TextView) rootView
				.findViewById(R.id.textView_fillup_date);
		dateTextView.setText(getDateString());
		dateTextView.setOnClickListener(this);

		String[] adapterColumns = new String[] { Vehicle.C_NICKNAME };
		int[] adapterViewIds = new int[] { android.R.id.text1 };
		Cursor nicknames = vd.getVehicleNicknames();
		SimpleCursorAdapter spinnerAdapter = new SimpleCursorAdapter(activity,
				android.R.layout.simple_spinner_item, nicknames,
				adapterColumns, adapterViewIds, 0);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		vehicleName = (Spinner) rootView.findViewById(R.id.textVehicleName);
		vehicleName.setAdapter(spinnerAdapter);
		vehicleName.setOnItemSelectedListener(this);

		enterFillup = (Button) rootView.findViewById(R.id.button_enter_fillup);
		enterFillup.setOnClickListener(this);

		fuelRate = (EditText) rootView.findViewById(R.id.edit_fuel_rate);
		fuelRate.addTextChangedListener(new TextValidator(fuelRate) {

			@Override
			public void validate(EditText edit, String text) {
				if (!TextUtils.isEmpty(text) && Double.valueOf(text) <= 0.0) {
					edit.setError("Fuel Price must be greater than zero");
					errorRate = true;
				} else
					errorRate = false;
			}
		});

		fuelVolume = (EditText) rootView.findViewById(R.id.edit_fuel_volume);
		fuelVolume.addTextChangedListener(new TextValidator(fuelVolume) {

			@Override
			public void validate(EditText edit, String text) {
				errorVol = true;

				if (!TextUtils.isEmpty(text) && Double.valueOf(text) <= 0.0)
					edit.setError("Fuel Volume must be greater than zero");
				else
					errorVol = false;
			}
		});

		odometer = (EditText) rootView.findViewById(R.id.edit_odometer);
		odometer.addTextChangedListener(new TextValidator(odometer) {

			@Override
			public void validate(EditText edit, String text) {
				errorOdo = true;

				if (!TextUtils.isEmpty(text) && Double.valueOf(text) <= 0.0)
					edit.setError("Odometer must be greater than zero");
				else
					errorOdo = false;
			}
		});

		checkTopFillup = (CheckBox) rootView
				.findViewById(R.id.checkBox_fillup_topped);
		checkTopFillup.setChecked(notToppedUp);

		return rootView;
	}

	private static CharSequence getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("E, MMM d, y",
				Locale.getDefault());
		return sdf.format(mDate);
	}

	public void onDateClicked(View v) {
		Bundle currentDate = new Bundle();
		currentDate.putLong("setDate", mDate.getTime());

		DialogFragment newFragment = new DatePickerFragment();
		newFragment.setArguments(currentDate);

		newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.textView_fillup_date:
			onDateClicked(v);
			break;

		case R.id.checkBox_fillup_topped:
			onCheckedTop(v);
			break;

		case R.id.button_enter_fillup:
			onEnterFillupClicked(v);
			break;
		}
	}

	private void onEnterFillupClicked(View v) {
		String text;

		text = fuelRate.getText().toString();
		if (text.equals("")) {
			fuelRate.setError(getResources()
					.getString(R.string.fuel_rate_error));
			errorRate = true;
		}

		text = fuelVolume.getText().toString();
		if (text.equals("")) {
			fuelVolume.setError(getResources().getString(
					R.string.fuel_vol_error));
			errorVol = true;
		}

		text = odometer.getText().toString();
		if (text.equals("")) {
			odometer.setError(getResources().getString(R.string.odometer_error));
			errorOdo = true;
		}

		if (errorOdo || errorRate || errorVol) {
			Toast.makeText(activity, "Make sure the entered values are valid",
					Toast.LENGTH_LONG).show();
			return;
		}

		double fuelRate = Double
				.parseDouble(this.fuelRate.getText().toString());
		double fuelVolume = Double.parseDouble(this.fuelVolume.getText()
				.toString());
		double odometer = Double
				.parseDouble(this.odometer.getText().toString());

		Fillup fillup = new Fillup(mDate, fuelRate, fuelVolume, odometer,
				!notToppedUp);
		fillup.set_car_id(car_id);

		if (FillupData.fillups.add(fillup)) {
			try {
				fd.addFillup(fillup);
				Toast.makeText(activity, "Fillup added", Toast.LENGTH_SHORT)
						.show();
				clearForm();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}

			// ListView history = (ListView)
			// activity.findViewById(R.id.history_list);
			// CursorAdapter adapter = (CursorAdapter) history.getAdapter();
			// adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(activity, "Error adding fillup", Toast.LENGTH_LONG)
					.show();
			return;
		}
	}

	private void clearForm() {
		fuelRate.getText().clear();
		fuelVolume.getText().clear();
		odometer.getText().clear();

		mDate = null;
		Calendar c = Calendar.getInstance();
		mDate = new Date(c.getTimeInMillis());
		dateTextView.setText(getDateString());

		notToppedUp = false;
		checkTopFillup.setChecked(notToppedUp);
	}

	private void onCheckedTop(View v) {
		checkTopFillup = (CheckBox) v;

		notToppedUp = !notToppedUp;
		checkTopFillup.setChecked(notToppedUp);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor c = (Cursor) parent.getItemAtPosition(position);
		car_id = c.getInt(c.getColumnIndexOrThrow(Vehicle.C_ID));
		Toast.makeText(activity,
				"Selected vehicle id: " + Integer.toString(car_id),
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Long curDate = getArguments().getLong("setDate");

			final Calendar c = Calendar.getInstance();
			c.setTimeInMillis(curDate);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Calendar c = Calendar.getInstance();
			c.set(year, month, day);

			if (isDateInFuture(c)) {
				Toast.makeText(getActivity(), "Date cannot be in the future",
						Toast.LENGTH_SHORT).show();
				return;
			}

			mDate = new Date(c.getTimeInMillis());

			TextView dateTextView = (TextView) getActivity().findViewById(
					R.id.textView_fillup_date);
			dateTextView.setText(getDateString());
		}

		private boolean isDateInFuture(Calendar c) {
			Calendar today = Calendar.getInstance();

			return today.before(c);
		}
	}

}
