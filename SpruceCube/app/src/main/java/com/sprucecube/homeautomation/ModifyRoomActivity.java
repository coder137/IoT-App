package com.sprucecube.homeautomation;

import android.content.Intent;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

public class ModifyRoomActivity extends AppCompatActivity {

    private static final String TAG = "ModifyRoomActivity";
    String room_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_room);

        HelperMethods.addToolbar(this);
        HelperMethods.setToolbarDefault(this, "Modify or Delete");

        Intent intent = getIntent();
        room_title = intent.getStringExtra(Params.ROOM_TITLE);

        //set the room title here
        TextView textView = findViewById(R.id.tv_current_room_name);
        textView.setText(room_title);
    }

    public void modifyRoomNameButtonClick(View view)
    {
        Log.d(TAG, "ModifyRoomNameButtonClick");

        EditText etModify = findViewById(R.id.et_modify_room_name);
        String getModifyTextData = etModify.getText().toString();

        if(!getModifyTextData.equals(""))
        {
            //TODO, Replace this with a function
            String data = AndroidFileHandler.readDataFromFile(this, Params.ROOM_FILE);
            Log.d(TAG, data);
            String replacedData = data.replace(room_title, getModifyTextData);

            AndroidFileHandler.writeDataToFile(this, Params.ROOM_FILE, replacedData);

            Intent sendBackIntent = new Intent();
            setResult(RESULT_OK, sendBackIntent);
            finish(); //return from here
        }
        else
        {
            Toast.makeText(this, "Do not leave Text blank", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRoomButtonClick(View view)
    {
        Log.d(TAG, "DeleteRoomButtonClick");
        //TODO, Replace this with a function
        String data = AndroidFileHandler.readDataFromFile(this, Params.ROOM_FILE);
        String replacedData = data.replace(room_title+"\n", "");

        AndroidFileHandler.writeDataToFile(this, Params.ROOM_FILE, replacedData);

        Intent sendBackIntent = new Intent();
        setResult(RESULT_OK, sendBackIntent);
        finish();
    }
}
