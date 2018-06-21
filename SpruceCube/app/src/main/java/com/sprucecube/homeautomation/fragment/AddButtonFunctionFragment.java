package com.sprucecube.homeautomation.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sprucecube.homeautomation.DevicesActivity;
import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.Params;

import static android.content.Context.MODE_PRIVATE;


public class AddButtonFunctionFragment extends Fragment {

    private static final String TAG = "AddButtonFunctionFrag";

    private static final String IDEN_ID = "iden_id";
    String identification_id;

    public AddButtonFunctionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddButtonFunctionFragment newInstance(String identification_id) {
        AddButtonFunctionFragment fragment = new AddButtonFunctionFragment();
        Bundle args = new Bundle();
        args.putString(IDEN_ID, identification_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            identification_id = getArguments().getString(IDEN_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_button_function, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "OnActivityCreated");

        final Activity activity = getActivity();

        //TODO, Get Button here
        Button saveButton  = activity.findViewById(R.id.button_save_button_function);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClick(view, activity);
            }
        });
    }

    //TODO, Clean this function
    void saveClick(View view, Activity activity)
    {
        //We add shit here
        EditText urlText = activity.findViewById(R.id.edittext_get_button_url);
        String url = urlText.getText().toString();
        Spinner spinner = activity.findViewById(R.id.spinner_choose_button_function);
        String data = spinner.getSelectedItem().toString();

        //NOTE, We HAVE TO POPBACK STACK BEFORE Shared Preferences
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

        DevicesActivity.nav_id = String.valueOf(identification_id.split(":")[0]);
        Log.d(TAG, DevicesActivity.nav_id +" :updated");

        //DONE, Save to shared preferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Params.PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(identification_id, url+":"+data);
        editor.apply(); //we want it to block the thread (use apply if you want it async)

        //TEST
        Log.d(TAG, identification_id);
        Log.d(TAG, url+":"+data);
    }
}
