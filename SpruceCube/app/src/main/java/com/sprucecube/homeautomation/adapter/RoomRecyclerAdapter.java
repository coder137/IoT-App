package com.sprucecube.homeautomation.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.Params;

import java.io.File;
import java.util.Arrays;

/**
 * RoomRecyclerAdapter
 * CLEANED: 21.06.18
 */
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
            roomImageView = itemView.findViewById(R.id.room_image);
        }
    }

    private String[] rooms;
    private String[] roomImageId;

    private OnItemClickListener listener = null;
    private OnLongItemClickListener longListener = null;

    public RoomRecyclerAdapter()
    {
        rooms = null;
        roomImageId = null;
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
            //Set the image size here (100 pixel by 100 pixel)
            holder.roomImageView.getLayoutParams().height = 100;
            holder.roomImageView.getLayoutParams().width = 100;

            //Log.d(TAG, "roomImageId: "+roomImageId[position]);

            //NOTE, Split the data and attach id imageView
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
