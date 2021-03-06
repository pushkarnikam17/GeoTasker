package com.fjaviermo.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProfilesSQLiteHelper {
 
	  public static final String TABLE_NAME = "profiles";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_ACTIVE = "active";

	  // Database creation sql statement
	  private static final String CREATE_TABLE_PROFILES = "create table "
	      + TABLE_NAME + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_NAME
	      + " text not null, " + COLUMN_ACTIVE
	      + " integer not null);";


	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(CREATE_TABLE_PROFILES);
	  }

	  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(ProfilesSQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);
	  }
}
