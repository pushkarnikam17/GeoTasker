package com.fjaviermo.model;

import android.util.SparseArray;


public class Location {

	public enum Type { ENTER(1), LEAVE(2), ENTER_LEAVE(3);
	
	private int value;
	private static final  SparseArray<Type> typesByValue = new SparseArray<Type>();

	static {
		for (Type type : Type.values()) {
			typesByValue.put(type.value, type);
		}
	}

	private Type(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Type forValue(int value) {
		return typesByValue.get(value);
	}
	}
	
	private long id;
	private long idProfile;
	private long latitude;
	private long longitude;
	private Type type;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getIdProfile() {
		return idProfile;
	}
	
	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return latitude + " , " + longitude;
	}
}