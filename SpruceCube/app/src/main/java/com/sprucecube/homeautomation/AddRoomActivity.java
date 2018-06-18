package com.sprucecube.homeautomation;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import java.io.File;

public class AddRoomActivity extends AppCompatActivity {

    private static final String TAG = "AddRoomActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        HelperMethods.addToolbar(this);
        HelperMethods.setToolbarDefault(this, "Add New Room");

        //TODO, DEBUGGING (TO DELETE RECENT FILE)
//        File file = new File(getFilesDir(), Params.ROOM_FILE);
//        Log.d(TAG, file.delete() + " :file is deleted");
    }

    public void saveRoomInfoClick(View view)
    {
        EditText roomNameText = findViewById(R.id.add_room_edittext);
        String roomName = roomNameText.getText().toString();

        //DONE, Add this to the file
        AndroidFileHandler.writeDataToFileAppend(this, Params.ROOM_FILE, roomName+"\n");

//        String data = AndroidFileHandler.readDataFromFile(this, Params.ROOM_FILE);
//        Log.d(TAG, data);

        //WORKS, Check if this hits the request (ListRoomFragment)
        Intent sendBackIntent = new Intent();
        //TODO, we never use this!
        sendBackIntent.putExtra(Params.UPDATE_BOOL, true);
        setResult(RESULT_OK, sendBackIntent);
        finish(); //return from here
    }
}
