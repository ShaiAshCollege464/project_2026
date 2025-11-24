package com.ashcollege.responses;

import com.ashcollege.entities.PostEntity;

public class PostModel {
    private String text;
    private String area;

    public PostModel () {
    }

    public PostModel (PostEntity postEntity) {
        this.text = postEntity.getText();
        this.area = postEntity.getArea();
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
}
