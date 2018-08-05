package com.sprucecube.homeautomation.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sprucecube.homeautomation.DimmerDialogActivity;
import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.AsyncHTTP;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import java.util.Arrays;

/**
 * Cleaned: 25.06.2018
 */
public class GenericFillButtonFragment extends Fragment {

    private static final String TAG = "GenericFillButtonFrag";

    String nav_id;
    String room_name;

    public GenericFillButtonFragment() {
        // Required empty public constructor
    }

    public static GenericFillButtonFragment newInstance(String nav_id, String roomName) {
        GenericFillButtonFragment fragment = new GenericFillButtonFragment();
        Bundle args = new Bundle();
        args.putString(Params.NAVIGATION_ID, nav_id);
        args.putString(Params.ROOM_TITLE, roomName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            nav_id = getArguments().getString(Params.NAVIGATION_ID);
            room_name = getArguments().getString(Params.ROOM_TITLE);
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
        TextView roomStatus = activity.findViewById(R.id.content_status);
        roomStatus.setText(room_name);

        buttonActions(nav_id, activity, sharedPreferences);
    }

    void buttonActions(final String nav_id, Activity activity, final SharedPreferences sharedPreferences)
    {
        for(int i=1;i<=9;i++)
        {
            //final int button_id = getResources().getIdentifier("button"+i, "id", activity.getPackageName());
            int button_id = HelperMethods.getButtonId((AppCompatActivity) activity, i);
            if(button_id == 0)
            {
                Log.wtf(TAG, "Button ID is 0");
            }

            Button genericButton = activity.findViewById(button_id);

            boolean updated = HelperMethods.setButtonArtifacts(nav_id, button_id, sharedPreferences, genericButton);
            Log.d(TAG, String.valueOf(updated));

            //Set GenericButton Long Click
            setLongClickButtonAction(genericButton, button_id);

            //Set GenericButton Click
            setClickButtonAction(genericButton, sharedPreferences);
        }
    }


    void setLongClickButtonAction(Button genericButton, final int button_id)
    {
        genericButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                //DONE, Attach your action here
                onLongUserButtonClick(button_id);
                return true;
            }
        });
    }

    /*
    Used to modify the contents of a button
     */
    void onLongUserButtonClick(int button_id)
    {
        AddButtonFunctionFragment addButtonFunctionFragment = AddButtonFunctionFragment.newInstance(nav_id+":"+button_id);
        //HelperMethods.startFragmentMethod((AppCompatActivity) getActivity(), addButtonFunctionFragment, true);
        HelperMethods.startFragmentMethod((AppCompatActivity) getActivity(), addButtonFunctionFragment, Params.FAV_STACK);
    }


    void setClickButtonAction(Button genericButton, final SharedPreferences sharedPreferences)
    {
        genericButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserButtonClick(view, sharedPreferences);
            }
        });
    }

    /*
    Check if the data is present inside the file, if not return null
     */
    void onUserButtonClick(View view, SharedPreferences preferences)
    {
        Log.d(TAG, "onUserButtonClick");

        int id = view.getId();
        Log.d(TAG, "ButtonId: "+id);

        //String name = getResources().getResourceEntryName(id);
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

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Params.SETTINGS, Context.MODE_PRIVATE);
            String ip_address = sharedPreferences.getString(String.valueOf(R.id.settings_host_url), "");
            
            if(ip_address.equals(""))
            {
                Toast.makeText(getActivity(), "Fill Settings screen", Toast.LENGTH_SHORT).show();
                return ;
            }

            //DONE, Different functionality for SWITCH and DIMMER HERE
            String[] splitData = data.split(":");
            Log.d(TAG, Arrays.toString(splitData));

            if(splitData[1].equals(Params.CONTROL_ACTION))
            {
                //DONE, Start DimmerDialogActivity here
                Log.d(TAG, Params.CONTROL_ACTION);

                Intent dimmerIntent = new Intent(getActivity(), DimmerDialogActivity.class);
                dimmerIntent.putExtra(Params.PIN_NUM, splitData[0]);
                startActivity(dimmerIntent);
            }
            else if(splitData[1].equals(Params.SWITCH_ACTION))
            {
                //DONE, Do switch stuff here
                Log.d(TAG, Params.SWITCH_ACTION);

                String url = "http://"+ip_address+"/"+splitData[0];
                Log.d(TAG, url);

                //This is 1 METHOD
                //PostAsync postAsync = new PostAsync(getActivity());
                //postAsync.execute(url);

                AsyncHTTP asyncHTTP = new AsyncHTTP(AsyncHTTP.POST_METHOD, new AsyncHTTP.CallbackReceived() {
                    @Override
                    public void onResponseReceived(String result)
                    {
                        if(result == null)
                        {
                            Toast.makeText(getActivity(), "Request: Timeout", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                asyncHTTP.execute(url);
            }
        }
    }
}
