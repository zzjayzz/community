package com.yanzhao.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private long parent_id;
    private String content;
    private Integer type;
}
