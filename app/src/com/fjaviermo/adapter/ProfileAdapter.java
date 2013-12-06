package com.fjaviermo.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.fjaviermo.geotasker.R;
import com.fjaviermo.model.Profile;

public class ProfileAdapter extends ArrayAdapter<Profile> {

	private final Activity context;
	private List<Profile> profiles;
	private ChangeSwitchCallback mDeleteItemCallback;

	static class ViewHolder {
		public TextView profileName;
		public Switch activate;
	}

	public ProfileAdapter(Activity context, List<Profile> names, ChangeSwitchCallback callback) {
		super(context, R.layout.profile_row, names);
		this.context = context;
		this.profiles = names;
		this.mDeleteItemCallback = callback;		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.profile_row, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.profileName = (TextView) rowView.findViewById(R.id.lblName);
			
			viewHolder.activate = (Switch) rowView.findViewById(R.id.activate);
			viewHolder.activate.setOnCheckedChangeListener(new OnCheckedChangeListener() 
			{               
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
				{       
					mDeleteItemCallback.changeState(position);
				}
			});
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Profile profile = profiles.get(position);

		holder.profileName.setText(profile.getName());
		holder.activate.setActivated(profile.isActive());

		return rowView;
	}
	
	public interface ChangeSwitchCallback {
		public void changeState(int position);
	}
} 
