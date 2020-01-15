package com.whitebyte.wifihotspotutils;

/**
 * Created by Jassi Singh on 10/29/2016.
 */

public class Student {
    //private variables
    String mac;
    String uid;
    String attendance = "a";
    String studentClass;
    String name;

    // Empty constructor
    public Student() {
    }
    // constructor
    public Student(String mac, String name, String uid, String studentClass, String attendance ) {
        this.mac = mac;
        this.name = name;
        this.uid = uid;
        this.studentClass = studentClass;
        this.attendance = attendance;
    }
    public Student(String mac, String name, String uid, String studentClass ) {
        this.mac = mac;
        this.name = name;
        this.uid = uid;
        this.studentClass = studentClass;
        this.attendance = "a";
    }
    public Student(String mac, String uid ) {
        this.mac = mac;
        this.uid = uid;
        this.attendance = "a";
    }

    public Student(String mac) {
        this.mac = mac;
    }
    // getting MAC
    public String getMAC(){
        return this.mac;
    }
    // setting MAC
    public void setMAC(String mac){
        this.mac = mac;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getting UID
    public String getUID(){
        return this.uid;
    }

    // setting UID
    public void setUID(String uid){
        this.uid = uid;
    }

    //getting Class
    public String getStudentClass() {
        return this.studentClass;
    }

    //setting Class
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getAttendance(){
        return this.attendance;
    }
    public void setAttendance(String attendance){
        this.attendance =attendance;
    }
}
