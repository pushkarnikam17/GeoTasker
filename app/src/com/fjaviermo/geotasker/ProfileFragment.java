package com.fjaviermo.geotasker;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fjaviermo.dao.ProfilesDataSource;
import com.fjaviermo.model.Profile;

public class ProfileFragment extends Fragment{

	// Profile id argument name
	public static final String PROFILE_INDEX = "profile index";
	// Profile id default value
	private static final long PROFILE_INDEX_NOT_SET = -1;

	private EditText mProfileNameEditText;
	private ProfilesDataSource mProfileDAO;
	private Profile mProfile;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		ActionBar actionBar = getActivity().getActionBar(); 
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
				
		View viewHierarchy = inflater.inflate(R.layout.fragment_profile, container, false);

		// Get reference to profile description edit text
		mProfileNameEditText = (EditText)
				viewHierarchy.findViewById(R.id.profileName);

		// Retrieve the profile id if attached
		Bundle args = getArguments();
		long bookIndex = args != null ? args.getLong(PROFILE_INDEX, PROFILE_INDEX_NOT_SET) 
				: PROFILE_INDEX_NOT_SET;

		// If we find the profile id, use it
		mProfileDAO = new ProfilesDataSource(getActivity().getBaseContext());
		mProfileDAO.open();
		mProfile = mProfileDAO.getProfile(bookIndex);
		//profileDAO.close();
		setProfile(mProfile.getName());
		updateActionBarTile(mProfile.getName());
		
		return viewHierarchy;
	}

	public void setProfile(String profileName) {
		
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
		case R.id.action_delete:
			mProfileDAO.deleteProfile(mProfile);
			
		    getActivity().getFragmentManager().popBackStack();
			return true;

		case R.id.action_accept:
			mProfile.setName(mProfileNameEditText.getText().toString());
			mProfileDAO.updateProfile(mProfile);
					
		    Context context = getActivity().getApplicationContext();
			CharSequence text = R.string.update_profile_toast_1 + mProfile.getName() 
					+ R.string.update_profile_toast_2;
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();			
		
		    getActivity().getFragmentManager().popBackStack();

			return true;

		case android.R.id.home:
			ProfileListFragment profileListFragment;
			FragmentManager fm = getFragmentManager();

			// Handle dynamic switch to description fragment
			FragmentTransaction ft = fm.beginTransaction();

			// Create the fragment and attach book index
			profileListFragment = new ProfileListFragment();

			// Replace the profile list with the description
			ft.replace(R.id.layoutRoot, profileListFragment, MainActivity.PROFILE_LIST);
			ft.setCustomAnimations(
					android.R.animator.fade_in, android.R.animator.fade_out);
			ft.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void updateActionBarTile(String title) {
		getActivity().getActionBar().setTitle(title);
	}
}
