package com.nevergetme.nevergetmeweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
     /**
     　　* @description: TODO
     　　* @param [articleId, response, model, request]
     　　* @return java.lang.String
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/5/6 15:02
     　　*/
    public String showArticle(@PathVariable("articleId") int articleId,
                              HttpServletResponse response,
                              Model model,
                              HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if(articleId>0){
            model.addAttribute("articleID",articleId);
            model.addAttribute("userId",ContentUtility.getCurrentUserId(request));
            response.setHeader("articleId", articleId + "");
            return "showArticle";
        }
        return "404";
    }

    @GetMapping("/editArticle/{articleId}")
    public String updateArticle(@PathVariable("articleId") int articleId,
                                HttpServletResponse response,
                                Model model,
                                HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session=request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null){
            int authorId=(Integer) session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
            Article article=articleService.getArticleByUserIDAndID(articleId,authorId);
            if(article==null){
                return "404";
            }else{
                model.addAttribute("article",article);
                return "editArticle";
            }
        }else{
            return "404";
        }

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

    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping("/tags/{tagId}")
    public String getArticleByTagId(@PathVariable("tagId") int tagId,
                                    HttpServletResponse response,
                                    Model model,
                                    HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        model.addAttribute("tagId",tagId);
        return "tags";
    }

    @GetMapping("/statistics")
    public String statisticsPage(){
        return "statistics";
    }

    @GetMapping("/my")
    public String getMyMainPage(HttpServletRequest request){
        if(ContentUtility.getCurrentUser(request)!=null){
            return "my";
        }else{
            return "404";
        }
    }
    @GetMapping("/my/{userId}")
    public String getUserMainPage(@PathVariable("userId") int userId,
                                  HttpServletResponse response,
                                  Model model,
                                  HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        model.addAttribute("userId",userId);
        return "my";
    }
}
