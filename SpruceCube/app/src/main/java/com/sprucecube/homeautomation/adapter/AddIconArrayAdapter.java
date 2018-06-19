package com.sprucecube.homeautomation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.classes.ImageItemClass;

import java.util.ArrayList;
import java.util.List;

public class AddIconArrayAdapter extends ArrayAdapter<ImageItemClass>
{

    ArrayList<ImageItemClass> list;
    LayoutInflater inflater;
    int groupId;

    public AddIconArrayAdapter(@NonNull Context context, int groupId, int id, @NonNull ArrayList<ImageItemClass> objects) {
        super(context, id, objects);

        this.list = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupId = groupId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View itemView = inflater.inflate(groupId, parent, false);

        ImageView imageView = itemView.findViewById(R.id.icon);
        imageView.setImageResource(list.get(position).getImageId());

        //return super.getView(position, convertView, parent);
        TextView textView = itemView.findViewById(R.id.icon_name);
        textView.setText(list.get(position).getText());
//        textView.setCompoundDrawablesWithIntrinsicBounds(list.get(position).getImageId(), 0, 0, 0);

        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);

        return getView(position, convertView, parent);
    }
}
