package com.whitebyte.hotspotcontrolexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class StudentList extends ActionBarActivity {

    public String studentclass;
    public WifiDatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Intent intent = getIntent();
        studentclass = intent.getStringExtra(DetailClass.EXTRA_MESSAGE2);

        db= new WifiDatabaseHandler(this);
        List<Student> students = db.getAllStudentRecord(studentclass);

        for(Student sd : students) {
            String log = "Mac: "+sd.getMAC()+ " ,Name: "+ sd.getName() + " ,UID: " + sd.getUID() +",Class: "+sd.getStudentClass()+" ,Atten: " + sd.getAttendance();
            Log.d("Name: ", log);
        }
    }
}
