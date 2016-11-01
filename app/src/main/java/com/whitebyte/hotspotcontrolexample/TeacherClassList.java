package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassList extends Activity {

    private Spinner spinnerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_list);

        Intent intent = getIntent();
        String F_teacherUsername = intent.getStringExtra(MainActivity.F_teacherUsername);
        String F_teacherName = intent.getStringExtra(MainActivity.F_teacherName);
        String F_teacherClass = intent.getStringExtra(MainActivity.F_teacherClass);

        TextView listTextView = (TextView) findViewById(R.id.teacherDetail);
        listTextView.setText(F_teacherUsername + " \n " + F_teacherName +" \n ");

        Button goClass = (Button) findViewById(R.id.teacherSelectClassSubmit);

        spinnerClass = (Spinner) findViewById(R.id.classList);
        // Spinner Drop down elements
        List<String> list = new ArrayList<String>();
        list.add(F_teacherClass);
        list.add("Pta Ni ");
        list.add("Hje vi ni pta ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerClass.setAdapter(dataAdapter);

        final String selectedClass = onSpinItemSelect();

        goClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), selectedClass , Toast.LENGTH_SHORT ).show();
            }
        });

    }

//    public void addClassToSpinner(String F_teacherClass) {
//        spinnerClass = (Spinner) findViewById(R.id.classList);
//        List<String> list = new ArrayList<String>();
//        list.add(F_teacherClass);
//        list.add("Pta Ni ");
//        list.add("Hje vi ni pta ");
//
//        ArrayAdapter<String> classListAdapter = new ArrayAdapter<String>(this,  );
//        classListAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinnerClass.setAdapter(classListAdapter);
//    }
    public String onSpinItemSelect() {
        return spinnerClass.getSelectedItem().toString();
    }
}

















