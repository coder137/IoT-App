package com.sprucecube.homeautomation.misc;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import com.sprucecube.homeautomation.R;

public class HelperMethods
{

    private static final String TAG = "HelperMethods";

    public static Toolbar addToolbar(AppCompatActivity activity)
    {
        Log.d(TAG, "addToolbar");
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        int color = ContextCompat.getColor(activity, R.color.white);
        toolbar.setTitleTextColor(color);

        return toolbar;
    }

    public static void setToolbarDefault(AppCompatActivity activity, String title)
    {
        Log.d(TAG, "setToolbarDefaults");
        //Enable back button
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(title);
    }

    /**
     * @param activity
     * @param fragment
     * @param backStack
     *
     * Start a fragment here
     */
    public static void startFragmentMethod(AppCompatActivity activity, Fragment fragment, boolean backStack)
    {
        Log.d(TAG, "startFragmentMethod");
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if(backStack)
        {
            fragmentManager.beginTransaction().replace(R.id.fragment_view, fragment).addToBackStack(Params.BACK_STACK).commit();
        }
        else
        {
            fragmentManager.beginTransaction().replace(R.id.fragment_view, fragment).commit();
        }
    }

    public static void startFragmentMethod(AppCompatActivity activity, Fragment fragment, String backStackString)
    {
        Log.d(TAG, "startFragmentMethod: Overload backStackString");
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment_view, fragment).addToBackStack(backStackString).commit();
    }

    //TODO, Clean this function up
    public static void updateButtonTags(AppCompatActivity activity, String nav_id, SharedPreferences sharedPreferences)
    {
        for(int i=1;i<=9;i++)
        {
            final int button_id = activity.getResources().getIdentifier("button" + i, "id", activity.getPackageName());
            if (button_id == 0)
            {
                Log.wtf(TAG, "Button ID is 0");
            }

            Button genericButton = activity.findViewById(button_id);

            setButtonArtifacts(nav_id, button_id, sharedPreferences, genericButton);
        }
    }

    public static int getButtonId(AppCompatActivity activity, int i)
    {
        return activity.getResources().getIdentifier("button" + i, "id", activity.getPackageName());
    }

    //TODO, CLEAN THIS
    public static boolean setButtonArtifacts(String nav_id, int button_id, SharedPreferences sharedPreferences, Button genericButton)
    {
        //get the current nav_id
        String identification = nav_id+":"+button_id;

//        Log.d(TAG, identification);

        //String buttonData = sharedPreferences.getString(identification,null);
        String buttonNameData = sharedPreferences.getString(identification+":"+Params.TAG_NAME, null);
        int buttonImageData = sharedPreferences.getInt(identification+":"+Params.TAG_IMAGE_ID, 0);

        if(buttonNameData != null)
        {
//            Log.d(TAG, buttonNameData);
            //String[] buttonSpecificData = buttonData.trim().split(":");
            genericButton.setText(buttonNameData);

            //TODO, This might be NULL
//            Log.d(TAG, String.valueOf(buttonImageData));
            genericButton.setCompoundDrawablesWithIntrinsicBounds(buttonImageData,0, 0, 0);
            return true;
        }
        return false;
    }
}
