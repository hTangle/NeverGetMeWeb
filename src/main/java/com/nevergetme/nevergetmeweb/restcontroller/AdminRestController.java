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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean isAdmin(HttpServletRequest request) {
        User user;
        if ((user = ContentUtility.getCurrentUser(request)) != null && StaticConfigParam.ADMIN_ROLE.equals(user.getRole())) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/admin/getUserList")
    public @ResponseBody
    List<User> getUserList(HttpServletRequest request) {
        if (isAdmin(request))
            return userService.findAllUser();
        return null;
    }

    @RequestMapping(value = "/admin/getArticleList")
    public @ResponseBody
    List<Article> getArticleList(HttpServletRequest request) {
        if (isAdmin(request))
            return articleService.findAllArticle();
        return null;
    }

    @RequestMapping(value = "/admin/getStatisticalDataOfPublishDate")
    public @ResponseBody
    List<PublishDateStatistical> getStatisticalDataOfPublishDate(HttpServletRequest request) {
        if (isAdmin(request))
            return articleService.getStatisticalDataOfPublishDate();
        return null;
    }

    @RequestMapping(value = "/admin/getTagsOfArticleCountStatistics")
    public @ResponseBody
    List<Tags> getTagsOfArticleCountStatistics(HttpServletRequest request) {
        if (isAdmin(request))
            return statisticsService.getTagsOfArticleCountStatistics();
        return null;
    }

    @RequestMapping(value = "/admin/updateUserRoleById")
    public @ResponseBody
    Map<String, String> updateUserRoleById(
            @RequestParam(value = "userID") int userID,
            @RequestParam(value = "role") int role,
            HttpServletRequest request
    ) {
        Map<String, String> map = new HashMap<>();
        if (isAdmin(request)) {
            userService.updateUserRoleById(userID, role);
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        return map;
    }

    @RequestMapping(value = "/admin/updateArticleIsStick")
    public void updateArticleIsStick(
            @RequestParam(value = "articleID") int articleID,
            @RequestParam("isStick") int isStick,
            HttpServletRequest request) {
        if(isAdmin(request)){
            if(isStick!=1){
                isStick=0;
            }
            articleService.updateArticleIsStick(articleID,isStick);
        }
    }
}
