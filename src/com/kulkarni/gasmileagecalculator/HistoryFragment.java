/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupData;

import android.app.Activity;
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

	Activity activity;
	RefuelerApplication app;
	FillupData fd;
	
	ListView history;
	//HistoryAdapter adapter;
	HistoryCursorAdapter adapter;
	
	/**
	 * 
	 */
	public HistoryFragment() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container, false);
		
		activity = getActivity();
		app = (RefuelerApplication) activity.getApplication();
		fd  = app.getFillups();
		
		history = (ListView) rootView.findViewById(R.id.history_list);
		
		adapter = new HistoryCursorAdapter(activity, fd.getFillups(), 0);
		history.setAdapter(adapter);
		
		return rootView;
	}
}
