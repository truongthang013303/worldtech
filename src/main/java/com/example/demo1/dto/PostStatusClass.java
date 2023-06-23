package com.example.demo1.dto;

public class PostStatusClass {
    private Integer postStatusCode;
    private String postStatusName;

    public PostStatusClass(Integer postStatusCode) {
        this.postStatusCode = postStatusCode;
        switch (postStatusCode)
        {
            case 0: {
                this.postStatusName=new String("PostStatusClass-Chờ duyệt");
                break;
            }
            case 1: {
                this.postStatusName=new String("PostStatusClass-Đã duyệt");
                break;
            }
            case 2: {
                this.postStatusName=new String("PostStatusClass-Duyệt thất bại");
                break;
            }
            default: {
                this.postStatusName=new String("PostStatusClass-DEFAULT");
                break;
            }
        }
    }

    public Integer getPostStatusCode() {
        return postStatusCode;
    }

    public String getPostStatusName() {
        return postStatusName;
    }
}
