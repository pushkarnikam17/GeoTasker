package com.fjaviermo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "geotasker.db";
	private static final int DATABASE_VERSION = 1;
		
	public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		if (!database.isReadOnly()) {
			// Enable foreign key constraints
			database.execSQL("PRAGMA foreign_keys = ON;");
			Log.i("TAG", "FOREIGN KEY constraint enabled!");
		}      

		ProfilesSQLiteHelper.onCreate(database);
		LocationSQLiteHelper.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		ProfilesSQLiteHelper.onUpgrade(database, oldVersion, newVersion);
		LocationSQLiteHelper.onUpgrade(database, oldVersion, newVersion);
	}			
}
