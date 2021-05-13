package com.william.scoreseeker.model;

public class Pertandingan {
    private String id;
    private String timKandang;
    private String timTandang;
    private String skorKandang;
    private String skorTandang;
    private String tanggal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTimKandang() {
        return timKandang;
    }

    public void setTimKandang(String timKandang) {
        this.timKandang = timKandang;
    }

    public String getTimTandang() {
        return timTandang;
    }

    public void setTimTandang(String timTandang) {
        this.timTandang = timTandang;
    }

    public String getSkorKandang() {
        return skorKandang;
    }

    public void setSkorKandang(String skorKandang) {
        this.skorKandang = skorKandang;
    }

    public String getSkorTandang() {
        return skorTandang;
    }

    public void setSkorTandang(String skorTandang) {
        this.skorTandang = skorTandang;
    }
}
