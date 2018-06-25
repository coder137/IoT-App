package com.sprucecube.homeautomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import java.io.File;

/**
 * TODO, Clean this
 */
public class ModifyRoomActivity extends AppCompatActivity {

    private static final String TAG = "ModifyRoomActivity";
    String room_title;
    String room_image_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_room);

        HelperMethods.addToolbar(this);
        HelperMethods.setToolbarDefault(this, "Modify or Delete");

        Intent intent = getIntent();
        room_title = intent.getStringExtra(Params.ROOM_TITLE);
        room_image_id = intent.getStringExtra(Params.ROOM_IMAGE_ID);

        Log.d(TAG, "room_title: "+room_title);
        Log.d(TAG, "room_image_id: "+room_image_id);

        //set the room title here
        TextView textView = findViewById(R.id.tv_current_room_name);
        textView.setText(room_title);
    }

    //TODO, Review this function again
    public void modifyRoomNameButtonClick(View view)
    {
        Log.d(TAG, "ModifyRoomNameButtonClick");

        EditText etModify = findViewById(R.id.et_modify_room_name);
        String getModifyTextData = etModify.getText().toString();

        if(getModifyTextData.equals(""))
        {
            Toast.makeText(this, "Do not leave Text blank", Toast.LENGTH_SHORT).show();
            return ;
        }

        //TODO, Replace this with a function
        String data = AndroidFileHandler.readDataFromFile(this, Params.ROOM_FILE);
        Log.d(TAG, data);
        String replacedData = data.replace(room_title, getModifyTextData);

        AndroidFileHandler.writeDataToFile(this, Params.ROOM_FILE, replacedData);

        Intent sendBackIntent = new Intent();
        setResult(RESULT_OK, sendBackIntent);
        finish(); //return from here
    }

    public void deleteRoomButtonClick(View view)
    {
        Log.d(TAG, "DeleteRoomButtonClick");

        //NOT NEEDED, Replace this with a function
        String data = AndroidFileHandler.readDataFromFile(this, Params.ROOM_FILE);
        String replacedData = data.replace(room_title+"\n", "");

        //NOTE, We need to check if we are writing empty data to our file or no
        if(replacedData.trim().equals(""))
        {
            Log.wtf(TAG, "Replaced Data is empty");

            //DONE, Right here is where we DELETE BOTH THE FILES
            boolean delRoom = deleteInternalFile(Params.ROOM_FILE);
            boolean delImageRoom = deleteInternalFile(Params.ROOM_IMAGE_FILE);

            Log.d(TAG, String.valueOf(delRoom));
            Log.d(TAG, String.valueOf(delImageRoom));

            finishOnActivityResultIntent();
        }
        else
        {
            Log.d(TAG, "Data written to imageFile");

            String imageData = AndroidFileHandler.readDataFromFile(this, Params.ROOM_IMAGE_FILE);
            String replaceImageData = imageData.replace(room_image_id + "\n", "");

            //NOTE, Write data to both the files
            AndroidFileHandler.writeDataToFile(this, Params.ROOM_FILE, replacedData);
            AndroidFileHandler.writeDataToFile(this, Params.ROOM_IMAGE_FILE, replaceImageData);

            finishOnActivityResultIntent();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean deleteInternalFile(String filename)
    {
        File file = new File(getFilesDir(), filename);
        return file.delete();
    }

    void finishOnActivityResultIntent()
    {
        Intent sendBackIntent = new Intent();
        setResult(RESULT_OK, sendBackIntent);
        finish();
    }
}
