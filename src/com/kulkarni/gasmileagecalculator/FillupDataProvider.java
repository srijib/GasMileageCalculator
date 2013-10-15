package com.kulkarni.gasmileagecalculator;

import com.kulkarni.gasmileagecalculator.data.Fillup;
import com.kulkarni.gasmileagecalculator.helpers.DbOpenHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FillupDataProvider extends ContentProvider {
	public static final String TAG = FillupDataProvider.class.getSimpleName();
	
	public static final String AUTHORITY = "content://com.kulkarni.gasmileagecalculator.fillupdataprovider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	
	Context context;
	DbOpenHelper dbHelper;
	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		context = getContext();
		dbHelper = new DbOpenHelper(context);
		
		return true;
	}

	@Override
	public String getType(Uri uri) {
		if (uri.getLastPathSegment() == null) {
			return "vnd.android.cursor.dir/vnd.com.kulkarni.gasmileagecalculator.fillupdataprovider.tblFillup";
		}
		else {
			return "vnd.android.cursor.item/vnd.com.kulkarni.gasmileagecalculator.fillupdataprovider.tblFillup";
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = dbHelper.getWritableDatabase();
		
		long id = db.insertWithOnConflict(Fillup.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		
		if (id != -1)
			return Uri.withAppendedPath(uri, Long.toString(id));
		else
			return uri;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(Fillup.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}


}
