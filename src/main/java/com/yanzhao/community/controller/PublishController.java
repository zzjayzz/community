package com.yanzhao.community.controller;

import com.yanzhao.community.Mapper.QuestionMapper;
import com.yanzhao.community.Mapper.UserMapper;
import com.yanzhao.community.model.Question;
import com.yanzhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired UserMapper userMapper;

    @GetMapping("/publish")
    public  String publish(){
    return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model)
    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title==null || title==""){
            model.addAttribute("error","Please input Title");
            return "publish";
        }

        if (description==null|| description =="")
        {
            model.addAttribute("error","Please input Description");
            return "publish";
        }

        if(tag==null || tag=="")
        {
            model.addAttribute("error","Please input Tag");
            return "publish";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        User user = null;
        Cookie[] cookies=request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null){
            model.addAttribute("error","Please login first");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(user.getGmt_create());
        question.setGmt_modified(user.getGmt_modified());
        questionMapper.create(question);
        return "redirect:/";
    }
}
