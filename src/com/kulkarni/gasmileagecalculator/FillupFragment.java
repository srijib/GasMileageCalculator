/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Amey Kulkarni
 *
 */
public class FillupFragment extends Fragment
{
	
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		
		public Dialog onCreateDialog (Bundle savedInstanceState) {
			Long curDate = savedInstanceState.getLong("setDate");
			
			final Calendar c = Calendar.getInstance();
			c.setTimeInMillis(curDate);
			int year  = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day   = c.get(Calendar.DAY_OF_MONTH);
			
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
	}

	public Date mDate;

	/**
	 * 
	 */
	public FillupFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fillup_fragment, container, false);
		
		mDate = new Date ();
		
		TextView dateTextView = (TextView) rootView.findViewById(R.id.textView_fillup_date);
		dateTextView.setText(getDateString ());
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
	
}
