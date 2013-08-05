/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * @author Amey Kulkarni
 *
 */
public class FillupFragment extends Fragment implements
	View.OnClickListener
{
	
	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		
		@Override
		public Dialog onCreateDialog (Bundle savedInstanceState) {
			Long curDate = getArguments().getLong("setDate");
			
			final Calendar c = Calendar.getInstance();
			c.setTimeInMillis(curDate);
			int year  = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day   = c.get(Calendar.DAY_OF_MONTH);
			
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// TODO Auto-generated method stub
			Calendar c = Calendar.getInstance();
			c.set(year, month, day);
			mDate = new Date (c.getTimeInMillis());
			
			TextView dateTextView = (TextView) getActivity().findViewById(R.id.textView_fillup_date);
			dateTextView.setText(getDateString());
		}
	}

	public Date mDate;
	public boolean notToppedUp = false;

	/**
	 * 
	 */
	public FillupFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fillup_fragment, container, false);
		
		Calendar c = Calendar.getInstance();
		mDate = new Date (c.getTimeInMillis());
		
		TextView dateTextView = (TextView) rootView.findViewById(R.id.textView_fillup_date);
		dateTextView.setText(getDateString ());
		dateTextView.setOnClickListener(this);
		
		TextView vehicleName = (TextView) rootView.findViewById(R.id.textVehicleName);
		vehicleName.setOnClickListener(this);
		
		return rootView;
	}

	
	private CharSequence getDateString () {
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		return sdf.format(mDate);
	}
	
	public void onDateClicked (View v) {
		Bundle currentDate = new Bundle ();
		currentDate.putLong("setDate", mDate.getTime());
		
		DialogFragment newFragment = new DatePickerFragment ();
		newFragment.setArguments(currentDate);
		
		newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.textView_fillup_date:
			onDateClicked(v);
			break;
			
		case R.id.checkBox_fillup_topped:
			onCheckedTop (v);
			break;
		
		case R.id.textVehicleName:
			break;
		}
	}

	private void onCheckedTop(View v) {
		// TODO Auto-generated method stub
		CheckBox checkTopFillup = (CheckBox) v;
		
		notToppedUp = !notToppedUp;
		checkTopFillup.setChecked(notToppedUp);
	}
	
}
