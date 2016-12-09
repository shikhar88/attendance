package com.example.easyattendance.attendance;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateclassFragment extends Fragment implements View.OnClickListener {
    View rootview;
    public CreateclassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_createclass, container, false);
        rootview = view;
        DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM classroom",null);
        try{
            while (cursor.moveToNext()) {
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearlayouttest);
                Button button = new Button(getActivity()); // needs activity context
                button.setText(cursor.getString( cursor.getColumnIndex("class_name") ));
                layout.addView(button);
            }
        }finally {

            cursor.close();
        }
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
//                                cursor.close();
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
                                    DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
                                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                    Cursor cursor = db.rawQuery("SELECT * FROM classroom WHERE class_name = ?",new String[] {classname + ""});
                                    try{
                                        if(cursor.getCount() > 0) {
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Class already exists", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            ContentValues values = new ContentValues();
                                            values.put("class_name", classname);
                                            values.put("class_year", classyear);
                                            db.insert("classroom", null, values);
                                            db.close(); // Closing database connection
                                            LinearLayout layout = (LinearLayout) rootview.findViewById(R.id.linearlayouttest);
                                            Button button = new Button(getActivity()); // needs activity context
                                            button.setText(classname);
                                            layout.addView(button);
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Class room has been created", Toast.LENGTH_SHORT).show();
                                        }
                                    }finally {

                                        cursor.close();
                                    }


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
