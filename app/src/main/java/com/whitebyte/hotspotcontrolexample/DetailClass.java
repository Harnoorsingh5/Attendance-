package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.math.MathContext;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailClass extends Activity {

    public static int length;
    public final static String EXTRA_MESSAGE2 = "com.whitebyte.hotspotcontrolexample2";
    public final static String EXTRA_MESSAGE2a = "com.whitebyte.hotspotcontrolexample2a";
    WifiDatabaseHandler db;
    String ADD_NEW_COLUMN;
    int i = 1;
    String columnName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);


        final Button listOfStudents = (Button) findViewById(R.id.list_students);
        final Button markAttendance = (Button) findViewById(R.id.mark_attendance);
        final Button newEntry = (Button) findViewById(R.id.new_entry);

        db = new WifiDatabaseHandler(this);

        Intent intent = getIntent();
        final String selectedClass = intent.getStringExtra(TeacherClassList.EXTRA_MESSAGE);

        listOfStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailClass.this, StudentList.class);
                intent.putExtra(EXTRA_MESSAGE2, selectedClass);
                startActivity(intent);
            }
        });

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudentTable(selectedClass);
                Log.v("COLUMN NAMES  ::", db.retrieveColumnNames(selectedClass));

                Intent intent = new Intent(DetailClass.this, MatchMAC.class);
                intent.putExtra(EXTRA_MESSAGE2, selectedClass);
                intent.putExtra(EXTRA_MESSAGE2a, columnName );
                startActivity(intent);
            }
        });

        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailClass.this, InsertStudentData.class);
                intent.putExtra(EXTRA_MESSAGE2, selectedClass);
                startActivity(intent);
            }
        });
    }


    /*

    WHILE MAKING FINAL TABLE CHANGE 2 TO 1

     */
    public void updateStudentTable(String S_TABLE_NAME) {
        String[] getLastColummName = db.retrieveColumnName(S_TABLE_NAME);
        length = getLastColummName.length;
        String subStringDate = getLastColummName[length - 1].substring(0, 15);
        if (getCurrentDate().equals(subStringDate)) {
            String counter = getLastColummName[length - 1].substring(16);

            i = Integer.parseInt(counter);
            i++;
            //Toast.makeText(getApplicationContext(), "naam ta badl laa ##" + i + "  " + subStringDate, Toast.LENGTH_SHORT).show();

        } else {
            Log.v("SUBSTRING : ", subStringDate);
        }
        columnName = getCurrentDate() + "_" + i;
        ADD_NEW_COLUMN = "ALTER TABLE " + S_TABLE_NAME + " ADD COLUMN " + columnName + " string;";
        db.addNewColumnInStudent(ADD_NEW_COLUMN);
    }
        public String getCurrentDate() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy_MM_dd");
            String strDate = mdformat.format(calendar.getTime());
            strDate = "Date_"+ strDate;
            return strDate;
        }

}





















