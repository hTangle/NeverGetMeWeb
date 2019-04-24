package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.service.ArticleService;
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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        System.out.println(path);
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
        map.put("url", "/source/img/" + fileName);
        return map;
    }

    @RequestMapping(value = "/article/createArticle")
    public @ResponseBody
    Map<String, String> createArticle(
            @RequestParam(value = "articleContent", required = true) String articleContent,
            @RequestParam(value = "articleTitle", required = true) String articleTitle,
            @RequestParam(value = "userId", required = true) int userId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, String> map = new HashMap<>();
        Article article = new Article(userId, articleTitle, articleContent);
        articleService.createNewArticle(article);
        map.put("artilceId", "" + article.getId());
        return map;
    }

    @RequestMapping(value = "/article/getArticle")
    public @ResponseBody
    Article getArticle(@RequestParam(value = "articleId", required = true) int articleId,
                       HttpServletRequest request,
                       HttpServletResponse response
    ) {
        return articleService.getArticleById(articleId);
    }

    @RequestMapping("/article/getArticleList")
    public @ResponseBody
    List<Article> getArtilceList(HttpServletRequest request,
                                 HttpServletResponse response) {
        return articleService.getArticleList();
    }
}
