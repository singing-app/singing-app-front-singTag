package com.example.tag;

public class FriendItem {
    String txt_num;
    String text_title_insert;
    String text_singer_insert;
    String text_pitch_insert;
    String message;
    int resourceId;

    public FriendItem(int resourceId, String txt_num, String text_title_insert, String text_singer_insert, String text_pitch_insert) {
        this.txt_num = txt_num;
        this.text_title_insert= text_title_insert;
        this.text_singer_insert = text_singer_insert;
        this.text_pitch_insert = text_pitch_insert;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTxt_num() {
        return txt_num;
    }

    public String getText_title_insert() {
        return text_title_insert;
    }

    public String getText_singer_insert() {
        return text_singer_insert;
    }

    public String getText_pitch_insert() {
        return text_pitch_insert;
    }

    public String setText_title_insert() {
        return text_title_insert;
    }

    public String setText_singer_insert() {
        return text_singer_insert;
    }

    public String setText_pitch_insert() {
        return text_pitch_insert;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
