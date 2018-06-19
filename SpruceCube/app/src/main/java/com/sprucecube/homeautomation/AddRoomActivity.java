package com.sprucecube.homeautomation;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sprucecube.homeautomation.adapter.AddIconArrayAdapter;
import com.sprucecube.homeautomation.classes.ImageItemClass;
import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import java.io.File;
import java.util.ArrayList;

public class AddRoomActivity extends AppCompatActivity {

    private static final String TAG = "AddRoomActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        HelperMethods.addToolbar(this);
        HelperMethods.setToolbarDefault(this, "Add New Room");

        //DONE, Make a custom ArrayAdapter
        //TODO, Make this neater (ADD FILE or something)
        //TODO, Add this to a function
        ArrayList<ImageItemClass> list = new ArrayList<>();
        list.add(new ImageItemClass(R.mipmap.bedroom, "BEDROOM"));
        list.add(new ImageItemClass(R.mipmap.dining, "DINING"));
        list.add(new ImageItemClass(R.mipmap.kitchen, "KITCHEN"));
        list.add(new ImageItemClass(R.mipmap.livingroom, "LIVINGROOM"));

        Spinner addRoomIconSpinner = findViewById(R.id.add_room_icon_spinner);
        AddIconArrayAdapter adapter = new AddIconArrayAdapter(this, R.layout.add_icon_row, R.id.icon_name, list);
        addRoomIconSpinner.setAdapter(adapter);

        //TODO, DEBUGGING (TO DELETE RECENT FILE)
//        File file = new File(getFilesDir(), Params.ROOM_FILE);
//        Log.d(TAG, file.delete() + " :file is deleted");
    }

    public void saveRoomInfoClick(View view)
    {
        EditText roomNameText = findViewById(R.id.add_room_edittext);
        String roomName = roomNameText.getText().toString();

        if(roomName.equals(""))
        {
            Toast.makeText(this, "Cannot leave Room Name empty", Toast.LENGTH_SHORT).show();
            return ;
        }

        Spinner roomIconSpinner = findViewById(R.id.add_room_icon_spinner);
        ImageItemClass data = (ImageItemClass) roomIconSpinner.getSelectedItem();
        Log.d(TAG, data.getText());
        Log.d(TAG, String.valueOf(data.getImageId()));

        AndroidFileHandler.writeDataToFileAppend(this, Params.ROOM_FILE, roomName+"\n");
        AndroidFileHandler.writeDataToFileAppend(this, Params.ROOM_IMAGE_FILE, roomName+":"+data.getImageId()+"\n");

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
