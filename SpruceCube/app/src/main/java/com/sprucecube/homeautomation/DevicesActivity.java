package com.sprucecube.homeautomation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.sprucecube.homeautomation.fragment.ListRoomFragment;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

public class DevicesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "DevicesActivity";

    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    //There are going to be a few navigation (this is for the home screen favourites navigation)
    public static String nav_id = String.valueOf(R.id.navigation_favourite);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        Toolbar toolbar = HelperMethods.addToolbar(this);

        //DONE, Adding GenericFill Button Methods here
        HelperMethods.GenericFillButtonFragmentMethod(this, String.valueOf(nav_id), "Favourites", false);

        //This is needed
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //NOT POSSIBLE, Add navigation items dynamically here

        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        //DONE, Apparently this method is better
        sharedPreferences = getSharedPreferences(Params.PREFS, MODE_PRIVATE);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener()
        {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
            {
                //FIXME, Highly dependent on nav_id tag
                Log.d(TAG, "Shared Preference has updated buttons");
                HelperMethods.updateButtonTags(DevicesActivity.this, nav_id, sharedPreferences);
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //DONE, BottomNavigationListener
                    return BottomNavigationClickListener(item);
                }
            };


    boolean BottomNavigationClickListener(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.navigation_favourite:
            {
                //DONE, Make this the favourites tab
                nav_id = String.valueOf(item.getItemId()); //NOTE, We get the item id here
                HelperMethods.GenericFillButtonFragmentMethod(this, nav_id, "Favourites", true);
                Log.d(TAG, "Favourite Tab created");
                return true;
            }
            case R.id.navigation_rooms:
            {
                ListRoomFragment listRoomFragment = new ListRoomFragment();
                HelperMethods.startFragmentMethod(this, listRoomFragment, true);
                return true;
            }
            case R.id.navigation_analytics:
            {
                Toast.makeText(DevicesActivity.this, "Return Analytics", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.devices, menu);

        //TODO, Attach action to the top Switch
        SwitchCompat sw = menu.findItem(R.id.mySwitch).getActionView().findViewById(R.id.switchForActionBar);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    Toast.makeText(DevicesActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DevicesActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            //TODO, Change this to analytics
            case R.id.action_settings:
            {
                //TODO, Create a settings activity,
                //TODO, Add all the settings to the Preferences
                Toast.makeText(this, "Settings has been clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //TODO, Uncomment this if you need SIDEBAR NAVIGATION
//        if(id == R.id.nav_room)
//        {
//
//        }
//        else if (id == R.id.nav_favourite)
//        {
//
//        }
//        else if (id == R.id.nav_bedroom)
//        {
//
//        }
//        else if (id == R.id.nav_hall)
//        {
//
//        }
//        else if (id == R.id.nav_kitchen)
//        {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

