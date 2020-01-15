package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class StudentList extends Activity {

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

        TableRow.LayoutParams params1=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableLayout tableLayout=(TableLayout) findViewById(R.id.tableLayout);

        TableRow rowColumnName = new TableRow(this);

        TextView macColumnName = new TextView(this);
        TextView uidColumnName = new TextView(this);
        TextView nameColumnName = new TextView(this);
        TextView attendanceColumnName = new TextView(this);

        macColumnName.setText("MAC ADDRESS "+ "  ");
        uidColumnName.setText("UID " + "  ");
        nameColumnName.setText("NAME " + "  ");
        attendanceColumnName.setText(DetailClass.columnName + "  ");

        macColumnName.setLayoutParams(params1);
        uidColumnName.setLayoutParams(params1);
        nameColumnName.setLayoutParams(params1);
        attendanceColumnName.setLayoutParams(params1);

        rowColumnName.addView(macColumnName);
        rowColumnName.addView(uidColumnName);
        rowColumnName.addView(nameColumnName);
        rowColumnName.addView(attendanceColumnName);

        rowColumnName.setLayoutParams(params2);
        tableLayout.addView(rowColumnName);


        for(Student sd : students) {
            String log = "Mac: "+sd.getMAC()+ " ,Name: "+ sd.getName() + " ,UID: " + sd.getUID() +",Class: "+sd.getStudentClass()+" ,Atten: " + sd.getAttendance();
            Log.d("Name: ", log);

            //Creating new tablerows and textviews
            TableRow row = new TableRow(this);

            TextView mac = new TextView(this);
            TextView uid = new TextView(this);
            TextView name = new TextView(this);
            TextView attendance = new TextView(this);

            //setting the text
            mac.setText(sd.getMAC() + "   ");
            uid.setText(sd.getUID() + "   ");
            name.setText(sd.getName() + "   ");
            attendance.setText(sd.getAttendance() + "   ");

            mac.setLayoutParams(params1);
            uid.setLayoutParams(params1);
            name.setLayoutParams(params1);
            attendance.setLayoutParams(params1);

            //the textviews have to be added to the row created
            row.addView(mac);
            row.addView(uid);
            row.addView(name);
            row.addView(attendance);
            Log.e("EEEEEE:", String.valueOf(attendance));
            row.setLayoutParams(params2);
            tableLayout.addView(row);

        }
    }
}
