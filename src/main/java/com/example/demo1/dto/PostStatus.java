package com.example.demo1.dto;

import org.apache.commons.lang3.EnumUtils;

public enum PostStatus {
    CHODUYET(0,"Chờ duyệt"),
    DADUYET(1,"Đã được duyệt"),
    TUCHOI(2, "Từ chối duyệt-Duyệt thất bại"),
    DEFAULT(3, "DEFAULT-Description");

    private Integer postStatusCode;
    private String postStatusName;

    PostStatus(Integer postStatusCode, String postStatusName) {
        this.postStatusCode = postStatusCode;
        this.postStatusName = postStatusName;
    }

    public Integer getPostStatusCode() {
        return postStatusCode;
    }

    public void setPostStatusCode(Integer postStatusCode) {
        this.postStatusCode = postStatusCode;
    }

    public String getPostStatusName() {
        return postStatusName;
    }

    public void setPostStatusName(String postStatusName) {
        this.postStatusName = postStatusName;
    }

    public static PostStatus getPostStatusEnumByInteger(Integer intStatus)
    {
        if(intStatus!=null) {
            switch (intStatus) {
                case 0: {
                    return PostStatus.CHODUYET;
                    //break;
                }
                case 1: {
                    return PostStatus.DADUYET;
                    //break;
                }
                case 2: {
                    return PostStatus.TUCHOI;
                    //break;
                }
                default: {
                    return PostStatus.DEFAULT;
                    //break;
                }
            }
        }
        return PostStatus.DEFAULT;
    }

    public static PostStatus getById(Long id) {
        if(id!=null){
            for(PostStatus e : values()) {
                if(e.postStatusCode.equals(id)) return e;
            }
        }
        return DEFAULT;
    }
}
