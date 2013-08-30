/**
 * 
 */
package com.kulkarni.gasmileagecalculator.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Amey Kulkarni
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper {

	private static final String TAG       = DbOpenHelper.class.getSimpleName();
	private static final String dbName    = "refueler.db";
	private static final int    dbVersion = 1;
	
	public DbOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		createTables();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	private void createTables() {
		// TODO Auto-generated method stub
		
	}
}
