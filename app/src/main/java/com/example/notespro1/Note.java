package com.example.notespro1;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Note {


    String title;
    String content;
    Timestamp timestamp;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public Note(String title, String content) {
        this.title = title;
        this.content = content;

    }


}
