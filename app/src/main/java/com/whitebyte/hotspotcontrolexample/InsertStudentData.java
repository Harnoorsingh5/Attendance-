
package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class InsertStudentData extends Activity {

    EditText studentMac;
    EditText studentName;
    EditText studentUid;
    TextView studentClass;
    WifiDatabaseHandler db;
    public String studentmac;
    public String studentname;
    public String studentuid;
    public String studentclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student_data);

        db = new WifiDatabaseHandler(this);

        Intent intent = getIntent();
        studentclass = intent.getStringExtra(DetailClass.EXTRA_MESSAGE2);

        studentMac = (EditText) findViewById(R.id.student_mac_address);
        studentName = (EditText) findViewById(R.id.student_name);
        studentUid = (EditText) findViewById(R.id.student_uid);

        studentClass = (TextView) findViewById(R.id.student_class);
        studentClass.setText(studentclass);

        final Button studentSubmitData = (Button) findViewById(R.id.student_submit_data);

        studentSubmitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                db.addStudentRecord(new Student(studentmac,studentname,studentuid,studentclass),studentclass);

                studentmac = studentMac.getText().toString();
                studentname = studentName.getText().toString();
                studentuid = studentUid.getText().toString();
                studentclass = studentClass.getText().toString();

                if( studentmac.trim().equals("")) {
                    studentMac.setError("MAC is required!");
                }
                if( studentname.trim().equals("")) {
                    studentName.setError("UID is required!");
                }
                if( studentuid.trim().equals("")) {
                    studentUid.setError("Name is required!");
                }

                if(studentMac.getError()==null && studentName.getError()==null && studentUid.getError()==null ) {
                    List<Student> students = db.getAllStudentRecord(studentclass);
                    for (Student sd : students) {
                        String log = "Mac: " + sd.getMAC() + " ,Name: " + sd.getName() + " ,UID: " + sd.getUID() + ",Class: " + sd.getStudentClass() + " ,Atten: " + sd.getAttendance();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                        Toast.makeText(InsertStudentData.this, "Data has been inserted!!! Now you can add more entries", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    });
}
    public void getData() {
        studentmac = studentMac.getText().toString();
        studentname = studentName.getText().toString();
        studentuid = studentUid.getText().toString();
        studentclass = studentClass.getText().toString();
    }
}
