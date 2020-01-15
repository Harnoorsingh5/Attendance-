package com.whitebyte.wifihotspotutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whitebyte.hotspotcontrolexample.DetailClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jassi Singh on 10/29/2016.
 */

public class WifiDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    public static String present;

    // Database Name
    private static final String DATABASE_NAME = "StudentRecordDB";

    // Student table name
    private static final String S_TABLE_NAME1 = "CSE1";
    private static final String S_TABLE_NAME2 = "CSE2";
    private static final String S_TABLE_NAME3 = "CSE3";
    private static final String S_TABLE_NAME4 = "CSE4";
    private static final String S_TABLE_NAME5 = "CSE5";
    private static final String S_TABLE_NAME6 = "CSE6";
    private static final String S_TABLE_NAME7 = "CSE7";
    private static final String S_TABLE_NAME8 = "CSE8";
    private static final String S_TABLE_NAME9 = "CSE9";



    // Studennt Table Columns names
    private static final String KEY_MAC = "mac_address";
    private static final String KEY_NAME = "student_name";
    private static final String KEY_UID = "uid";
    private static  final String KEY_CLASS = "class";
    private static final String KEY_ATTENDANCE = "Date_yyyy_MM_dd_0";


    //Teacher Table Name
    private static final String T_TABLE_NAME = "TeacherRecord";

    //Teacher Table Column Name
    private static final String KEY_T_UID = "t_uid";
    private static final String KEY_T_NAME = "teacher_name";
    private static final String KEY_T_CLASS_TEACH = "class_allotted ";
    private static  final String KEY_T_PASSWORD = "password";

    public WifiDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STUDENT_RECORD_TABLE_CSE1 = "CREATE TABLE " + S_TABLE_NAME1 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE2 = "CREATE TABLE " + S_TABLE_NAME2 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE3 = "CREATE TABLE " + S_TABLE_NAME3 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE4 = "CREATE TABLE " + S_TABLE_NAME4 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE5 = "CREATE TABLE " + S_TABLE_NAME5 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE6 = "CREATE TABLE " + S_TABLE_NAME6 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE7 = "CREATE TABLE " + S_TABLE_NAME7 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE8 = "CREATE TABLE " + S_TABLE_NAME8 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";

        String CREATE_STUDENT_RECORD_TABLE_CSE9 = "CREATE TABLE " + S_TABLE_NAME9 + "(" + KEY_MAC + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_UID + " TEXT," + KEY_CLASS +" TEXT," + KEY_ATTENDANCE + " TEXT );";
       // Log.v("teacher table","SOrry table not created");

        String CREATE_TEACHER_RECORD_TABLE = "CREATE TABLE " + T_TABLE_NAME + "(" + KEY_T_UID + " TEXT PRIMARY KEY,"
                + KEY_T_NAME + " TEXT," + KEY_T_CLASS_TEACH + " TEXT," + KEY_T_PASSWORD + " TEXT );";

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE1);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE2);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE3);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE4);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE5);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE6);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE7);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE8);

        db.execSQL(CREATE_STUDENT_RECORD_TABLE_CSE9);

        db.execSQL(CREATE_TEACHER_RECORD_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME1);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME2);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME3);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME4);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME5);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME6);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME7);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME8);
            db.execSQL("DROP TABLE IF EXISTS " + S_TABLE_NAME9);
            db.execSQL("DROP TABLE IF EXISTS " + T_TABLE_NAME);
            // Create tables again
            onCreate(db);
    }
    public void addNewColumnInStudent(String addColumnCommand) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(addColumnCommand);
    }

    //ADDING STUDENT RECORD
    public void addStudentRecord(Student student, String S_TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAC, student.getMAC()); // Student Mac
        values.put(KEY_NAME, student.getName());
        values.put(KEY_UID, student.getUID()); // Student UID
        values.put(KEY_CLASS, student.getStudentClass());
        values.put(KEY_ATTENDANCE, student.getAttendance()); //Student Attendance

        // Inserting Row
        db.insert(S_TABLE_NAME, null, values);
        db.close(); //Closing Database Connection
    }

    public void addTeacherRecord(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_T_UID, teacher.getT_uid()); // Student Name
        values.put(KEY_T_NAME, teacher.getT_name()); // Student UID
        values.put(KEY_T_CLASS_TEACH, teacher.getT_classteach()); //Student Attendance
        values.put(KEY_T_PASSWORD,teacher.getT_password());

        // Inserting Row
        db.insert(T_TABLE_NAME, null, values);
        db.close(); //Closing Database Connection
    }

   public Student getStudentRecord(String mac , String S_TABLE_NAME){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(S_TABLE_NAME, new String[]{KEY_MAC,KEY_NAME,KEY_UID,KEY_CLASS,KEY_ATTENDANCE},
                KEY_MAC +"=?",new String[]{String.valueOf(mac)},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return  student;
    }

    public List<Student> getAllStudentRecord(String S_TABLE_NAME) {
        List<Student> studentList = new ArrayList<Student>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + S_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setMAC(cursor.getString(0));
                student.setName(cursor.getString(1));
                student.setUID(cursor.getString(2));
                student.setStudentClass(cursor.getString(3));
                //if(present.equals("P")) {
                   // student.setAttendance(cursor.getString(DetailClass.columnNumber));

                student.setStudentClass((cursor.getString(4)));
                //Adding Student to list
                studentList.add(student);
            }while (cursor.moveToNext());
        }
        //return Student List
        return studentList;
    }

    public List<Student> getAllMacOfStudent(String S_TABLE_NAME) {
        List<Student> studentList = new ArrayList<Student>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + S_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setMAC(cursor.getString(0));

                //Adding Student to list
                studentList.add(student);
            }while (cursor.moveToNext());
        }
        //return Student List
        return studentList;
    }


    public Teacher getTeacherRecord(String t_uid ){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(T_TABLE_NAME, new String[]{KEY_T_UID,KEY_T_NAME,KEY_T_CLASS_TEACH,KEY_T_PASSWORD},
                KEY_T_UID +"=?",new String[]{String.valueOf(t_uid)},null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }

        Teacher teacher = new Teacher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return teacher;
    }

    public List<Teacher> getAllTeacherRecord() {
        List<Teacher> teacherList = new ArrayList<Teacher>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + T_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                Teacher teacher = new Teacher();
                teacher.setT_uid(cursor.getString(0));
                teacher.setT_name(cursor.getString(1));
                teacher.setT_classteach(cursor.getString(2));
                teacher.setT_password(cursor.getString(3));
                //Adding Student to list
                teacherList.add(teacher);
            }while (cursor.moveToNext());
        }
        //return Student List
        return teacherList;
    }

    // Deleting single contact
    public void sDeleteRecord(Student student, String S_TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(S_TABLE_NAME, KEY_MAC + " = ?", new String[] { String.valueOf(student.getMAC()) });
        db.close();
    }

    public void tDeleteRecord(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(T_TABLE_NAME, KEY_MAC + " = ?", new String[] { String.valueOf(teacher.getT_uid()) });
        db.close();
    }

    public String retrieveColumnNames(String TABLE_NAME) {
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        StringBuilder builder = new StringBuilder();
        for(String s : columnNames) {
            builder.append(s);
        }
        return builder.toString();
    }
    public String[] retrieveColumnName(String TABLE_NAME) {
        SQLiteDatabase mDataBase;
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }

    public void markAttendance(String S_TABLE_NAME, String columnName, String mac) {
        SQLiteDatabase db = getWritableDatabase();
         present = "P";
//        ContentValues cv = new ContentValues();

//        cv.put(columnName, present);
//        db.update(S_TABLE_NAME, cv, KEY_MAC + "= ?", new String[] {mac});

        String sql = "UPDATE "+ S_TABLE_NAME +" SET " + columnName+ " = '"+present+"' WHERE "+KEY_MAC+ " = " + "'"+mac+"'";
        db.execSQL(sql);
    }
    public List<Student> tempCheckAtt(String S_TABLE_NAME, String mac) {
        SQLiteDatabase db = getWritableDatabase();
        List<Student> studentList = new ArrayList<Student>();
        String sql = "SELECT * FROM "+ S_TABLE_NAME  + " WHERE " + KEY_MAC + " = " + "'"+mac+"'";

        Cursor cursor = db.rawQuery(sql, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                  Student student = new Student();
                student.setMAC(cursor.getString(0));
                student.setName(cursor.getString(1));
                student.setUID(cursor.getString(2));
                student.setStudentClass(cursor.getString(3));
                student.setAttendance(cursor.getString(DetailClass.length));

                //Adding Student to list
                studentList.add(student);
            }while (cursor.moveToNext());
        }
        //return Student List
        return studentList;
    }
}























