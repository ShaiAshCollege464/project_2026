package com.ashcollege.responses;

import com.ashcollege.entities.PostEntity;

public class PostModel {
    private int id;
    private String text;
    private String area;
    private String fileLink;
    private String categoryName;

    public PostModel () {
    }

    public PostModel (PostEntity postEntity) {
        this.text = postEntity.getText();
        this.area = postEntity.getArea();
        this.fileLink = postEntity.getFileLink();
        this.id = postEntity.getId();
        if (postEntity.getCategoryEntity() != null) {
            this.categoryName = postEntity.getCategoryEntity().getName();
        }
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {return id;}

}
