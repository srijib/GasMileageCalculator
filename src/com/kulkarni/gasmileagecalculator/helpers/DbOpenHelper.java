/**
 * 
 */
package com.kulkarni.gasmileagecalculator.helpers;

import com.kulkarni.gasmileagecalculator.data.Fillup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Amey Kulkarni
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper {

	private static final String TAG       = DbOpenHelper.class.getSimpleName();
	public  static final String dbName    = "refueler.db";
	public  static final int    dbVersion = 1;
	
	public DbOpenHelper(Context context) {
		super(context, dbName, null, dbVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = Fillup.createTable;
		
		Log.d(TAG + "::onCreate", "sql: " + sql);
		
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Fillup.TABLE);
		Log.d(TAG + "::onUpgrade", "dropped table " + Fillup.TABLE);
		this.onCreate(db);
	}
	
}
