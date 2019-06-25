package com.nevergetme.nevergetmeweb.restcontroller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nevergetme.nevergetmeweb.bean.*;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.message.RabbiMQSender;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import com.nevergetme.nevergetmeweb.service.StatisticsService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@EnableAutoConfiguration
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private StatisticsService statisticsService;

//    @Autowired
//    private RabbiMQSender sender;

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
            @RequestParam(value = "articleShortcut", required = true) String articleShortcut,
            @RequestParam(value = "articleOriginal", required = true) int articleOriginal,
            @RequestParam(value = "articleTags", required = true) String articleTags,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, String> map = new HashMap<>();
        int id;
        if ((id= ContentUtility.getCurrentUserId(request))!=-1) {
            Article article = new Article(id, articleTitle, articleContent, articleShortcut, articleOriginal);
            List<Integer> arttcleTagsList = new ArrayList<>();
            if (articleTags.length() > 4) {
                String[] tags = articleTags.split(",");
                for (int i = 0; i < tags.length; i++) {
                    if (tags[i].length() > 3)
                        arttcleTagsList.add(Integer.parseInt(tags[i]));
                }
            }
            articleService.createNewArticle(article, arttcleTagsList);
            map.put("artilceId", "" + article.getId());
        } else {
            map.put("error", "login in first");
        }
        return map;
    }

    @RequestMapping(value = "/article/updateArticle")
    public @ResponseBody
    Map<String, String> updateArticle(@RequestParam(value = "articleContent", required = true) String articleContent,
                                      @RequestParam(value = "articleTitle", required = true) String articleTitle,
                                      @RequestParam(value = "articleID", required = true) int articleID,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        int userId;
        if ((userId=ContentUtility.getCurrentUserId(request))!=-1) {
            Article article = new Article(articleID,userId,articleTitle,articleContent);
            int status=articleService.updateArticleByIdAndUserid(article);
            map.put("status",""+status);
            map.put("success","1");
            map.put("articleID",""+articleID);
        }else{
            map.put("success","0");
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
        statisticsService.setVisitorCountStatistics(new Visitor(1));
        return articleService.getArticleById(articleId);
    }

    @RequestMapping("/article/getArticleList")
    public @ResponseBody
    PageInfo<Article> getArtilceList(
            @Param(value = "pageNum") int pageNum,
            @Param(value = "pageSize") int pageSize,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 5) pageSize = 5;
        PageHelper.startPage(pageNum, StaticConfigParam.PAGE_SIZE);
        return new PageInfo<>(articleService.getArticleList(pageNum));
    }

    @RequestMapping("/article/getArticleListByTagId")
    public @ResponseBody
    List<Article> getArticleListByTagId(@Param(value = "tagId") int tagId){
        return articleService.getArticleListByTagId(tagId);
    }

    @RequestMapping("/article/getAllTags")
    public @ResponseBody
    List<Tags> getAllTags() {
//        sender.send();
        return articleService.getAllTags();
    }

    @RequestMapping("/article/updateCover")
    public void updateCoverByContent(){
        List<Article> articles=articleService.findAllArticleWithoutUserAndTags();
        for(Article a:articles){
            a.setCover(ContentUtility.getCoverFromContent(a.getContent()));
            if(a.getCover()!=null){
                articleService.updateArticleCover(a);
            }
        }
    }

    @RequestMapping(value = "/article/searchArticle",method = RequestMethod.POST)
    public @ResponseBody List<Article> searchArticle(@RequestParam("contents")String contents){
        return articleService.getArticleListByTitle(contents);
    }

    @RequestMapping("/article/getSearchHistory")
    public @ResponseBody List<Search> getSearchHistory(){
        return articleService.getSearchHistory();
    }
}
