package com.example.musicapplication.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class OnlineBillBoardBean {
    private String name;
    private String update;
    private int songNum;
    private String comment; //榜单介绍
    private Bitmap picList;
    private Bitmap picCover;//榜单封面
    private Bitmap picBg;//背景图片
    private ArrayList<OnlineMusicBean> music;
    public OnlineBillBoardBean(String name, String comment, String update, int songNum, Bitmap picCover) {
        this.name = name;
        this.comment = comment;
        this.update = update;
        this.songNum = songNum;
        this.picCover = picCover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Bitmap getPicList() {
        return picList;
    }

    public void setPicList(Bitmap picList) {
        this.picList = picList;
    }

    public Bitmap getPicCover() {
        return picCover;
    }

    public void setPicCover(Bitmap picCover) {
        this.picCover = picCover;
    }

    public Bitmap getPicBg() {
        return picBg;
    }

    public void setPicBg(Bitmap picBg) {
        this.picBg = picBg;
    }

    public ArrayList<OnlineMusicBean> getMusic() {
        return music;
    }

    public void setMusic(ArrayList<OnlineMusicBean> music) {
        this.music = music;
    }



}
