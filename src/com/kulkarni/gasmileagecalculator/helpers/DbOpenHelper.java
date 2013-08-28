/**
 * 
 */
package com.kulkarni.gasmileagecalculator.helpers;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Amey Kulkarni
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper {

	private static final String TAG       = DbOpenHelper.class.getSimpleName();
	private static final String dbName    = "refueler.db";
	private static final int    dbVersion = 1;
}
