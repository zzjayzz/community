package com.yanzhao.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmt_modified;
    private Long gmt_create;
    private Integer view_count;
    private Integer like_count;
    private Integer comment_count;

}
