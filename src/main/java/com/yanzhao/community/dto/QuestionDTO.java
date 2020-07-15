package com.yanzhao.community.dto;

import com.yanzhao.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long creator;
    private Long gmt_modified;
    private Long gmt_create;
    private Integer view_count;
    private Integer like_count;
    private Integer comment_count;
    private User user;
}
