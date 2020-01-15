package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Teacher;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class TeacherSignUp extends Activity {

    private EditText teacherName;
    private EditText teacherUID;
    private EditText teacherPassword;

    private String teacher_name;
    private String teacher_uid;
    private String teacher_password;
    private String teacher_class;
    CheckBox cse1,cse2,cse3,cse4,cse5,cse6,cse7,cse8,cse9;
    Button submit;
    StringBuffer totalClassesChecked;
    List<Teacher> teachers;

    WifiDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up);

        db = new WifiDatabaseHandler(this);

        teacherName = (EditText) findViewById(R.id.teacher_name);
        teacherUID = (EditText) findViewById(R.id.teacher_uid);
        teacherPassword = (EditText) findViewById(R.id.teacher_password);

        cse1 = (CheckBox) findViewById(R.id.cse1);
        cse2 = (CheckBox) findViewById(R.id.cse2);
        cse3 = (CheckBox) findViewById(R.id.cse3);
        cse4 = (CheckBox) findViewById(R.id.cse4);
        cse5 = (CheckBox) findViewById(R.id.cse5);
        cse6 = (CheckBox) findViewById(R.id.cse6);
        cse7 = (CheckBox) findViewById(R.id.cse7);
        cse8 = (CheckBox) findViewById(R.id.cse8);
        cse9 = (CheckBox) findViewById(R.id.cse9);

        submit = (Button) findViewById(R.id.submit_teacher_data);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalClassesChecked = new StringBuffer();

                teacher_name = teacherName.getText().toString();
                teacher_uid = teacherUID.getText().toString();
                teacher_password = teacherPassword.getText().toString();

                if(cse1.isChecked()) {
                    totalClassesChecked.append(cse1.getText().toString()).append(",");
                }
                if(cse2.isChecked()) {
                    totalClassesChecked.append(cse2.getText().toString()).append(",");
                }
                if(cse3.isChecked()) {
                    totalClassesChecked.append(cse3.getText().toString()).append(",");
                }
                if(cse4.isChecked()) {
                    totalClassesChecked.append(cse4.getText().toString()).append(",");
                }
                if(cse5.isChecked()) {
                    totalClassesChecked.append(cse5.getText().toString()).append(",");
                }
                if(cse6.isChecked()) {
                    totalClassesChecked.append(cse6.getText().toString()).append(",");
                }
                if(cse7.isChecked()) {
                    totalClassesChecked.append(cse7.getText().toString()).append(",");
                }
                if(cse8.isChecked()) {
                    totalClassesChecked.append(cse8.getText().toString()).append(",");
                }
                if(cse9.isChecked()) {
                    totalClassesChecked.append(cse9.getText().toString()).append(",");
                }

                String totalclasseschecked = totalClassesChecked.toString();
                if( teacher_name.trim().equals("")) {
                    teacherName.setError("Name is required!");
                }
                if( teacher_uid.trim().equals("")) {
                    teacherUID.setError("UID is required!");
                }
                if( teacher_password.trim().equals("")) {
                    teacherPassword.setError("Password is required!");
                }
                if( totalclasseschecked.trim().equals("")) {
                    cse9.setError( "Select Atleast one class" );
                }
                if( !totalclasseschecked.trim().equals("")) {
                    cse9.setError(null);
                }

                if(teacherName.getError()==null && teacherUID.getError()==null && teacherPassword.getError()==null && !totalclasseschecked.trim().equals("") ){

                    teachers = db.getAllTeacherRecord();
                    for (Teacher teacher : teachers) {
                        if(teacher.getT_uid().equals(teacher_uid)) {
                            teacherUID.setError("Username Already Exists");
                        }
                    }
                    if(teacherUID.getError()==null) {
                        totalclasseschecked = totalclasseschecked.substring(0, totalclasseschecked.length() - 1);
                        db.addTeacherRecord(new Teacher(teacher_uid, teacher_name, totalclasseschecked , teacher_password));
                    }
                    for (Teacher teacher : teachers) {
                        String log = "T_UID: " + teacher.getT_uid() + " ,Name: " + teacher.getT_name() + " ,Class: " + teacher.getT_classteach() + ", Password: " + teacher.getT_password();
                        // Writing Contacts to log
                        Log.d("TEACHERE RECOR##### ", log);
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), "Registered Successfully!!!", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(TeacherSignUp.this,MainActivity.class);
                    startActivity(intent);

                }

//                Toast toast = Toast.makeText(getApplicationContext(), "Registered Successfully!!!", Toast.LENGTH_LONG);
//                toast.show();
//                Intent intent = new Intent(TeacherSignUp.this,MainActivity.class);
//                startActivity(intent);

//                Log.v("#########CLASSES  :", totalclasseschecked);
            }
        });

    }
}