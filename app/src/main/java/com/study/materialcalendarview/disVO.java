package com.study.materialcalendarview;

public class disVO {
    private  int dis_img;
    private  String dis_name;
    private  String dis_data;
    private  String dis_date;

    public disVO(int dis_img, String dis_name, String dis_date){
        this.dis_img = dis_img;
        this.dis_name = dis_name;
        //this.dis_data = dis_data;
        this.dis_date = dis_date;
    }

    public int getDis_img() {return dis_img;}

    public void setDis_img(int img) {
        this.dis_img = dis_img;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getDis_data() {
        return dis_data;
    }

    public void setDis_data(String dis_data) {
        this.dis_data = dis_data;
    }

    public String getDis_date() {
        return dis_date;
    }

    public void setDis_date(String dis_date) {
        this.dis_date = dis_date;
    }

    @Override
    public String toString() {
        return "proVO{" +
                "img=" + dis_img +
                ", dis_name='" + dis_name + '\'' +
                // ", dis_data='" + dis_data + '\'' +
                ", dis_date='" + dis_date + '\'' +
                '}';
    }
}