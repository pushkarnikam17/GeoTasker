package com.fjaviermo.geotasker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.fjaviermo.geotasker.ProfileListFragment.OnSelectedProfileChangeListener;

public class MainActivity extends Activity 
implements OnSelectedProfileChangeListener {

	public static final String PROFILE_LIST = "profileList";
	public static final String PROFILE_DESC = "profileDescription";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the profile description fragment
		FragmentManager fm = getFragmentManager();

		// Begin transaction
		FragmentTransaction ft = fm.beginTransaction();

		// Create the Fragment and add
		ProfileListFragment listFragment = new ProfileListFragment();
		ft.add(R.id.layoutRoot, listFragment, PROFILE_LIST);

		// Commit the changes
		ft.commit();
	}

	@Override
	public void onSelectedProfileChanged(long profileIndex) {
		ProfileFragment profileDescFragment;
		FragmentManager fm = getFragmentManager();

		// Handle dynamic switch to description fragment
		FragmentTransaction ft = fm.beginTransaction();

		// Create the fragment and attach profile id
		profileDescFragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putLong(ProfileFragment.PROFILE_INDEX, profileIndex);
		profileDescFragment.setArguments(args);

		// Replace the profile list with the description
		ft.replace(R.id.layoutRoot, profileDescFragment, PROFILE_DESC);
		ft.addToBackStack(null);
		ft.setCustomAnimations(
				android.R.animator.fade_in, android.R.animator.fade_out);
		ft.commit();
	}
}
