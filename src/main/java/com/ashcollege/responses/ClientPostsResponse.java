package com.ashcollege.responses;

import com.ashcollege.entities.PostEntity;

import java.util.List;

public class ClientPostsResponse extends BasicResponse {
    private List<PostModel> posts;

    public ClientPostsResponse () {
    }

    public ClientPostsResponse(boolean success, Integer errorCode, List<PostEntity> posts) {
        super(success, errorCode);
        this.posts = posts.stream().map(PostModel::new).toList();
    }


    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }
}
