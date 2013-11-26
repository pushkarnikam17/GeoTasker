package com.fjaviermo.geotasker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fjaviermo.dao.ProfilesDataSource;
import com.fjaviermo.model.Profile;

public class ProfileFragment extends Fragment{

	// Profile id argument name
	public static final String PROFILE_INDEX = "profile index";
	// Profile id default value
	private static final long PROFILE_INDEX_NOT_SET = -1;

	private EditText mProfileNameEditText;
	private ProfilesDataSource profileDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		View viewHierarchy = inflater.inflate(R.layout.fragment_profile, container, false);

		// Get reference to profile description edit text
		mProfileNameEditText = (EditText)
				viewHierarchy.findViewById(R.id.profileName);

		// Retrieve the profile id if attached
		Bundle args = getArguments();
		long bookIndex = args != null ? args.getLong(PROFILE_INDEX, PROFILE_INDEX_NOT_SET) : PROFILE_INDEX_NOT_SET;

		// If we find the profile id, use it
		if (bookIndex != PROFILE_INDEX_NOT_SET) {
			profileDAO = new ProfilesDataSource(getActivity().getBaseContext());
			profileDAO.open();
			Profile profile = profileDAO.getProfile(bookIndex);
			profileDAO.close();
			setProfile(profile.getName());
		}

		return viewHierarchy;
	}

	public void setProfile(String profileName) {
		
		// Display it
		mProfileNameEditText.setText(profileName);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_profiledesc, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.menu_new:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
