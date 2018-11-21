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

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sprucecube.homeautomation.DimmerDialogActivity;
import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import org.json.JSONException;
import org.json.JSONObject;

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

        String identification_id = nav_id+":"+id;

        Log.d(TAG, "IdentificationId: "+identification_id);
        String data = preferences.getString(identification_id, null);

        String deviceObjectData = preferences.getString(identification_id+":"+Params.DEVICE_OBJECT, null);
        final String devicePinData = preferences.getString(identification_id+":"+Params.DEVICE_PIN, null);
        String deviceTagData = preferences.getString(identification_id+":"+Params.TAG_NAME, null);

        Log.d(TAG, "deviceObjectData: "+deviceObjectData);
        Log.d(TAG, "devicePinData: "+devicePinData);
        Log.d(TAG, "deviceTagData: "+deviceTagData);

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

            // TODO, Initialize Parse server here
            String app_id = sharedPreferences.getString(String.valueOf(R.id.settings_app_id), "");
            Parse.initialize(new Parse.Configuration.Builder(getActivity())
                    .applicationId(app_id.trim())
                    .server("http://"+ip_address.trim()+":1337/parse/")
                    .build());

            //DONE, Different functionality for SWITCH and DIMMER HERE
            String[] splitData = data.split(":");
            Log.d(TAG, Arrays.toString(splitData));

            if(splitData[1].equals(Params.CONTROL_ACTION))
            {
                //DONE, Start DimmerDialogActivity here
                Log.d(TAG, Params.CONTROL_ACTION);

                Intent dimmerIntent = new Intent(getActivity(), DimmerDialogActivity.class);
//                dimmerIntent.putExtra(Params.PIN_NUM, splitData[0]);
                dimmerIntent.putExtra(Params.PIN_OBJECT_STRING, deviceObjectData);
                dimmerIntent.putExtra(Params.PIN_NUM, devicePinData);
                startActivity(dimmerIntent);
            }
            else if(splitData[1].equals(Params.SWITCH_ACTION))
            {
                //DONE, Do switch stuff here
                Log.d(TAG, Params.SWITCH_ACTION);

                // TODO, Send a PUT request to the Parse server here
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Devices");

                query.getInBackground(deviceObjectData, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null){
                            JSONObject jObj = object.getJSONObject(devicePinData);
                            try {
                                // TODO, Update JSON Object
                                Log.d(TAG, devicePinData+"::"+jObj.toString(2));

                                // Update the TYPE of the pin
                                String type = jObj.getString("type");
                                if (!type.equals(Params.SWITCH_ACTION.toLowerCase())){
                                    Log.d(TAG, "Updated " + devicePinData + " to type SWITCH");
                                    jObj.put("type", "switch");
                                    jObj.put("value", 0);
                                }

                                // Update the VALUE of the pin
                                int v = jObj.getInt("value");
                                v = v ^ 1;
                                Log.d(TAG, "Value of v: "+v);
                                jObj.put("value", v);

                                // TODO, Sync this with the database
                                object.put(devicePinData, jObj);
                                object.saveInBackground();

                                Log.d(TAG, "Saved in background");

                            } catch (JSONException e1) {
                                Log.wtf(TAG, "Not a valid json object");
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Timeout: Cannot connect to server", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Cannot find parse server");

                            //TODO, Error Toast here
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
