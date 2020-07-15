package com.yanzhao.community.controller;

import com.yanzhao.community.dto.CommentCreateDTO;
import com.yanzhao.community.dto.CommentDTO;
import com.yanzhao.community.dto.ResultDTO;
import com.yanzhao.community.enums.CommentTypeEnum;
import com.yanzhao.community.exception.CustomizeErrorCode;
import com.yanzhao.community.model.Comment;
import com.yanzhao.community.model.User;
import com.yanzhao.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method= RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                        HttpServletRequest request){

        User user=(User) request.getSession().getAttribute("user");
        if (user == null){
           return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDTO==null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLike_count(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method= RequestMethod.GET)
    public ResultDTO <List> comments(
         @PathVariable(name="id") Long id)

    {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);

    }
}
