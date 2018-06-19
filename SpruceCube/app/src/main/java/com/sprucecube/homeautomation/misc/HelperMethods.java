package com.sprucecube.homeautomation.misc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.fragment.GenericFillButtonFragment;

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

    public static void GenericFillButtonFragmentMethod(AppCompatActivity activity, String nav_id, String room_name, boolean backStack)
    {
        Log.d(TAG, "GenericFillButtonFragmentMethod");
        GenericFillButtonFragment genericFillButtonFragment = GenericFillButtonFragment.newInstance(nav_id, room_name);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if(backStack)
        {
            fragmentManager.beginTransaction().replace(R.id.fragment_view, genericFillButtonFragment).addToBackStack(Params.BACK_STACK).commit();
        }
        else
        {
            fragmentManager.beginTransaction().replace(R.id.fragment_view, genericFillButtonFragment).commit();
        }
    }

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

    public static void updateButtonTags(AppCompatActivity activity, String nav_id, SharedPreferences sharedPreferences)
    {
        for(int i=1;i<=9;i++)
        {
            final int button_id = activity.getResources().getIdentifier("button" + i, "id", activity.getPackageName());

            if (button_id == 0)
            {
                Log.wtf(TAG, "Button ID is 0");
            }

            //get the current nav_id
            String data = nav_id + ":" + button_id;

            Button genericButton = activity.findViewById(button_id);
            String buttonData = sharedPreferences.getString(data, null);
            if (buttonData != null)
            {
                genericButton.setText(buttonData.replace(":", "\n"));
                Log.d(TAG, "DataAttached on Button: " + i);
            }
        }
    }
}
