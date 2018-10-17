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
import android.widget.Toast;

import com.sprucecube.homeautomation.DevicesActivity;
import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.adapter.AddIconArrayAdapter;
import com.sprucecube.homeautomation.classes.ImageItemClass;
import com.sprucecube.homeautomation.misc.Params;

import java.util.ArrayList;

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
                saveClick(view);
            }
        });

        //TODO, Add Stuff to spinner here
        ArrayList<ImageItemClass> list = new ArrayList<>();
        list.add(new ImageItemClass(R.mipmap.waterheater, "WATERHEATER"));
        list.add(new ImageItemClass(R.mipmap.ac, "AC"));
        list.add(new ImageItemClass(R.mipmap.fan, "FAN"));
        list.add(new ImageItemClass(R.mipmap.light, "LIGHT"));
        list.add(new ImageItemClass(R.mipmap.plugpoint, "PLUGPOINT"));
        list.add(new ImageItemClass(R.mipmap.tv, "TV"));

        Spinner addButtonIconSpinner = activity.findViewById(R.id.add_button_icon_spinner);
        AddIconArrayAdapter adapter = new AddIconArrayAdapter(activity, R.layout.add_icon_row, R.id.icon_name, list);
        addButtonIconSpinner.setAdapter(adapter);
    }

    //TODO, Clean this function
    void saveClick(View view)
    {
        Activity activity = getActivity();

        EditText nameText = activity.findViewById(R.id.edittext_get_button_name);
        String name = nameText.getText().toString();

        EditText urlText = activity.findViewById(R.id.edittext_get_button_url);
        String url = urlText.getText().toString();
        if(url.trim().equals("") || name.trim().equals(""))
        {
            Toast.makeText(activity, "Button NAME or URL cannot be empty", Toast.LENGTH_SHORT).show();
            return ;
        }

        Spinner pinSpinner = activity.findViewById(R.id.spinner_choose_button_pin);
        int pin_number = pinSpinner.getSelectedItemPosition();

        Spinner modeSpinner = activity.findViewById(R.id.spinner_choose_button_function);
        String pin_mode_string = modeSpinner.getSelectedItem().toString();
        int pin_mode = modeSpinner.getSelectedItemPosition();

        Spinner buttonIconSpinner = activity.findViewById(R.id.add_button_icon_spinner);
        ImageItemClass imageData = (ImageItemClass) buttonIconSpinner.getSelectedItem();

        //TODO, Remove if needed
        Log.d(TAG, "pin_number: "+pin_number);
        Log.d(TAG, "pin_mode: "+pin_mode);
        Log.d(TAG, imageData.getText());
        Log.d(TAG, String.valueOf(imageData.getImageId()));
        Log.d(TAG, identification_id);

        //NOTE, We HAVE TO POPBACK STACK BEFORE Shared Preferences
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

        DevicesActivity.nav_id = String.valueOf(identification_id.split(":")[0]);
        Log.d(TAG, DevicesActivity.nav_id +" :updated");

        //DONE, Save to shared preferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Params.PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //TODO, Clean this later
        editor.putString(identification_id, url+":"+pin_mode+":"+imageData.getImageId());

        //TODO, This is VERY IMPORTANT
        editor.putInt(identification_id+":"+Params.TAG_IMAGE_ID, imageData.getImageId());
        editor.putString(identification_id+":"+Params.TAG_URL, url);
        editor.putString(identification_id+":"+Params.TAG_NAME, name);

//        editor.putString(identification_id+":"+Params.TAG_PIN_TYPE, pin_mode);
        // NOTE, These are the schemas
        editor.putString(identification_id+":"+Params.TAG_PIN_TYPE_STRING, pin_mode_string.toLowerCase());
        editor.putInt(identification_id+":"+Params.TAG_PIN_TYPE, pin_mode);
        editor.putInt(identification_id+":"+Params.TAG_PIN_NUMBER, pin_number);

        editor.apply(); //we want it to block the thread (use apply if you want it async)
    }
}
