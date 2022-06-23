package com.study.materialcalendarview;

public class ProVO {
    private int pro_img;
    private  String pro_name;
    private  String pro_phone;
    private  String pro_info;

    public ProVO(int pro_img, String pro_name, String pro_phone, String pro_info){
        this.pro_img = pro_img;
        this.pro_name = pro_name;
        this.pro_phone = pro_phone;
        this.pro_info = pro_info;
    }

    public int getPro_img() {
        return pro_img;
    }

    public void setPro_img(int pro_img) {
        this.pro_img = pro_img;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_phone() {
        return pro_phone;
    }

    public void setPro_phone(String pro_phone) {
        this.pro_phone = pro_phone;
    }

    public String getPro_info() {
        return pro_info;
    }

    public void setPro_info(String pro_info) {
        this.pro_info = pro_info;
    }

    @Override
    public String toString() {
        return "ProVO{" +
                "pro_img=" + pro_img +
                ", pro_name='" + pro_name + '\'' +
                ", pro_phone='" + pro_phone + '\'' +
                ", pro_info='" + pro_info + '\'' +
                '}';
    }
}
