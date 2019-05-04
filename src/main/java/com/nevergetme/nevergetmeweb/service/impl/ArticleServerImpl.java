package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.mapper.ArticleMapper;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServerImpl implements ArticleService {
    private final String ARTICLE_KEY = "article_";

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Article> findAllArticle() {
        List<Article> articles = articleMapper.getAllArticle();
        return articles;
    }

    @Override
    public List<Article> findUserArticle(int userid) {
        return articleMapper.getUserArticle(userid);
    }

    /**
     * 如果缓存中存在，则从缓存中读取
     *
     * @param id
     * @return
     */
    @Override
    public Article getArticleById(int id) {
        String key = ARTICLE_KEY + id;
        ValueOperations<String, Article> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            return operations.get(key);
        } else {
            Article article = articleMapper.getArticleById(id);
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
    }

    @Override
    @Transactional
    public int createNewArticle(Article article,List<Integer> tagid) {
        ValueOperations<String, Article> operations = redisTemplate.opsForValue();
        articleMapper.createNewArticle(article);
        int articleID =article.getId();
        for(int k:tagid)
            articleMapper.setArticleTags(articleID,k);
        if (articleID != 0) {
            String key = ARTICLE_KEY + articleID;
            if (redisTemplate.hasKey(key)) {
                redisTemplate.delete(key);
            }
            Article articleNew = getArticleById(articleID);
            if (articleNew != null) {
                operations.set(key, articleNew, 5, TimeUnit.HOURS);
            }
            //updateArticleListInRedis();
        }
        return articleID;
    }

    private void updateArticleListInRedis() {
        ListOperations<String, Article> operations = redisTemplate.opsForList();
        int pages=(int)Math.ceil(articleMapper.getTotalArticleCount()*1.0/StaticConfigParam.PAGE_SIZE);
        for(int i=0;i<=pages;i++){
            if(redisTemplate.hasKey(StaticConfigParam.ARTICLE_LIST_KEY+i)){
                redisTemplate.delete(StaticConfigParam.ARTICLE_LIST_KEY+i);
            }
        }
    }

    /**
     * 获取文章列表
     * 首先从缓存中读取，如果缓存中没有，则需要读取数据库，然后更新缓存再返回
     *
     * @return
     */
    @Override
    public List<Article> getArticleList(int pageNum) {
        return articleMapper.getArticleList();
//        ListOperations<String,Article> operations=redisTemplate.opsForList();
//        String key=StaticConfigParam.ARTICLE_LIST_KEY+pageNum;
//        if(redisTemplate.hasKey(key)){
//            System.out.println("read article list from redis");
//            return operations.range(key,0,-1);
//        }else{
//            List<Article> articleList=articleMapper.getArticleList();
//            if(articleList!=null&&articleList.size()>0){
//                operations.rightPushAll(key,articleList);
//            }
//                //
//            return articleList;
//        }
    }

    @Override
    public void updateVisitTimes(int id) {
        articleMapper.updateVisitTimes(id);
    }

    @Override
    public List<Tags> getAllTags(){
        return articleMapper.getAllTags();
    }

    @Override
    public void setArticleTags(int articleId,int tagid){
        articleMapper.setArticleTags(articleId,tagid);
    }
}
