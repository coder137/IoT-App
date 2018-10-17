package com.sprucecube.homeautomation;

import android.content.Intent;
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

    //String dimmerPin, ip_address;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer_dialog);

        // NOTE, This is IMPORTANT ( FIXME )
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        getWindow().setLayout((6*width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        url = intent.getStringExtra(Params.PIN_DIMMING_URL);
        Log.d(TAG, "final_url: "+url);
        //dimmerPin = intent.getStringExtra(Params.PIN_NUM);
        //Log.d(TAG, dimmerPin);

        //SharedPreferences sharedPreferences = getSharedPreferences(Params.SETTINGS, Context.MODE_PRIVATE);
        //ip_address = sharedPreferences.getString(String.valueOf(R.id.settings_host_url), "");

        SeekBar seekBar = findViewById(R.id.dimmer_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                postControlProgress(seekBar);
            }
        });
    }

    void postControlProgress(SeekBar seekBar)
    {
        int progress = seekBar.getProgress();
        int conversion = 100-progress;

        Log.d(TAG, progress+"");
        Log.d(TAG, conversion+"");

        Log.d(TAG, url+conversion);

        AsyncHTTP asyncHTTP = new AsyncHTTP(POST_METHOD, new AsyncHTTP.CallbackReceived() {
            @Override
            public void onResponseReceived(String result)
            {
                if (result == null)
                {
                    Toast.makeText(DimmerDialogActivity.this, "Request: Timeout", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DimmerDialogActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        asyncHTTP.execute(url+conversion);
    }

    public void acceptDimmerClick(View view)
    {
        finish();
    }
}
