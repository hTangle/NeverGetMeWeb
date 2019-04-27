package com.nevergetme.nevergetmeweb.controller;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        List<Article> articles=articleService.getArticleList();
        if(articles.size()!=0){
            model.addAttribute("articles",articles);
        }
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
        Article article = articleService.getArticleById(articleId);
        if (article != null && article.getTitle() != null) {
            model.addAttribute("article", article);
        }
        response.setHeader("articleId", articleId + "");
        return "showArticle";
    }

    @GetMapping("/editArticle")
    public String editArticle(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (request.getSession(true).getAttribute("userid") == null) {
            return "login";
        }
        return "editArticle.html";
    }
}
