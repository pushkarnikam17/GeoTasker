package com.fjaviermo.model;

public class Profile {
	
	private long mId;
	private String mName;
	private boolean mActive;
	
	public long getId() {
		return mId;
	}
	
	public void setId(long id) {
		this.mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public boolean isActive() {
		return mActive;
	}
	
	public void setActive(boolean active) {
		mActive = active;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return mName;
	}
}
