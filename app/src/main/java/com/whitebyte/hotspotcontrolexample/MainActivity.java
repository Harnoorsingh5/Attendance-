package com.whitebyte.hotspotcontrolexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.Teacher;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.List;

public class MainActivity extends Activity {

    public final static String F_teacherUsername = "Teacher Username ";
    public final static String F_teacherName ="Teacher Name ";
    public final static String F_teacherClass ="Teacher Class ";

    EditText teacherUsername;
    EditText teacherPassword;
    String teacherusername;
    String teacherpassword;
    WifiDatabaseHandler db;
    List<Teacher> teachers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherUsername = (EditText) findViewById(R.id.teacherUsername);
        teacherPassword = (EditText) findViewById(R.id.teacherPassword);

        db = new WifiDatabaseHandler(this);

//        db.addStudentRecord(new Student("34:fc:ef:c5:c5:26","Harry","14BCS1530","CSE-5","P"),"StudentRecord");
//        db.addStudentRecord(new Student("70:77:81:8E:E3:DF","Jassi","14BCS1538","CSE-4","A"),"StudentRecord");
//        db.addStudentRecord(new Student("24:fc:ef:c5:c5:27","Joshi","14BCS1528","CSE-6","P"),"StudentRecord");


//		 Inserting Teacher Records
//        Log.d("Insert: ", "Inserting ..");
//        db.addTeacherRecord(new Teacher("E2231","abc","CSE6,CSE3","qwertyuiop"));
//        db.addTeacherRecord(new Teacher("E2341","def","CSE5","asdfghjkl"));
//        db.addTeacherRecord(new Teacher("E2342","ghi","CSE4","zxcvbnm"));

        teachers = db.getAllTeacherRecord();

        final Button loginButton = (Button) findViewById(R.id.teacherLogin);
        final Button signupButton = (Button) findViewById(R.id.teacherSignup);

//        for (Teacher teacher : teachers) {
//            String log = "T_UID: " + teacher.getT_uid() + " ,Name: " + teacher.getT_name() + " ,Class: " + teacher.getT_classteach() + ", Password: " + teacher.getT_password();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                teacherusername = teacherUsername.getText().toString();
                teacherpassword = teacherPassword.getText().toString();

                if (matchValue(teacherusername, teacherpassword)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "SUCCESSFULLY LOGIN!!!", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(MainActivity.this, TeacherClassList.class);
                    intent.putExtra(F_teacherUsername,teacherusername );
                    intent.putExtra(F_teacherName, teacherName);
                    intent.putExtra(F_teacherClass, teacherClass);

                    startActivity(intent);


                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "INCORRECT PASSWORD!!", Toast.LENGTH_LONG);
                    toast.show();


                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TeacherSignUp.class);
                startActivity(intent);
            }
        });
    }

    public static String teacherName;
    public static String teacherClass;
    public boolean matchValue(String teacherusername, String teacherpassword) {

        for (Teacher teacher : teachers) {
            if (teacher.getT_uid().equals(teacherusername) && teacher.getT_password().equals(teacherpassword)) {
                teacherName = teacher.getT_name();
                teacherClass = teacher.getT_classteach();
                return true;
            }
        }
        return false;
    }
}
