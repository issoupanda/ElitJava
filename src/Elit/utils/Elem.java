/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.utils;

/**
 *
 * @author megapc
 */
public class Elem {

    String temp;
    String desc;
    String image;
    String date;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Elem{" + "temp=" + temp + ", desc=" + desc + ", image=" + image + ", date=" + date + '}';
    }

    public Elem(String temp, String desc, String image, String date) {
        this.temp = temp;
        this.desc = desc;
        this.image = image;
        this.date = date;
    }

}
