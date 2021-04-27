package com.hamta.mydivar.Model;

import java.util.List;

public class ListItem {
    private String title;
    private String desc;
    private String url;
    private String city;
    private String date;
    private String price;
    private String latitude;
    private String longitude;
    private List<String> webImagesUrlList;

    public ListItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public List<String> getWebImagesUrlList() {
        return webImagesUrlList;
    }

    public void setWebImagesUrlList(List<String> webImagesUrlList) {
        this.webImagesUrlList = webImagesUrlList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}