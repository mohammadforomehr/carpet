package com.example.carpet.Model;

public class News {

    private String title;
    private String caption;
    private String date;
    private String image;
    private int id;

    public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
