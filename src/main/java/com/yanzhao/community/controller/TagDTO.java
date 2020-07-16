package com.yanzhao.community.controller;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {

    private String categoryName;
    private List<String> tags;
}
