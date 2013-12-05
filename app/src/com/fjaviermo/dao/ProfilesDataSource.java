package com.fjaviermo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fjaviermo.database.DatabaseHelper;
import com.fjaviermo.database.ProfilesSQLiteHelper;
import com.fjaviermo.model.Profile;

public class ProfilesDataSource {

	// Database fields
	private SQLiteDatabase mDatabase;
	private DatabaseHelper mDbHelper;
	private String[] mAllColumns = { 
			ProfilesSQLiteHelper.COLUMN_ID,
			ProfilesSQLiteHelper.COLUMN_NAME,
			ProfilesSQLiteHelper.COLUMN_ACTIVE
	};

	public ProfilesDataSource(Context context) {
		mDbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Profile createProfile(String name, boolean active) {
		ContentValues values = new ContentValues();
		values.put(ProfilesSQLiteHelper.COLUMN_NAME, name);
		values.put(ProfilesSQLiteHelper.COLUMN_ACTIVE, active ? 1 : 0);
		long insertId = mDatabase.insert(ProfilesSQLiteHelper.TABLE_NAME, null,
				values);
		Cursor cursor = mDatabase.query(ProfilesSQLiteHelper.TABLE_NAME,
				mAllColumns, ProfilesSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Profile newProfile = cursorToProfile(cursor);
		cursor.close();
		
		return newProfile;
	}

	public void deleteProfile(Profile profile) {
		long id = profile.getId();
		System.out.println("Profile deleted with id: " + id);
		mDatabase.delete(ProfilesSQLiteHelper.TABLE_NAME, 
				        ProfilesSQLiteHelper.COLUMN_ID
				        + " = " + id, null);
	}

	public void updateProfile(Profile profile) {
		ContentValues values = new ContentValues();
		values.put(ProfilesSQLiteHelper.COLUMN_NAME, profile.getName());
		values.put(ProfilesSQLiteHelper.COLUMN_ACTIVE, profile.isActive() ? 1 : 0);

		long id = profile.getId();
		System.out.println("Profile update with id: " + id);

		mDatabase.update(ProfilesSQLiteHelper.TABLE_NAME, values, 
				ProfilesSQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public Profile getProfile(long id) {
		Cursor cursor = mDatabase.query(ProfilesSQLiteHelper.TABLE_NAME,
				mAllColumns, ProfilesSQLiteHelper.COLUMN_ID + "=" + id,
				null, null, null, null, null);
		cursor.moveToFirst();
		
		Profile profile = cursorToProfile(cursor);
		// make sure to close the cursor
		cursor.close();
		
		return profile;
	}
	
	public List<Profile> getAllProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		Cursor cursor = mDatabase.query(ProfilesSQLiteHelper.TABLE_NAME,
				mAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Profile profile = cursorToProfile(cursor);
			profiles.add(profile);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		
		return profiles;
	}

	private Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();		
		profile.setId(cursor.getLong(cursor.getColumnIndex(ProfilesSQLiteHelper.COLUMN_ID)));
		profile.setName(cursor.getString(cursor.getColumnIndex(ProfilesSQLiteHelper.COLUMN_NAME)));
		profile.setActive(cursor.getInt(cursor.getColumnIndex(ProfilesSQLiteHelper.COLUMN_ACTIVE)) == 1);
		
		return profile;
	}
}
