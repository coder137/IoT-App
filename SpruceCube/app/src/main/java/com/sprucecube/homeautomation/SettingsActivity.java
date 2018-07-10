package com.sprucecube.homeautomation;

import android.content.SharedPreferences;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        HelperMethods.addToolbar(this);
        HelperMethods.setToolbarDefault(this, "Settings");

        sharedPreferences = getSharedPreferences(Params.SETTINGS, MODE_PRIVATE);
        String host_ip = sharedPreferences.getString(String.valueOf(R.id.settings_host_url), "");

        TextView settings_info_tv = findViewById(R.id.settings_info);

        if(host_ip.equals(""))
        {
            settings_info_tv.setText("Host IP not set");
        }
        else
        {
            settings_info_tv.setText("Host IP: "+host_ip);
        }
    }

    public void saveSettingsClick(View view)
    {
        EditText host_et = findViewById(R.id.settings_host_url);
        //EditText slave_et = findViewById(R.id.settings_slave_url);

        if(host_et.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill up the Blank", Toast.LENGTH_SHORT).show();
            return ;
        }

        sharedPreferences = getSharedPreferences(Params.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(String.valueOf(R.id.settings_host_url), host_et.getText().toString());
        editor.apply();

        finish();
    }
}
