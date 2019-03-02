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
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class ArticleContent {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/list")
    public @ResponseBody
    List<Article> getAllTheArticle(HttpServletRequest request){
        return articleService.findAllArticle();
    }
}
