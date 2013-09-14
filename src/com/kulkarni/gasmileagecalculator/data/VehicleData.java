package com.kulkarni.gasmileagecalculator.data;

import android.content.Context;
import android.provider.BaseColumns;

public class VehicleData {

	private static final String TAG = VehicleData.class.getSimpleName();
	
	private Context context;
	
	public VehicleData(Context context) {
		this.context = context;
	}
	
	public class Vehicle {
		
		private final String TAG = Vehicle.class.getSimpleName();
		
		// Members
		private int    vehicle_id;
		private int    year;
		private String make;
		private String model;
		private String nickname;
		
		
		// Table
		public static final String TABLE           = "tblVehicle";
		public static final String C_ID            = BaseColumns._ID;
		public static final String C_LAST_MODIFIED = "lastModified";
		public static final String C_YEAR          = "year";
		public static final String C_MAKE          = "make";
		public static final String C_MODEL         = "model";
		public static final String C_NICKNAME      = "nickname";
		
		public static final String createTable =
				"CREATE TABLE " + TABLE + " (" +
				C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
				C_LAST_MODIFIED + " INTEGER NOT NULL, " +
				C_YEAR + " INTEGER, " +
				C_MAKE + " TEXT NOT NULL, " +
				C_MODEL + " TEXT NOT NULL, " +
				C_NICKNAME + " TEXT" +
				")";
		
		
		public int getVehicle_id() {
			return vehicle_id;
		}
		
		public int getYear() {
			return year;
		}
		
		public String getMake() {
			return make;
		}
		
		public String getModel() {
			return model;
		}
		
		public String getNickname() {
			return nickname;
		}
	}
}
