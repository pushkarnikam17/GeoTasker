package com.fjaviermo.model;

import android.util.SparseArray;


public class Location {

	public enum Type { ENTER(1), LEAVE(2), ENTER_LEAVE(3);
	
	private int mValue;
	private static final  SparseArray<Type> mTypesByValue = new SparseArray<Type>();

	static {
		for (Type type : Type.values()) {
			mTypesByValue.put(type.mValue, type);
		}
	}

	private Type(int value) {
		this.mValue = value;
	}

	public int getValue() {
		return mValue;
	}

	public static Type forValue(int value) {
		return mTypesByValue.get(value);
	}
	}
	
	private long mId;
	private long mIdProfile;
	private long mLatitude;
	private long mLongitude;
	private Type mType;
	
	
	public long getId() {
		return mId;
	}
	
	public void setId(long id) {
		mId = id;
	}
	
	public long getIdProfile() {
		return mIdProfile;
	}
	
	public void setIdProfile(long idProfile) {
		mIdProfile = idProfile;
	}

	public long getLatitude() {
		return mLatitude;
	}

	public void setLatitude(long latitude) {
		mLatitude = latitude;
	}

	public long getLongitude() {
		return mLongitude;
	}

	public void setLongitude(long longitude) {
		mLongitude = longitude;
	}

	public Type getType() {
		return mType;
	}

	public void setType(Type type) {
		mType = type;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return mLatitude + " , " + mLongitude;
	}
}
