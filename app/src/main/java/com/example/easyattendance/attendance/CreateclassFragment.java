package com.example.easyattendance.attendance;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateclassFragment extends Fragment implements View.OnClickListener {
    public CreateclassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_createclass, container, false);
        Button createClass = (Button)view.findViewById(R.id.button_createclass);
        createClass.setOnClickListener(this);
        return view;
    }

    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.button_createclass:
                LayoutInflater layoutInflaterAndroid = getActivity().getLayoutInflater();
                final View mView = layoutInflaterAndroid.inflate(R.layout.createclass, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(view.getContext());
                alertDialogBuilderUserInput.setView(mView);
                final EditText classnameText = (EditText) mView.findViewById(R.id.classnameInput);
                final EditText classyearText = (EditText) mView.findViewById(R.id.classyearInput);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                final String classname = classnameText.getText().toString();
                                final String classyear = classyearText.getText().toString();
                                if (classname.length() == 0 && classyear.length() == 0){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Fields are empty", Toast.LENGTH_SHORT).show();
                                }
                                else if (classyear.length() == 0){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Year field is empty", Toast.LENGTH_SHORT).show();
                                }
                                else if ( classname.length() == 0 ){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Class name field is empty", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                }
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                break;
        }
    }

}
