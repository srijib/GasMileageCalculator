/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

	private Activity activity;
	private RefuelerApplication app;
	
	ListView history;
	//HistoryAdapter adapter;
	HistoryCursorAdapter adapter;
	
	/**
	 * 
	 */
	public HistoryFragment() {
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container, false);
		
		activity = getActivity();
		app = (RefuelerApplication) activity.getApplication();
		
		history = (ListView) rootView.findViewById(R.id.history_list);
		
		Cursor c = app.getFillups().getFillups();
		adapter = new HistoryCursorAdapter(activity, app, c);
		history.setAdapter(adapter);

		return rootView;
	}
}
