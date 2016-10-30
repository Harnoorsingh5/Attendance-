package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Teacher;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class MainActivity extends Activity {

    EditText teacherUsername;
    EditText teacherPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherUsername = (EditText) findViewById(R.id.teacherUsername);
        teacherPassword = (EditText) findViewById(R.id.teacherPassword);

        final String teacherusername = teacherUsername.toString();
        final String teacherpassword = teacherPassword.toString();
        final WifiDatabaseHandler db = new WifiDatabaseHandler(this);

        final List<Teacher> teachers = db.getAllTeacherRecord();

        final Button loginButton = (Button) findViewById(R.id.teacherLogin);

//        for(Teacher teacher : teachers) {
//            String log = "T_UID: "+teacher.getT_uid()+" ,Name: " + teacher.getT_name() + " ,Class: " + teacher.getT_classteach()+", Password: "+teacher.getT_password();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Teacher teacher : teachers) {

                    if (teacher.getT_uid().equals(teacherusername)) {
                        //if (teacher.getT_password() == teacherpassword) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Yahooooo", Toast.LENGTH_LONG);
                        toast.show();
                        //}
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Gal Ni Bani", Toast.LENGTH_LONG);
                        toast.show();
                    }


//                if(db.getTeacherRecord(teacherusername, teacherpassword) ) {
//                    Toast toast = Toast.makeText(getApplicationContext(),"Yahooooo",Toast.LENGTH_LONG);
//                    toast.show();
//                }
                }
            }
        });
    }
}

