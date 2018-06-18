package com.sprucecube.homeautomation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprucecube.homeautomation.R;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.RoomHolder>
{
    private static final String TAG = "RoomRecyclerAdapter";

    // We init everything here
    class RoomHolder extends RecyclerView.ViewHolder
    {

        TextView roomText;

        RoomHolder(View itemView)
        {
            super(itemView);
            roomText = itemView.findViewById(R.id.room_text);
        }
    }

    //TODO, Add images later, if possible
    private String[] rooms;
    private OnItemClickListener listener = null;
    private OnLongItemClickListener longListener = null;
    public RoomRecyclerAdapter(String[] rooms)
    {
        this.rooms = rooms;
    }

    //updateRooms
    public void updateRooms(String[] rooms)
    {
        this.rooms = rooms;
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

            //TODO, We can set the image here later

            //TODO, Add a click here, or do it in the parent fragment
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
