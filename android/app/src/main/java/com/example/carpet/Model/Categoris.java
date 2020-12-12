package com.example.carpet.Model;

public class Categoris {

    private String title;
    private String image;
    private int id;
    private int parent_id;

    public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
