package com.whitebyte.wifihotspotutils;

/**
 * Created by Jassi Singh on 10/29/2016.
 */

public class Teacher {
    //private variables
    String t_uid;
    String t_name;
    String t_classteach;
    String t_password;

    // Empty constructor
    public Teacher() {
    }
    // constructor
    public Teacher(String t_uid, String t_name, String t_classteach ,String t_password ) {
        this.t_uid = t_uid;
        this.t_name = t_name;
        this.t_classteach = t_classteach;
        this.t_password = t_password;
    }
    public Teacher(String t_uid, String t_name) {
        this.t_uid = t_uid;
        this.t_name = t_name;
    }

    public Teacher(String t_uid) {
        this.t_uid = t_uid;
    }
    // getting MAC
    public String getT_uid(){
        return this.t_uid;
    }
    // setting MAC
    public void setT_uid(String t_uid){
        this.t_uid = t_uid;
    }

    // getting UID
    public String getT_name(){
        return this.t_name;
    }

    // setting UID
    public void setT_name(String t_name){
        this.t_name = t_name;
    }

    public String getT_classteach(){
        return this.t_classteach;
    }
    public void setT_classteach(String t_classteach){
        this.t_classteach =t_classteach;
    }
    public String getT_password(){
        return  this.t_password;
    }
    public void setT_password(String t_password){
        this.t_password = t_password;
    }
}
