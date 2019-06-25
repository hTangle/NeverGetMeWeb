package com.nevergetme.nevergetmeweb.service.impl;

import com.nevergetme.nevergetmeweb.bean.Article;
import com.nevergetme.nevergetmeweb.bean.PublishDateStatistical;
import com.nevergetme.nevergetmeweb.bean.Search;
import com.nevergetme.nevergetmeweb.bean.Tags;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.mapper.ArticleMapper;
import com.nevergetme.nevergetmeweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServerImpl implements ArticleService {
    /**
     * 使用线程池更新SearchHistory
     * 如果在新增博客时，我们需要判断新的博客标题是否包含在之前的SearchHistory中，
     * 我们使用一个HashTable保存搜索历史，遍历这个HashTable，如果搜索历史与当前标题相匹配，
     * 则需要将该搜索历史缓存删除
     * 大概就是实现这样一个功能
     * 现在有以下几个问题：
     *      1. 使用redisTemplate进行删除时，如果存在并发删除怎么办
     *      2. searchHistory的HashTable会不会存在并发问题
     */
    @Async
    public void updateSearchHistoryAsyc(String content){
        searchHistoryTable.forEach((String s,Integer i)->{
            if(content.contains(s)&&redisTemplate.hasKey(StaticConfigParam.SEARCH_HISTROY_LIST+s)){
                redisTemplate.delete(StaticConfigParam.SEARCH_HISTROY_LIST+s);
            }
        });
    }
//    class UpdateSearchHistoryThread implements Runnable{
//        String content;
//        UpdateSearchHistoryThread(String content){
//            this.content=content;
//        }
//        @Override
//        public void run() {
//            searchHistoryTable.forEach((String s,Integer i)->{
//                if(content.contains(s)&&redisTemplate.hasKey(StaticConfigParam.SEARCH_HISTROY_LIST+s)){
//                    redisTemplate.delete(StaticConfigParam.SEARCH_HISTROY_LIST+s);
//                }
//            });
//        }
//    }
    private final String ARTICLE_KEY = "article_";

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

//    private ExecutorService cacheThreadPool= Executors.newCachedThreadPool();

    private static final Hashtable<String,Integer> searchHistoryTable=new Hashtable<>();




    @Override
    public List<Article> findAllArticleWithoutUserAndTags() {
        return articleMapper.findAllArticleWithoutUserAndTags();
    }

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
            redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            return operations.get(key);
        } else {
            Article article = articleMapper.getArticleById(id);
            operations.set(key, article, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            return article;
        }
    }

    @Override
    public Article getArticleByUserIDAndID(int id, int authorId) {
        Article article = getArticleById(id);
        if (article.getAuthor().getId() == authorId) {
            return article;
        } else {
            return null;
        }
    }

    @Override
    public Article getArticleByArticleId(int id) {
        return articleMapper.getArticleByArticleId(id);
    }

    @Override
    /**
     　　* @description: TODO
     　　* @param [article] 
     　　* @return int
     　　* @throws 
     　　* @author Alden He
     　　* @date 2019/5/6 15:47
     　　*/
    public int updateArticleByIdAndUserid(Article article) {
        int status = articleMapper.updateArticleByIdAndUserid(article);
        String key = ARTICLE_KEY + article.getId();
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        return status;
    }

    @Override
    @Transactional
    /**
     　　* @description: 创建一个新的博客，首先将博客写入数据库，然后删除通过tagid寻找博客的相应的缓存
     * 之后再将这篇博客写入缓存
     　　* @param [article, tagid]
     　　* @return int
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/5/24 10:44
     　　*/
    public int createNewArticle(Article article, List<Integer> tagid) {
        ValueOperations<String, Article> operations = redisTemplate.opsForValue();
        articleMapper.createNewArticle(article);
        int articleID = article.getId();
        for (int k : tagid) {
            articleMapper.setArticleTags(articleID, k);
            deleteArticleTagListByKey(k);
        }
        if (articleID != 0) {
            String key = ARTICLE_KEY + articleID;
            if (redisTemplate.hasKey(key)) {
                redisTemplate.delete(key);
            }
            Article articleNew = getArticleById(articleID);
            if (articleNew != null) {
                operations.set(key, articleNew, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            }
        }
        if(searchHistoryTable.size()==0){
            loadSearchHistoryTable();
        }
        updateSearchHistoryAsyc(article.getContent());
        //cacheThreadPool.execute(new UpdateSearchHistoryThread(article.getContent()));
        return articleID;
    }

    private void deleteArticleTagListByKey(int tagid) {
        String key = StaticConfigParam.ARTICLE_LIST_TAG_ID + tagid;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
    private void loadSearchHistoryTable(){
        List<Search> searches=articleMapper.getSearchHistory();
        for(Search search:searches){
            searchHistoryTable.put(search.getContent(),1);
        }
    }

    private void updateArticleListInRedis() {
        ListOperations<String, Article> operations = redisTemplate.opsForList();
        int pages = (int) Math.ceil(articleMapper.getTotalArticleCount() * 1.0 / StaticConfigParam.PAGE_SIZE);
        for (int i = 0; i <= pages; i++) {
            if (redisTemplate.hasKey(StaticConfigParam.ARTICLE_LIST_KEY + i)) {
                redisTemplate.delete(StaticConfigParam.ARTICLE_LIST_KEY + i);
            }
        }
    }

    /**
     * 获取文章列表
     * 首先从缓存中读取，如果缓存中没有，则需要读取数据库，然后更新缓存再返回
     * PageHelper是采用AOP编程的，因此做缓存会出现问题
     *
     * @return
     */
    @Override
    public List<Article> getArticleList(int pageNum) {
        return articleMapper.getArticleList();
    }

    @Override
    public List<Article> getArticleList() {
        return articleMapper.getAllArticle();
    }

    @Override
    /**
     　　* @description: 查找的匹配方式是%contents%,如果检索到，则会将这个contents加到search History中
     　　* @param [contents]
     　　* @return java.util.List<com.nevergetme.nevergetmeweb.bean.Article>
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/5/23 19:04
     　　*/
    public List<Article> getArticleListByTitle(String contents) {
        if(searchHistoryTable.size()==0){
            loadSearchHistoryTable();
        }
        if(!searchHistoryTable.contains(contents)){
            searchHistoryTable.put(contents,1);
        }
        String key=StaticConfigParam.SEARCH_HISTROY_LIST+contents;
        ListOperations<String, Article> operations = redisTemplate.opsForList();
        if(redisTemplate.hasKey(key)){
            redisTemplate.expire(key,StaticConfigParam.EXPIRE_TIME_HOUR,TimeUnit.HOURS);
            addSearchHistory(new Search(contents, 1));
            return operations.range(key,0,-1);
        }else{
            List<Article> list = articleMapper.getArticleListByTitle("%" + contents + "%");
            if (list != null && list.size() > 0) {
                addSearchHistory(new Search(contents, 1));
                operations.rightPushAll(key,list);
                redisTemplate.expire(key,StaticConfigParam.EXPIRE_TIME_HOUR,TimeUnit.HOURS);
            }
            return list;
        }
    }

    @Override
    /**
     　　* @description: 首先判断缓存中有没有
     　　* @param [tagid]
     　　* @return java.util.List<com.nevergetme.nevergetmeweb.bean.Article>
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/5/24 10:27
     　　*/
    public List<Article> getArticleListByTagId(int tagid) {
        String key = StaticConfigParam.ARTICLE_LIST_TAG_ID + tagid;
        ListOperations<String, Article> operations = redisTemplate.opsForList();
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);//更新缓存过期时间
            return operations.range(key, 0, -1);
        } else {
            List<Article> articles = articleMapper.getArticleListByTagId(tagid);
            if (articles != null && articles.size() > 0) {
                operations.rightPushAll(key, articles);
                redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            }
            return articles;
        }
    }

    @Override
    public void updateVisitTimes(int id) {
        articleMapper.updateVisitTimes(id);
    }


    @Override
    /**
     　　* @description: 一般来说，tag都是不会变的，因此可以做持久缓存
     　　* @param []
     　　* @return java.util.List<com.nevergetme.nevergetmeweb.bean.Tags>
     　　* @throws
     　　* @author Alden He
     　　* @date 2019/5/24 10:38
     　　*/
    public List<Tags> getAllTags() {
        String key = StaticConfigParam.TAGS_LIST;
        ListOperations<String, Tags> operations = redisTemplate.opsForList();
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            return operations.range(key, 0, -1);
        } else {
            List<Tags> tags = articleMapper.getAllTags();
            if (tags != null && tags.size() > 0) {
                operations.rightPushAll(key, tags);
                redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            }
            return tags;
        }
    }

    @Override
    public void setArticleTags(int articleId, int tagid) {
        articleMapper.setArticleTags(articleId, tagid);
    }

    @Override
    public List<PublishDateStatistical> getStatisticalDataOfPublishDate() {
        return articleMapper.getStatisticalDataOfPublishDate();
    }

    @Override
    public void updateArticleIsStick(int id, int isStick) {
        articleMapper.updateArticleIsStick(id, isStick);
    }

    @Override
    public void updateArticleCover(Article article) {
        articleMapper.updateArticleCover(article);
    }

    //搜索记录相关
    @Override
    public List<Search> getSearchHistory() {
        String key = StaticConfigParam.SEARCH_HISTROY_LIST;
        ListOperations<String, Search> operations = redisTemplate.opsForList();
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            return operations.range(key, 0, -1);
        } else {
            List<Search> searches = articleMapper.getSearchHistory();
            if (searches != null && searches.size() > 0) {
                operations.rightPushAll(key, searches);
                redisTemplate.expire(key, StaticConfigParam.EXPIRE_TIME_HOUR, TimeUnit.HOURS);
            }
            return searches;
        }
    }

    @Override
    public int addSearchHistory(Search search) {
        if (redisTemplate.hasKey(StaticConfigParam.SEARCH_HISTROY_LIST))
            redisTemplate.delete(StaticConfigParam.SEARCH_HISTROY_LIST);
        return articleMapper.addSearchHistory(search);
    }
}
