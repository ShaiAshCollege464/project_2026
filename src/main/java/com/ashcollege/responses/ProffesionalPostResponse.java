package com.ashcollege.responses;

import com.ashcollege.entities.BidEntity;
import com.ashcollege.entities.PostEntity;

import java.util.List;

public class ProffesionalPostResponse extends BasicResponse {
    private PostModel post;

    public ProffesionalPostResponse() {
    }

    public ProffesionalPostResponse(boolean success, Integer errorCode, PostEntity postEntity) {
        super(success, errorCode);
        this.post = new PostModel(postEntity,null);
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }
}
