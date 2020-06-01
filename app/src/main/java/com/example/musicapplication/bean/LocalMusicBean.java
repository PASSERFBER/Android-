package com.example.musicapplication.bean;

import android.graphics.Bitmap;

public class LocalMusicBean {

    private String song;
    private String singer;
    private String album;
    private String duration;
    private String path;
//    private String album_pic;
    private String strPath;
    private Bitmap bMap;




    public LocalMusicBean(String song, String singer, String album, String duration, String path) {
        this.song = song;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;

    }

    public LocalMusicBean() {

    }

    public Bitmap getbMap() {
        return bMap;
    }

    public void setbMap(Bitmap bMap) {
        this.bMap = bMap;
    }

    public LocalMusicBean(Bitmap bMap) {
        this.bMap = bMap;
    }




    public String getStrPath() {
        return strPath;
    }

    public void setStrPath(String strPath) {
        this.strPath = strPath;
    }
    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
    public String getSinger() {
        return singer;
    }

    public  void  setSinger(String singer){
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setTime(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
