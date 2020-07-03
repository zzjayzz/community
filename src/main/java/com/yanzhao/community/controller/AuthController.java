package com.yanzhao.community.controller;

import com.yanzhao.community.Mapper.UserMapper;
import com.yanzhao.community.dto.AccessTokenDTO;
import com.yanzhao.community.dto.GithubUser;
import com.yanzhao.community.model.User;
import com.yanzhao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @GetMapping("/callback")
        public String callback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state,
                               HttpServletRequest request)
            {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientsecret);
            accessTokenDTO.setCode(code);
            accessTokenDTO.setRedirect_uri(redirecturi);
            accessTokenDTO.setState(state);
            String accessToken= githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser= githubProvider.getuser(accessToken);
            if (githubUser!=null)
            { //login success
                User user=new User();
                user.setToken(UUID.randomUUID().toString());
                user.setName(githubUser.getName());
                user.setAccount_id(String.valueOf(githubUser.getId()));
                user.setGmt_create(System.currentTimeMillis());
                user.setGmt_modified(user.getGmt_create());
                userMapper.insert(user);
                request.getSession().setAttribute("user",githubUser);//if not assign cookies, will automatically give you
                return "redirect:/";


            }else {
                //login fail
                return "redirect:/";
            }
        }
    }
