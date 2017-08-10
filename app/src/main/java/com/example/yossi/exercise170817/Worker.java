package com.example.yossi.exercise170817;

/**
 * Created by Yossi on 07/08/2017.
 */

public class Worker {

    private int id;
    private String name;
    private String uname;
    private String pass;
    private String intime;
    private String outtime;
    private String applocation;
    private String userlocation;

    public Worker() {

    }



    public Worker(int id, String name, String intime,
                       String outtime, String applocation, String userlocation) {

        this.id = id;
        this.name = name;
        this.intime = intime;
        this.outtime = outtime;
        this.applocation = applocation;
        this.userlocation = userlocation;
    }

    public Worker(String name, String uname, String pass, String intime,
                  String outtime, String applocation, String userlocation) {

        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.intime = intime;
        this.outtime = outtime;
        this.applocation = applocation;
        this.userlocation = userlocation;
    }

    public Worker(String name, String intime, String outtime, String applocation,
                  String userlocation) {

        this.name = name;
        this.intime = intime;
        this.outtime = outtime;
        this.applocation = applocation;
        this.userlocation = userlocation;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getApplocation() {
        return applocation;
    }

    public void setApplocation(String applocation) {
        this.applocation = applocation;
    }

    public String getUserlocation() {
        return userlocation;
    }

    public void setUserlocation(String userlocation) {
        this.userlocation = userlocation;
    }


}
