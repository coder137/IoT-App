package com.sprucecube.homeautomation.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprucecube.homeautomation.R;

import java.util.Arrays;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.RoomHolder>
{
    private static final String TAG = "RoomRecyclerAdapter";

    // We init everything here
    class RoomHolder extends RecyclerView.ViewHolder
    {

        TextView roomText;
        ImageView roomImageView;

        RoomHolder(View itemView)
        {
            super(itemView);
            roomText = itemView.findViewById(R.id.room_text);

            //TODO, Set ImageView stuff here
            roomImageView = itemView.findViewById(R.id.room_image);

        }
    }

    //TODO, Add images later, if possible
    private String[] rooms;
    private String[] roomImageId;

    private OnItemClickListener listener = null;
    private OnLongItemClickListener longListener = null;
    public RoomRecyclerAdapter(String[] rooms)
    {
        this.rooms = rooms;
    }

    public void updateRooms(String[] rooms)
    {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    public RoomRecyclerAdapter(String[] rooms, String[] roomImageId)
    {
        this.rooms = rooms;
        this.roomImageId = roomImageId;
    }

    //updateRooms
    public void updateRooms(String[] rooms, String[] roomImageId)
    {
        this.rooms = rooms;
        this.roomImageId = roomImageId;
        notifyDataSetChanged();
    }


    public void setListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public void setLongListener(OnLongItemClickListener longListener)
    {
        this.longListener = longListener;
    }

    @Override
    public RoomHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_card_layout, parent, false);
        return new RoomHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RoomHolder holder, int position)
    {
        if(rooms != null)
        {
            holder.roomText.setText(rooms[position]);
        }

        if(roomImageId != null)
        {
            //DONE, Set the image here
            holder.roomImageView.getLayoutParams().height = 100;
            holder.roomImageView.getLayoutParams().width = 100;

            String[] imageStringId = roomImageId[position].split(":");
            Log.d(TAG, Arrays.toString(imageStringId));
            holder.roomImageView.setImageResource(Integer.parseInt(imageStringId[1]));
        }


        if(listener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view);
                }
            });
        }

        if(longListener != null)
        {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longListener.onLongItemClick(view);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        if(rooms != null)
        {
            return rooms.length;
        }

        return 0;
    }


    //NOTE, Setting OnClick listener here
    public interface OnItemClickListener
    {
        void onItemClick(View view);
    }

    public interface OnLongItemClickListener
    {
        void onLongItemClick(View view);
    }



}
