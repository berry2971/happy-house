package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.domain.entity.Comment;
import com.ssafy.happyhouse.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "댓글 목록 조회", notes = "특정 게시글의 댓글 목록 반환")
    @GetMapping("")
    public List<Comment> getArticles(
            @RequestParam Long articleId,
            @RequestParam(required = false, defaultValue = "1") int page
    ) throws Exception {
        return commentService.getCommentsByArticleId(articleId, page);
    }

    @ApiOperation(value = "댓글 조회", notes = "id에 해당하는 댓글 반환")
    @GetMapping("/{id}")
    public Comment getComment(
            @PathVariable Long id
    ) throws Exception {
        return commentService.getComment(id);
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 후 댓글 반환")
    @PostMapping("")
    public Comment writeComment(
            @RequestBody Comment comment,
            HttpServletRequest request
    ) throws Exception {
        String loginId = (String)request.getAttribute("userId");
       comment.setAuthor(loginId);
        return commentService.createComment(comment);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 후 boolean 반환")
    @DeleteMapping("/{id}")
    public boolean removeArticle(
            @PathVariable Long id
    ) throws Exception {
        return commentService.removeComment(id);
    }

}
