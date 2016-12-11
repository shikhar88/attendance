package com.example.easyattendance.attendance;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.design.R.id.right;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends Fragment implements View.OnClickListener{

    View rootview;
    String class_name,class_id = "";
    public ClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        Bundle args = this.getArguments();
        if(args != null){
            if (args.containsKey("class_name"))
                class_name = args.getString("class_name");
            if (args.containsKey("class_id"))
                class_id = args.getString("class_id");
        }
        Button createClass = (Button)rootview.findViewById(R.id.button_addstudent);
        createClass.setOnClickListener(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String getQuery = "SELECT * FROM student WHERE class_id = '" + class_id +"'";
        Cursor cursor = db.rawQuery(getQuery,null);
        try{
            while (cursor.moveToNext()) {
                String studentName = cursor.getString( cursor.getColumnIndex("name") );
                String rollNo = cursor.getString( cursor.getColumnIndex("rollno") );
                tableStudent(studentName,rollNo);
            }
        }finally {

            cursor.close();
        }
        return rootview;
    }
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.button_addstudent:
                LayoutInflater layoutInflaterAndroid = getActivity().getLayoutInflater();
                final View mView = layoutInflaterAndroid.inflate(R.layout.addstudent, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(view.getContext());
                alertDialogBuilderUserInput.setView(mView);
                final EditText studentnameText = (EditText) mView.findViewById(R.id.studentnameInput);
                final EditText rollnoText = (EditText) mView.findViewById(R.id.rollnoInput);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
//                                cursor.close();
                                final String studentname = studentnameText.getText().toString();
                                final String rollno = rollnoText.getText().toString();
                                if (studentname.length() == 0 && rollno.length() == 0){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Fields are empty", Toast.LENGTH_SHORT).show();
                                }
                                else if (studentname.length() == 0){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Student name field is empty", Toast.LENGTH_SHORT).show();
                                }
                                else if ( rollno.length() == 0 ){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Roll no field is empty", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    DatabaseHelper mDbHelper = new DatabaseHelper(getContext());
                                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("class_id",class_id);
                                    values.put("name", studentname);
                                    values.put("rollno", rollno);
                                    Integer dbId = (int) (long) db.insert("student", null, values);
                                    db.close(); // Closing database connection
                                    if(dbId != -1) {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                    "Entry successful", Toast.LENGTH_SHORT).show();
                                        tableStudent(studentname,rollno);
                                    }
                                    else
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Error ! try again", Toast.LENGTH_SHORT).show();
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

    public void tableStudent(String studentName,String rollNo){
        TableLayout tl = (TableLayout) rootview.findViewById(R.id.table_student);
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView textView1 = new TextView(getContext());
        textView1.setText(studentName);
        textView1.setMinWidth(50);
        textView1.setPadding(3,3,3,3);
        TextView textView2 = new TextView(getContext());
        textView2.setText(rollNo);
        textView2.setPadding(3,3,3,3);
        textView2.setGravity(right);
        tr.addView(textView1);
        tr.addView(textView2);
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

}
