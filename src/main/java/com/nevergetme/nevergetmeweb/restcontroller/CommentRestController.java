package com.nevergetme.nevergetmeweb.restcontroller;

import com.nevergetme.nevergetmeweb.bean.ArticleComment;
import com.nevergetme.nevergetmeweb.bean.ReplyComment;
import com.nevergetme.nevergetmeweb.config.StaticConfigParam;
import com.nevergetme.nevergetmeweb.service.ArticleCommentService;
import com.nevergetme.nevergetmeweb.service.ReplyCommentService;
import com.nevergetme.nevergetmeweb.utility.ContentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Alden He on 2019/5/7
 */
@RestController
@EnableAutoConfiguration
public class CommentRestController {
    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private ReplyCommentService replyCommentService;

    @RequestMapping(value = "/comments/addComments")
    public @ResponseBody
    Map<String, String> addComments(
            @RequestParam(value = "articleId", required = true) int articleId,
            @RequestParam(value = "content", required = true) String content,
            HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();
        int userId;
        if (articleId > StaticConfigParam.ARTICLE_ID_BEGIN && content.length() <= StaticConfigParam.COMMENT_MAX_LENGTH && content != "" && (userId = ContentUtility.getCurrentUserId(request)) != -1) {
            ArticleComment articleComment = new ArticleComment(userId, articleId, content);
            result.put("status", articleCommentService.addArticleComment(articleComment) + "");
            result.put("success", "1");
        } else {
            result.put("success", "0");
        }
        return result;
    }

    @RequestMapping(value = "/comments/getComments")
    public @ResponseBody
    List<ArticleComment> getCommentsByArticleId(@RequestParam(value = "articleId", required = true) int articleId) {
        if(articleId>=StaticConfigParam.ARTICLE_ID_BEGIN)
            return articleCommentService.getArticleCommentsByArticleId(articleId);
        else return null;
    }

    @RequestMapping(value = "/comments/addReplyComment")
    public @ResponseBody
    Map<String, String> addReplyComment(
            @RequestParam(value = "articleId", required = true) int articleId,
            @RequestParam(value = "commentId", required = true) int commentId,
            @RequestParam(value = "repliedUserId", required = true) int repliedUserId,
            @RequestParam(value = "content", required = true) String content,
            HttpServletRequest request
    ) {
        int replyUserId;
        Map<String, String> result = new HashMap<>();
        if (articleId>= StaticConfigParam.ARTICLE_ID_BEGIN &&commentId>0&&repliedUserId>=StaticConfigParam.USER_ID_BEGIN&&content!=""&&content.length()<=StaticConfigParam.COMMENT_MAX_LENGTH&&(replyUserId = ContentUtility.getCurrentUserId(request)) != -1) {
            ReplyComment replyComment=new ReplyComment(articleId,commentId,replyUserId,repliedUserId,content);
            int status=replyCommentService.addReplyCommentMapper(replyComment);
            result.put("status",status+"");
            result.put("success","1");
        } else {
            result.put("success", "0");
        }
        return result;
    }
}
