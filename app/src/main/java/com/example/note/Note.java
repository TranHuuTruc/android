package com.example.note;

import java.util.Date;

public class Note {
   private long ID;
    private String tiltle;
    private String noidung;
    private String date;
    private String time;

    Note(){}
    Note(String tiltle, String noidung, String date, String time){
        this.tiltle = tiltle;
        this.noidung = noidung;
        this.date = date;
        this.time = time;

    }
    Note(long id, String tiltle, String noidung, String date, String time){
        this.ID = id;
        this.tiltle = tiltle;
        this.noidung = noidung;
        this.date = date;
        this.time = time;

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTiltle() {
        return tiltle;
    }

    public void setTiltle(String tiltle) {
        this.tiltle = tiltle;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
