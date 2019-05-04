package com.nevergetme.nevergetmeweb.restcontroller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.bean.User;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@EnableAutoConfiguration
public class ArticleRestContent {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/list")
    public @ResponseBody
    Map<String, Object> getAllTheArticle(HttpServletRequest request) {
        //HttpSession session = request.getSession(true);
        //if(session.getAttribute("userId") != null){
        //int userId=(Integer) session.getAttribute("userId");
        List<Article> articles = articleService.findUserArticle(103450);
        Map<String, Object> map = new HashMap<>();
        map.put("count", articles.size());
        map.put("state", 1);
        map.put("data", articles);
        return map;
        //}
        //return new HashMap<>();
    }

    @RequestMapping(value = "/article/details")
    public @ResponseBody
    Map<String, Object> getArticleById(int id, HttpServletRequest request) {
        Article articles = articleService.getArticleById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("state", 1);
        map.put("data", articles);
        return map;
    }

    @Value("${file.uploadPath}")
    private String uploadFilePath;

    @RequestMapping(value = "/article/uploadImage")
    public @ResponseBody
    Map<String, String> getArticleImage(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String trueFileName = file.getOriginalFilename();
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + (new Random().nextInt(89999) + 10000) + suffix;
        String path = uploadFilePath;
//        System.out.println(path);
        File target = new File(path, fileName);
        Map<String, String> map = new HashMap<>();
        try {
            file.transferTo(target);
            map.put("success", "1");
            map.put("message", "upload success");
        } catch (IOException e) {
            map.put("success", "0");
            map.put("message", "upload failed");
            e.printStackTrace();
        }
        map.put("url", "/" + fileName);
        return map;
    }

    @RequestMapping(value = "/article/createArticle")
    public @ResponseBody
    Map<String, String> createArticle(
            @RequestParam(value = "articleContent", required = true) String articleContent,
            @RequestParam(value = "articleTitle", required = true) String articleTitle,
            @RequestParam(value = "articleShortcut",required = true)String articleShortcut,
            @RequestParam(value = "articleOriginal",required = true)int articleOriginal,
            @RequestParam(value = "articleTags",required = true)String articleTags,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        if(session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID)!=null) {
            int id = (Integer) session.getAttribute(StaticConfigParam.LOGIN_IN_USER_ID);
            Article article = new Article(id, articleTitle, articleContent, articleShortcut,articleOriginal);
            List<Integer> arttcleTagsList=new ArrayList<>();
            if(articleTags.length()>4) {
                String[] tags = articleTags.split(",");
                for (int i = 0; i < tags.length; i++) {
                    if(tags[i].length()>3)
                        arttcleTagsList.add(Integer.parseInt(tags[i]));
                }
            }
            articleService.createNewArticle(article,arttcleTagsList);
            map.put("artilceId", "" + article.getId());
        }else{
            map.put("error","login in first");
        }
        return map;
    }

    @RequestMapping(value = "/article/getArticle")
    public @ResponseBody
    Article getArticle(@RequestParam(value = "articleId", required = true) int articleId,
                       HttpServletRequest request,
                       HttpServletResponse response
    ) {
        articleService.updateVisitTimes(articleId);
        return articleService.getArticleById(articleId);
    }

    @RequestMapping("/article/getArticleList")
    public @ResponseBody
    PageInfo<Article> getArtilceList(
            @Param(value = "pageNum")int pageNum,
            @Param(value = "pageSize")int pageSize,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if(pageNum<1)pageNum=1;
        if(pageSize<5)pageSize=5;
        PageHelper.startPage(pageNum,StaticConfigParam.PAGE_SIZE);
        return new PageInfo<>(articleService.getArticleList(pageNum));
    }

    @RequestMapping("/article/getAllTags")
    public @ResponseBody List<Tags> getAllTags(){
        return articleService.getAllTags();
    }
}
