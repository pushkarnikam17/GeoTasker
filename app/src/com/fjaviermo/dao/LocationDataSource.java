package com.fjaviermo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fjaviermo.database.DatabaseHelper;
import com.fjaviermo.database.LocationSQLiteHelper;
import com.fjaviermo.model.Location;
import com.fjaviermo.model.Location.Type;

public class LocationDataSource {
	// Database fields
	private SQLiteDatabase mDatabase;
	private DatabaseHelper mDbHelper;
	private String[] mAllColumns = { 
			LocationSQLiteHelper.COLUMN_ID,
			LocationSQLiteHelper.COLUMN_ID_PROFILE,
			LocationSQLiteHelper.COLUMN_LATITUDE,
			LocationSQLiteHelper.COLUMN_LONGITUDE,
			LocationSQLiteHelper.COLUMN_TYPE
	};

	public LocationDataSource(Context context) {
		mDbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Location createLocation(long id_profile, long latitude, long longitude, Location.Type type) {
		ContentValues values = new ContentValues();
		values.put(LocationSQLiteHelper.COLUMN_ID_PROFILE, id_profile);
		values.put(LocationSQLiteHelper.COLUMN_LATITUDE, latitude);
		values.put(LocationSQLiteHelper.COLUMN_LONGITUDE, longitude);
		values.put(LocationSQLiteHelper.COLUMN_TYPE, type.getValue());

		long insertId = mDatabase.insert(LocationSQLiteHelper.TABLE_NAME, null,
				values);
		Cursor cursor = mDatabase.query(LocationSQLiteHelper.TABLE_NAME,
				mAllColumns, LocationSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Location newLocation = cursorToLocation(cursor);
		cursor.close();
		
		return newLocation;
	}

	public void deleteLocation(Location location) {
		long id = location.getId();
		System.out.println("Location deleted with id: " + id);
		mDatabase.delete(LocationSQLiteHelper.TABLE_NAME, 
				LocationSQLiteHelper.COLUMN_ID
				        + " = " + id, null);
	}

	public List<Location> getAllLocations() {
		List<Location> locations = new ArrayList<Location>();
		Cursor cursor = mDatabase.query(LocationSQLiteHelper.TABLE_NAME,
				mAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Location location = cursorToLocation(cursor);
			locations.add(location);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		
		return locations;
	}

	private Location cursorToLocation(Cursor cursor) {
		Location location = new Location();		
		location.setId(cursor.getLong(cursor.getColumnIndex(LocationSQLiteHelper.COLUMN_ID)));
		location.setIdProfile(cursor.getLong(cursor.getColumnIndex(LocationSQLiteHelper.COLUMN_ID_PROFILE)));
		location.setLatitude(cursor.getLong(cursor.getColumnIndex(LocationSQLiteHelper.COLUMN_LATITUDE)));
		location.setLongitude(cursor.getLong(cursor.getColumnIndex(LocationSQLiteHelper.COLUMN_LONGITUDE)));
		location.setType(Type.forValue(cursor.getInt(cursor.getColumnIndex(LocationSQLiteHelper.COLUMN_TYPE))));
		
		return location;
	}
}
