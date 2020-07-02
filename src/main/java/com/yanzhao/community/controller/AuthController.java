package com.yanzhao.community.controller;

import com.yanzhao.community.dto.AccessTokenDTO;
import com.yanzhao.community.dto.GithubUser;
import com.yanzhao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @GetMapping("/callback")
        public String callback(@RequestParam(name = "code") String code,
                @RequestParam(name = "state") String state){
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(clientsecret);
            accessTokenDTO.setCode(code);
            System.out.println(code);
            accessTokenDTO.setRedirect_uri(redirecturi);
            accessTokenDTO.setState(state);
            String accessToken= githubProvider.getAccessToken(accessTokenDTO);
            GithubUser user= githubProvider.getuser(accessToken);
            System.out.println(user.getName());
            return "index";
        }
    }

