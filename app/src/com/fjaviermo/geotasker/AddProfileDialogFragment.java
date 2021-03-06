package com.fjaviermo.geotasker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProfileDialogFragment extends DialogFragment {

	OnAddedProfileListener mListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View view = inflater.inflate(R.layout.profile_name, null);

		final EditText profilePossibleName = (EditText) view.findViewById(R.id.profilePossibleName);

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setTitle(R.string.title_dialog_profile)
		.setView(view)
		// Add action buttons
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				
				// Access the Activity and cast to the interface
				FragmentManager fm = getActivity().getFragmentManager();
				
				mListener = (OnAddedProfileListener)fm.findFragmentByTag(MainActivity.PROFILE_LIST);
			    
				// Notify the Activity of the selection
				mListener.onAddedProfile(profilePossibleName.getText().toString());
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				getDialog().cancel();
			}
		});      

		final AlertDialog dialog = builder.create();

    	dialog.setOnShowListener(new OnShowListener() {

    	    @Override
    	    public void onShow(DialogInterface dialog) {
    	        if(profilePossibleName.getText().length() == 0)
    	        ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    	    }
    	});
    	
    	profilePossibleName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            	final Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            	
                if(profilePossibleName.getText().length() == 0) {
                    okButton.setEnabled(false);
                    
                } else {
                    okButton.setEnabled(true);
                }
            }
        });
		
		return dialog;
	}
	
	public interface OnAddedProfileListener {
		void onAddedProfile(String name);
	}

}
