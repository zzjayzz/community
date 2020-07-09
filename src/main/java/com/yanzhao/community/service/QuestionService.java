package com.yanzhao.community.service;

import com.yanzhao.community.mapper.QuestionMapper;
import com.yanzhao.community.mapper.UserMapper;
import com.yanzhao.community.dto.PageDTO;
import com.yanzhao.community.dto.QuestionDTO;
import com.yanzhao.community.model.Question;
import com.yanzhao.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
        Integer totalcount = questionMapper.count();
        if (totalcount % size == 0) {
            totalPage = totalcount / size;
        } else {
            totalPage = totalcount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageDTO.setPagination(totalPage, page);

        //size*(page-1)
        Integer offset=size*(page-1);
        List<Question> questions= questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();


        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return  pageDTO;
}

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
        Integer totalcount = questionMapper.countByUserId(userId);
        if (totalcount % size == 0) {
            totalPage = totalcount / size;
        } else {
            totalPage = totalcount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageDTO.setPagination(totalPage, page);
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrupdate(Question question) {
        if (question.getId() ==null){
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_modified());
            questionMapper.create(question);
        }else{
            //update
            question.setGmt_modified(question.getGmt_modified());
            questionMapper.update(question);
        }
    }
}
