package com.samuelagbede.sammybobo.iwe;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Agbede on 27/08/2016.
 */

public class Notes implements Serializable {
    private int id;
    private String date;
    private String note;
    private String tag;
    private static final long serialVersionUID = -7060210544600464481L;

    public Notes(){

    }

    public Notes(int id, String date, String note, String tag) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.tag = tag;
    }
    public Notes(String date, String note, String tag) {
        this.date = date;
        this.note = note;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



}
