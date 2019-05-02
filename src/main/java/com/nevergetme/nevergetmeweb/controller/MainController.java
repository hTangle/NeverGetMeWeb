package com.nevergetme.nevergetmeweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public String getMainPage(HttpServletResponse response,
                              Model model,
                              HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
//        PageHelper.startPage(1,5);
//
//        List<Article> articles=articleService.getArticleList();
//        PageInfo<Article> p=new PageInfo<>(articles);
//        if(articles.size()!=0){
//            model.addAttribute("articles",articles);
//            model.addAttribute("pageInfo",p);
//        }
        return "index";
    }

    @GetMapping("/article/showArticle/{articleId}")
    public String showArticle(@PathVariable("articleId") int articleId,
                              HttpServletResponse response,
                              Model model,
                              HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //articleService.getArticleById()
//        Article article = articleService.getArticleById(articleId);
//        if (article != null && article.getTitle() != null) {
//            model.addAttribute("article", article);
//            articleService.updateVisitTimes(articleId);
//        }
        if(articleId>0){
            model.addAttribute("articleID",articleId);
            response.setHeader("articleId", articleId + "");
            return "showArticle";
        }
        return "404";
    }

    @GetMapping("/editArticle")
    public String editArticle(HttpServletRequest request, HttpServletResponse response, Model model) {
//        User user= ContentUtility.getUser();
        return "editArticle.html";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    @GetMapping("/404")
    public String get404(){
        return "404";
    }
}
