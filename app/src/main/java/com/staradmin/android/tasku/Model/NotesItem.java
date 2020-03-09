package com.staradmin.android.tasku.Model;

public class NotesItem {
    public NotesItem(String id, String title, String notes, String date) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.id = id;
    }

    private String title;
    private String notes;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
