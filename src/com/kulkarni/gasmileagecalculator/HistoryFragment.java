/**
 * 
 */
package com.kulkarni.gasmileagecalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Amey Kulkarni
 *
 */
public class HistoryFragment extends Fragment {

	/**
	 * 
	 */
	public HistoryFragment() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container, false);
		return rootView;
	}
}