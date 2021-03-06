package com.yanzhao.community.controller;

import com.yanzhao.community.mapper.UserMapper;
import com.yanzhao.community.dto.AccessTokenDTO;
import com.yanzhao.community.dto.GithubUser;
import com.yanzhao.community.model.User;
import com.yanzhao.community.provider.GithubProvider;
import com.yanzhao.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @GetMapping("/callback")
        public String callback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state,
                               HttpServletRequest request,
                               HttpServletResponse response)
            {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientsecret);
            accessTokenDTO.setCode(code);
            accessTokenDTO.setRedirect_uri(redirecturi);
            accessTokenDTO.setState(state);
            String accessToken= githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser= githubProvider.getuser(accessToken);
            if (githubUser!=null && githubUser.getId()!= null)
            { //login success
                User user=new User();
                String token=UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccount_id(String.valueOf(githubUser.getId()));
                user.setAvatar_url(githubUser.getAvatar_url());
                userService.createOrupdate(user);

                response.addCookie(new Cookie("token",token));

//                request.getSession().setAttribute("user",githubUser);//if not assign cookies, will automatically give you
                return "redirect:/";
            }else {
                //login fail
                return "redirect:/";
            }
        }

        @GetMapping("/logout")
        public String logout(
                HttpServletRequest request,
                HttpServletResponse response
        ){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
        }
    }

