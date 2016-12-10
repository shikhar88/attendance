package com.example.easyattendance.attendance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends Fragment {

    View rootview;
    public ClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        String class_name,class_id;
        Bundle args = getArguments();
        if(args != null){
            if (args.containsKey("class_name"))
                class_name = args.getString("user_name");
            if (args.containsKey("class_id"))
                class_id = args.getString("user_name");
        }
        return rootview;
    }

}
