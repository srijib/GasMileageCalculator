/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupData;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author Amey Kulkarni
 * 
 */
public class HistoryFragment extends Fragment {

	private static final String TAG = HistoryFragment.class.getSimpleName();

	Activity activity;
	RefuelerApplication app;
	FillupData fd;

	ListView history;
	// HistoryAdapter adapter;
	HistoryCursorAdapter adapter;

	/**
	 * 
	 */
	public HistoryFragment() {
		activity = getActivity();
		app = (RefuelerApplication) activity.getApplication();
		fd = app.getFillups();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container,
				false);
		
		try {
			history = (ListView) rootView.findViewById(R.id.history_list);

			adapter = new HistoryCursorAdapter(activity, app, fd.getFillups());
			history.setAdapter(adapter);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return rootView;
	}
}
