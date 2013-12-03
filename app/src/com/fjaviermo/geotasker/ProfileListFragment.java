package com.fjaviermo.geotasker;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fjaviermo.dao.ProfilesDataSource;
import com.fjaviermo.geotasker.AddProfileDialogFragment.OnAddedProfileListener;
import com.fjaviermo.model.Profile;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

public class ProfileListFragment extends ListFragment 
implements DeleteItemCallback, OnAddedProfileListener {

	private ProfilesDataSource mProfileDAO;

	OnSelectedProfileChangeListener mListener;
	private ArrayAdapter<Profile> mProfilesAdapter;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setHomeButtonEnabled(false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

		mProfileDAO = new ProfilesDataSource(getActivity().getBaseContext());
		mProfileDAO.open();
		
		List<Profile> profiles = mProfileDAO.getAllProfiles();

		mProfilesAdapter = new ArrayAdapter<Profile>(getActivity(),
				android.R.layout.simple_list_item_1, profiles);

		setListAdapter(mProfilesAdapter);

		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mProfilesAdapter, 
				R.layout.undo_row, R.id.undo_row_undobutton);
		adapter.setAbsListView(getListView());
		getListView().setAdapter(adapter);
		adapter.setDeleteItemCallback(this);
	}

	@Override
	public void onListItemClick(ListView l, View v,
			int position, long id) {

		Profile profile = (Profile) getListAdapter().getItem(position);

		// Access the Activity and cast to the interface
		mListener = (OnSelectedProfileChangeListener)getActivity();

		// Notify the Activity of the selection
		mListener.onSelectedProfileChanged(profile.getId());
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
			AddProfileDialogFragment theDialog = new AddProfileDialogFragment();
			theDialog.show(getFragmentManager(), null);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public interface OnSelectedProfileChangeListener {
		void onSelectedProfileChanged(long profileIndex);
	}

	@Override
	public void deleteItem(int position) {
		Profile profile = mProfilesAdapter.getItem(position);

		mProfileDAO.deleteProfile(profile);

		mProfilesAdapter.remove(profile);
		mProfilesAdapter.notifyDataSetChanged();
	}

	@Override
	public void onAddedProfile(String name) {
		Profile profile = mProfileDAO.createProfile(name, true);

		mProfilesAdapter.add(profile);
		mProfilesAdapter.notifyDataSetChanged();
	}
}
