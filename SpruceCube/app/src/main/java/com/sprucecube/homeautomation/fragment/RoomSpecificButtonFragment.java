package com.sprucecube.homeautomation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprucecube.homeautomation.R;
import com.sprucecube.homeautomation.misc.AndroidFileHandler;
import com.sprucecube.homeautomation.misc.Params;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomSpecificButtonFragment extends Fragment {

    private static final String TAG = "RoomSpecificButtonFrag";

    String file_identifier;

    public RoomSpecificButtonFragment() {
        // Required empty public constructor
    }

    public static RoomSpecificButtonFragment newInstance(String file_identifier) {
        RoomSpecificButtonFragment fragment = new RoomSpecificButtonFragment();
        Bundle args = new Bundle();
        args.putString(Params.FILE_IDENTIFIER, file_identifier);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            file_identifier = getArguments().getString(Params.FILE_IDENTIFIER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_devices, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "OnActivityCreated");


    }
}
