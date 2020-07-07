package com.yanzhao.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long Id;
    private String bio;
    private String avatar_url;

}
