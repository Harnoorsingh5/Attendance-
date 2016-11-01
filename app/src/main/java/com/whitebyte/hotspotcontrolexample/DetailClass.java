package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Student;

public class DetailClass extends Activity {

    public final static String EXTRA_MESSAGE2 = "com.whitebyte.hotspotcontrolexample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);


        final Button listOfStudents = (Button) findViewById(R.id.list_students);
        final Button markAttendance = (Button) findViewById(R.id.mark_attendance);
        final  Button newEntry = (Button) findViewById(R.id.new_entry);

        Intent intent = getIntent();
        final String selectedClass = intent.getStringExtra(TeacherClassList.EXTRA_MESSAGE);

        listOfStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailClass.this,StudentList.class);
                intent.putExtra(EXTRA_MESSAGE2 ,selectedClass);
                startActivity(intent);
            }
        });

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailClass.this,InsertStudentData.class);
                intent.putExtra(EXTRA_MESSAGE2 ,selectedClass);
                startActivity(intent);
        }
    });

}
}
