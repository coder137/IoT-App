package com.sprucecube.homeautomation.fragment;


import android.app.Activity;
import android.content.Context;
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

import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.Params;


public class GenericFillButtonFragment extends Fragment {

    private static final String TAG = "GenericFillButtonFrag";

    String nav_id;

    public GenericFillButtonFragment() {
        // Required empty public constructor
    }

    public static GenericFillButtonFragment newInstance(String nav_id) {
        GenericFillButtonFragment fragment = new GenericFillButtonFragment();
        Bundle args = new Bundle();
        args.putString(Params.NAVIGATION_ID, nav_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            nav_id = getArguments().getString(Params.NAVIGATION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_devices, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "Activity Created");
        //Attach the data to all the buttons here
        final Activity activity = getActivity();

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Params.PREFS, Context.MODE_PRIVATE);

        //Attach Button Actions for all 9 buttons
        buttonActions(nav_id, activity, sharedPreferences);
    }

    void buttonActions(final String nav_id, Activity activity, final SharedPreferences sharedPreferences)
    {
        for(int i=1;i<=9;i++)
        {
            final int button_id = getResources().getIdentifier("button"+i, "id", activity.getPackageName());

            if(button_id == 0)
            {
                Log.wtf(TAG, "Button ID is 0");
            }
            else
            {
                Log.d(TAG, "Button ID: "+button_id);
            }

            //get the current nav_id
            String data = nav_id+":"+button_id;
            Log.d(TAG, data+":"+i);

            Button genericButton = activity.findViewById(button_id);

            //Set GenericButton Long Click
            genericButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    //DONE, Attach your action here
                    onLongUserButtonClick(button_id);
                    return true;
                }
            });

            //Set GenericButton Click
            genericButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserButtonClick(view, sharedPreferences);
                }
            });


            //TODO, Get data from the file and set the data here
            String buttonData = sharedPreferences.getString(data,null);
            if(buttonData != null)
            {
                genericButton.setText(buttonData.replace(":", "\n"));
                Log.d(TAG, "Attached generic Button on: " +i);
            }

        }
        //EOFunction
    }


    /*
    Used to modify the contents of a button
     */
    void onLongUserButtonClick(int button_id)
    {
//        Intent intent = new Intent(getActivity(), AddButtonFunctionActivity.class);
//        Bundle args = new Bundle();
//        args.putString(Params.IDENTIFICATION_ID, nav_id+":"+button_id);
//        intent.putExtra(Params.INTENT_BUNDLE, args);
//        startActivity(intent);

        //TODO, Add a fragment here, AddButtonFunctionFragment
        AddButtonFunctionFragment addButtonFunctionFragment = AddButtonFunctionFragment.newInstance(nav_id+":"+button_id);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_view, addButtonFunctionFragment).addToBackStack(Params.BACK_STACK).commit();

    }

    /*
    Check if the data is present inside the file, if not return null
     */
    //TODO, Write this function
    void onUserButtonClick(View view, SharedPreferences preferences)
    {
        Log.d(TAG, "onUserButtonClick");

        int id = view.getId();
        Log.d(TAG, "ButtonId: "+id);
        String name = getResources().getResourceEntryName(id);

        //SharedPreferences preferences = getSharedPreferences(Params.PREFS, MODE_PRIVATE);

        Log.d(TAG, nav_id+":"+id);
        String data = preferences.getString(nav_id+":"+id, null);
        if(data == null)
        {
           onLongUserButtonClick(id);
        }
        else
        {
            Log.d(TAG, "We got the data==> " +data);
            //TODO, Attach the HTTP Action to this, [IMPORTANT]
        }
    }
}
