package com.sprucecube.homeautomation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sprucecube.homeautomation.misc.AsyncHTTP;
import com.sprucecube.homeautomation.misc.Params;

import static com.sprucecube.homeautomation.misc.AsyncHTTP.POST_METHOD;

public class DimmerDialogActivity extends AppCompatActivity {

    private static final String TAG = "DimmerDialogAct";

    String dimmerPin, ip_address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer_dialog);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        getWindow().setLayout((6*width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        dimmerPin = intent.getStringExtra(Params.PIN_NUM);
        Log.d(TAG, dimmerPin);

        SharedPreferences sharedPreferences = getSharedPreferences(Params.SETTINGS, Context.MODE_PRIVATE);
        ip_address = sharedPreferences.getString(String.valueOf(R.id.settings_host_url), "");
    }

    public void cancelDimmerClick(View view)
    {
        finish();
    }

    public void acceptDimmerClick(View view)
    {
        SeekBar seekBar = findViewById(R.id.dimmer_seekbar);
        int progress = seekBar.getProgress();

        //DONE, Convert from 0-100 from 5-128
        int conversion = (int) (progress*1.23 +5);
        Log.d(TAG, "Progress: "+progress);
        Log.d(TAG, "Conversion: "+conversion);

        //DONE, Form the URL here and send it
        String url = "http://"+ip_address+"/DIMMING?"+dimmerPin+"="+conversion;
        Log.d(TAG, url);

        AsyncHTTP asyncHTTP = new AsyncHTTP(POST_METHOD, new AsyncHTTP.CallbackReceived() {
            @Override
            public void onResponseReceived(String result)
            {
                if(result == null)
                {
                    Toast.makeText(DimmerDialogActivity.this, "Request: Timeout", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DimmerDialogActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        asyncHTTP.execute(url);
        finish();

    }
}
