package com.nevergetme.nevergetmeweb.controller;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    @Value("${file.uploadPath}")
    private String uploadFilePath;
    @RequestMapping(value = "/article/uploadImage")
    public @ResponseBody Map<String,String> getArticleImage(@RequestParam(value = "editormd-image-file",required = true)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        String trueFileName=file.getOriginalFilename();
        String suffix=trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName=System.currentTimeMillis()+"_"+ (new Random().nextInt(89999)+10000)+suffix;
        String path=uploadFilePath;
        System.out.println(path);
        File target=new File(path,fileName);
        Map<String,String> map=new HashMap<>();
        JSONObject jsonObject=new JSONObject();
        try{
            file.transferTo(target);
            map.put("success","1");
            map.put("message","upload success");
        } catch (IOException e) {
            map.put("success","0");
            map.put("message","upload failed");
            e.printStackTrace();
        }
        map.put("url","/source/img/"+fileName);
        return map;
    }

}
