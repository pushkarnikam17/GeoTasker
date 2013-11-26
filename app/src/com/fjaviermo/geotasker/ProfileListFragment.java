package com.fjaviermo.geotasker;

import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fjaviermo.dao.ProfilesDataSource;
import com.fjaviermo.model.Profile;

public class ProfileListFragment extends ListFragment{

	private ProfilesDataSource profileDAO;
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

	    profileDAO = new ProfilesDataSource(getActivity().getBaseContext());
	    profileDAO.open();
	    List<Profile> profiles = profileDAO.getAllProfiles();
	    
	    ArrayAdapter<Profile> profilesAdapter;
	    profilesAdapter = new ArrayAdapter<Profile>(getActivity(),
	        android.R.layout.simple_list_item_1, profiles);

	    setListAdapter(profilesAdapter);
	    profileDAO.close();
	  }

	  @Override
	  public void onListItemClick(ListView l, View v,
	                              int position, long id) {
		  
	    Profile profile = (Profile) getListAdapter().getItem(position);
	    
	    // Access the Activity and cast to the interface
	    OnSelectedProfileChangeListener listener =
	        (OnSelectedProfileChangeListener)
	            getActivity();

	    // Notify the Activity of the selection
	    listener.onSelectedProfileChanged(profile.getId());
	  }
	  
	  @Override
	  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		   inflater.inflate(R.menu.menu_profilelist, menu);
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	     // handle item selection
	     switch (item.getItemId()) {
	        case R.id.menu_new:
	        	ProfileFragment profileDescFragment;
	    		FragmentManager fm = getFragmentManager();

	    		// Handle dynamic switch to description fragment
	    		FragmentTransaction ft = fm.beginTransaction();

	    		// Create the fragment and attach book index
	    		profileDescFragment = new ProfileFragment();

	    		// Replace the profile list with the description
	    		ft.replace(R.id.layoutRoot, profileDescFragment, "profileDescription");
	    		ft.addToBackStack(null);
	    		ft.setCustomAnimations(
	    				android.R.animator.fade_in, android.R.animator.fade_out);
	    		ft.commit();
	        	return true;
	        default:
	           return super.onOptionsItemSelected(item);
	     }
	  }
}
