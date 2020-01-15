package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassList extends Activity implements AdapterView.OnItemSelectedListener {

    public final static String EXTRA_MESSAGE = "com.whitebyte.hotspotcontrolexample";
    private Spinner spinnerClass;
    public String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_list);

        Intent intent = getIntent();
        String F_teacherUsername = intent.getStringExtra(MainActivity.F_teacherUsername);
        String F_teacherName = intent.getStringExtra(MainActivity.F_teacherName);
        String F_teacherClass = intent.getStringExtra(MainActivity.F_teacherClass);

        TextView listTextView = (TextView) findViewById(R.id.teacherDetail);
        listTextView.setText(F_teacherUsername + " \n " + F_teacherName + " \n ");

        spinnerClass = (Spinner) findViewById(R.id.classList);
        // Spinner click listener
        spinnerClass.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        String [] splitted = F_teacherClass.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i<splitted.length; i++) {
            list.add(splitted[i]);
        }
//
//        list.add(F_teacherClass);
//        list.add("Pta Ni ");
//        list.add("Hje vi ni pta ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerClass.setAdapter(dataAdapter);


        Button goClass = (Button) findViewById(R.id.teacherSelectClassSubmit);
        goClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//String[] splitted = line.split(" +");
//                startActivity(intent);

                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TeacherClassList.this, DetailClass.class);
                intent.putExtra(EXTRA_MESSAGE ,item);
                startActivity(intent);
            }
        });
    }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
              item = parent.getItemAtPosition(position).toString();
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }


}

















