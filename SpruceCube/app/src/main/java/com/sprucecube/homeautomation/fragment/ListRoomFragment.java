package com.sprucecube.homeautomation.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprucecube.homeautomation.AddRoomActivity;
import com.sprucecube.homeautomation.ModifyRoomActivity;
import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.adapter.RoomRecyclerAdapter;
import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.HelperMethods;
import com.sprucecube.homeautomation.misc.Params;

import java.lang.reflect.Array;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRoomFragment extends Fragment {

    private static final String TAG = "ListRoomFragment";
    static final int ADD_CODE = 1;
    static final int MOD_CODE = 2;

    RecyclerView recyclerView;
    RoomRecyclerAdapter adapter;

    public ListRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_room, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated");

        final Activity activity = getActivity();

        CardView addRoomButtonCard = activity.findViewById(R.id.add_room_button);
        TextView addRoomButtonText = addRoomButtonCard.findViewById(R.id.room_text);

        //Remove image from here
        ImageView addRoomImageView = addRoomButtonCard.findViewById(R.id.room_image);
        addRoomImageView.setImageResource(0);
        addRoomButtonText.setText("Add New Room Here");

        addRoomButtonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //DONE, Start a new room activity here
                Log.d(TAG, "Creating new Room");
                Intent intent = new Intent(activity, AddRoomActivity.class);
                startActivityForResult(intent, ADD_CODE);

            }
        });

        //Setting the Recycler View here
        //Add your recycler view here
        recyclerView = activity.findViewById(R.id.fragment_room_recyclerview);
        recyclerView.setHasFixedSize(true);

        //Use Linear Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Specify adapter
        String[] rooms = readFromFile(Params.ROOM_FILE);
        String[] roomImageIds = readFromFile(Params.ROOM_IMAGE_FILE);
        Log.d(TAG, Arrays.toString(roomImageIds));

//        adapter = new RoomRecyclerAdapter(rooms);
        adapter = new RoomRecyclerAdapter(rooms, roomImageIds);
        recyclerView.setAdapter(adapter);

        //We can create a RecyclerView Listener here, use it to attach the view to an intent
        adapter.setListener(new RoomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                //Do Stuff here
                CardView roomCard = view.findViewById(R.id.room_card);
                TextView textFromRoomCard = roomCard.findViewById(R.id.room_text);
                String roomName = textFromRoomCard.getText().toString();
                Log.d(TAG, "Button Clicked: "+roomName);

                HelperMethods.GenericFillButtonFragmentMethod((AppCompatActivity) getActivity(), roomName, roomName, true);
            }
        });

        adapter.setLongListener(new RoomRecyclerAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view) {
                Log.d(TAG, "Long Click Listner has fired");

                int cardViewId = view.getId();
                CardView cardView = view.findViewById(cardViewId);

                TextView cardViewText = cardView.findViewById(R.id.room_text);
                ImageView imageViewText = cardView.findViewById(R.id.room_image);
                Log.d(TAG, cardViewText.getText().toString());

                //DONE, Start an Activity here and supply the name here (If needed we can change the value here)
                Intent modOrDeleteIntent = new Intent(getActivity(), ModifyRoomActivity.class);
                modOrDeleteIntent.putExtra(Params.ROOM_TITLE, cardViewText.getText().toString());
                modOrDeleteIntent.putExtra(Params.ROOM_IMAGE_ID, String.valueOf(imageViewText.getId()) );
                startActivityForResult(modOrDeleteIntent, MOD_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == ADD_CODE)
        {
            Log.d(TAG, "We get the data from AddRoomActivity");

            //DONE, Refresh the Recycler view here
            //Open the file here readfrom it and add it again
            adapter.updateRooms(readFromFile(Params.ROOM_FILE), readFromFile(Params.ROOM_IMAGE_FILE));
        }

        if(resultCode == RESULT_OK && requestCode == MOD_CODE)
        {
            Log.d(TAG, "We need to modify room");
            adapter.updateRooms(readFromFile(Params.ROOM_FILE), readFromFile(Params.ROOM_IMAGE_FILE));
        }
    }

    String[] readFromFile(String filename)
    {
        //FIXED, Used to crash here (throw an exception when file was not present)
        String data = AndroidFileHandler.readDataFromFile(getContext(), filename);
        //Log.d(TAG, String.valueOf(data));
        if (data != null)
        {
            return data.split("\n");
        }
        return null;
    }

//    String[] readImageTagFromFile(String filename)
//    {
//        return nu
//    }
}
