package com.fjaviermo.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocationSQLiteHelper {

	  public static final String TABLE_NAME = "locations";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_ID_PROFILE = "id_profile";
	  public static final String COLUMN_LATITUDE = "latitude";
	  public static final String COLUMN_LONGITUDE = "longitude";
	  public static final String COLUMN_TYPE = "type";

	  // Database creation sql statement
	  private static final String CREATE_TABLE_LOCATIONS = "create table "
	      + TABLE_NAME + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_ID_PROFILE
	      + " integer references " + ProfilesSQLiteHelper.TABLE_NAME + "(" 
	      + ProfilesSQLiteHelper.COLUMN_ID  + "),"  + COLUMN_LATITUDE
	      + " real not null, " + COLUMN_LONGITUDE
	      + " real not null, " + COLUMN_TYPE
	      + " integer not null);";


	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(CREATE_TABLE_LOCATIONS);
	  }

	  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(LocationSQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);
	  }
}
