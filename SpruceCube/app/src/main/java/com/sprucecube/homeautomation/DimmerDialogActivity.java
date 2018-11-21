package com.sprucecube.homeautomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sprucecube.homeautomation.misc.Params;

import org.json.JSONException;
import org.json.JSONObject;

public class DimmerDialogActivity extends AppCompatActivity {

    private static final String TAG = "DimmerDialogAct";

//    String dimmerPin, ip_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer_dialog);

        // NOTE, FIXME
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
//        dimmerPin = intent.getStringExtra(Params.PIN_NUM);
        String deviceObjectData = intent.getStringExtra(Params.PIN_OBJECT_STRING);
        final String devicePinData = intent.getStringExtra(Params.PIN_NUM);
        Log.d(TAG, "deviceObjectData: " + deviceObjectData);
        Log.d(TAG, "devicePinData: " + devicePinData);

        final SeekBar seekBar = findViewById(R.id.dimmer_seekbar);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Devices");
        query.getInBackground(deviceObjectData, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {
                    final JSONObject jObj = object.getJSONObject(devicePinData);

                    // Update the TYPE of the pin
                    String type = null;
                    try {
                        type = jObj.getString("type");
                        if (!type.equals(Params.CONTROL_ACTION.toLowerCase())) {
                            Log.d(TAG, "Updated " + devicePinData + " to type CONTROL");
                            jObj.put("type", "control");
                            jObj.put("value", 0);
                        }

                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                int progress = seekBar.getProgress();
                                int conversion = 100-progress;

                                // Put value here
                                try {
                                    jObj.put("value", conversion);
                                    Log.d(TAG, "value: "+conversion);

                                    //DONE, Sync with Parse Server here
                                    object.put(devicePinData, jObj);
                                    object.saveInBackground();
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Cannot connect to Server");
                }
            }
        });
    }


    public void acceptDimmerClick(View view)
    {
        finish();
    }
}
