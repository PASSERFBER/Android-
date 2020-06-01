package com.example.musicapplication.bean;

public class OnlineMusicBean {
    private String song;
    private String singer;
    private String duration;
    private String path;
    private long id;
    private String album;
    private long albumId;

    public OnlineMusicBean(String song, String singer, long songId, String album, long albumId) {
        this.song = song;
        this.singer=singer;
        this.id=songId;
        this.album=album;
        this.albumId=albumId;

    }
    public OnlineMusicBean() {

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

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }





}


