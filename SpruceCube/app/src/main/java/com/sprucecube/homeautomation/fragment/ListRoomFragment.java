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

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

/**
 * ListRoomFragment
 * CLEANED: 21.06.18
 */
public class ListRoomFragment extends Fragment {

    private static final String TAG = "ListRoomFragment";
    static final int ADD_CODE = 1;
    static final int MOD_CODE = 2;

    RecyclerView recyclerView;
    RoomRecyclerAdapter adapter;

    String[] rooms;
    String[] roomImageIds;

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

        addRoomCardAction(activity);

        //Initialize Recycler view here
        recyclerViewInitialize(activity);

        //Add shit here
        rooms = readFromFile(Params.ROOM_FILE);
        roomImageIds = readFromFile(Params.ROOM_IMAGE_FILE);

        Log.d(TAG,"rooms: "+Arrays.toString(rooms));
        Log.d(TAG, "roomImageIds: "+Arrays.toString(roomImageIds));

        //We now update the view
        adapter.updateRooms(rooms, roomImageIds);

        //We can create a RecyclerView Listener here, use it to attach the view to an intent
        adapter.setListener(new RoomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view)
            {
                fillButtonCardAction(view);
            }
        });

        adapter.setLongListener(new RoomRecyclerAdapter.OnLongItemClickListener()
        {
            @Override
            public void onLongItemClick(View view)
            {
                modifyCardAction(view);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && (requestCode == ADD_CODE || requestCode == MOD_CODE))
        {
            //DONE, Refresh the Recycler view here
            //Open the file here readfrom it and add it again
            Log.d(TAG, "RequestCode: "+requestCode);

            rooms = readFromFile(Params.ROOM_FILE);
            roomImageIds = readFromFile(Params.ROOM_IMAGE_FILE);
            Log.d(TAG, Arrays.toString(rooms));

            adapter.updateRooms(rooms, roomImageIds);
        }
    }


    ////NOTE, Own functions start from here
    void recyclerViewInitialize(Activity activity)
    {
        recyclerView = activity.findViewById(R.id.fragment_room_recyclerview);
        recyclerView.setHasFixedSize(true);

        //Use Linear Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Specify adapter
        adapter = new RoomRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    void fillButtonCardAction(View view)
    {
        CardView roomCard = view.findViewById(R.id.room_card);
        TextView textFromRoomCard = roomCard.findViewById(R.id.room_text);

        String roomName = textFromRoomCard.getText().toString();
        Log.d(TAG, "Button Clicked: "+roomName);

        //HelperMethods.GenericFillButtonFragmentMethod((AppCompatActivity) getActivity(), roomName, roomName, true);
        Fragment fragment = GenericFillButtonFragment.newInstance(roomName, roomName);
        HelperMethods.startFragmentMethod((AppCompatActivity) getActivity(), fragment, true);
    }

    ///Own Function Declarations here
    void modifyCardAction(View view)
    {
        Log.d(TAG, "modifyCardAction");

        int cardViewId = view.getId();
        CardView cardView = view.findViewById(cardViewId);
        TextView cardTextView = cardView.findViewById(R.id.room_text);

        String roomName = cardTextView.getText().toString();

        //NOTE, We find the imageIndexNo along with the cardView
        ArrayList<String> listRooms = new ArrayList<>(Arrays.asList(rooms));
        int imageIndexNo = listRooms.indexOf(roomName);

        //TODO, Remove if not needed
        Log.d(TAG, "Button Clicked: "+roomName);
        Log.d(TAG, "imageIndexNo: "+imageIndexNo);
        Log.d(TAG, "roomImageId: "+roomImageIds[imageIndexNo]);

        //DONE, Start an Activity here and supply the name here (If needed we can change the value here)
        Intent modOrDeleteIntent = new Intent(getActivity(), ModifyRoomActivity.class);
        modOrDeleteIntent.putExtra(Params.ROOM_TITLE, cardTextView.getText().toString());
        modOrDeleteIntent.putExtra(Params.ROOM_IMAGE_ID, roomImageIds[imageIndexNo] );
        startActivityForResult(modOrDeleteIntent, MOD_CODE);
    }

    void addRoomCardAction(final Activity activity)
    {
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
    }

    String[] readFromFile(String filename)
    {
        String data = AndroidFileHandler.readDataFromFile(getContext(), filename);
        if (data != null)
        {
            return data.split("\n");
        }
        return null;
    }
}
