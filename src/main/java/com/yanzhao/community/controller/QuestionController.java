package com.yanzhao.community.controller;


import com.yanzhao.community.dto.CommentDTO;
import com.yanzhao.community.dto.QuestionDTO;
import com.yanzhao.community.enums.CommentTypeEnum;
import com.yanzhao.community.service.CommentService;
import com.yanzhao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name="id") Long id,
            Model model)
    {
        QuestionDTO questionDTO= questionService.getById(id);
        //increment views
        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }


}
