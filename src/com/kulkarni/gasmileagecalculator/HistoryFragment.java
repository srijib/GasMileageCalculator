/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.FillupVector;

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

	ListView history;
	HistoryAdapter adapter;
	
	/**
	 * 
	 */
	public HistoryFragment() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container, false);
		
		history = (ListView) rootView.findViewById(R.id.history_list);
		adapter = new HistoryAdapter(getActivity(), new FillupVector());
		history.setAdapter(adapter);
		
		return rootView;
	}
}
