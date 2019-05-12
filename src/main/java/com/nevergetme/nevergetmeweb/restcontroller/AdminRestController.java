package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.PublishDateStatistical;
import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import com.nevergetme.nevergetmeweb.service.StatisticsService;
import com.nevergetme.nevergetmeweb.service.UserService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by Alden He on 2019/5/11
 */
@RestController
@EnableAutoConfiguration
public class AdminRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StatisticsService statisticsService;

    public boolean isAdmin(HttpServletRequest request){
        int id;
        if((id= ContentUtility.getCurrentUserId(request))!=-1){
            User user=userService.findUserByUserId(id);
            if(StaticConfigParam.ADMIN_ROLE.equals(user.getRole())){
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/admin/getUserList")
    public @ResponseBody
    List<User> getUserList(HttpServletRequest request){
        if(isAdmin(request))
            return userService.findAllUser();
        return null;
    }

    @RequestMapping(value = "/admin/getArticleList")
    public @ResponseBody List<Article> getArticleList(HttpServletRequest request){
        if(isAdmin(request))
            return articleService.findAllArticle();
        return null;
    }
    @RequestMapping(value = "/admin/getStatisticalDataOfPublishDate")
    public @ResponseBody List<PublishDateStatistical> getStatisticalDataOfPublishDate(HttpServletRequest request){
        if(isAdmin(request))
            return articleService.getStatisticalDataOfPublishDate();
        return null;
    }
    @RequestMapping(value = "/admin/getTagsOfArticleCountStatistics")
    public @ResponseBody List<Tags> getTagsOfArticleCountStatistics(HttpServletRequest request){
        if(isAdmin(request))
            return statisticsService.getTagsOfArticleCountStatistics();
        return null;
    }
}
