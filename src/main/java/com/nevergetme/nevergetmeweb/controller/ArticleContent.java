package com.nevergetme.nevergetmeweb.controller;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class ArticleContent {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/list")
    public @ResponseBody
    Map<String,Object> getAllTheArticle(HttpServletRequest request){
        //HttpSession session = request.getSession(true);
        //if(session.getAttribute("userId") != null){
            //int userId=(Integer) session.getAttribute("userId");
            List<Article> articles=articleService.findUserArticle(103450);
            Map<String,Object> map=new HashMap<>();
            map.put("count",articles.size());
            map.put("state",1);
            map.put("data",articles);
            return map;
        //}
        //return new HashMap<>();
    }

    @RequestMapping(value = "/article/details")
    public @ResponseBody Map<String,Object> getArticleById(int id,HttpServletRequest request){
        Article articles=articleService.getArticleById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("state",1);
        map.put("data",articles);
        return map;
    }
}
