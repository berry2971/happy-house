package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.domain.entity.Article;
import com.ssafy.happyhouse.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "특정 게시판의 게시글 목록 반환")
    @GetMapping("")
    public List<Article> getArticles(
            @RequestParam String boardName,
            @RequestParam(required = false, defaultValue = "1") int page
    ) throws Exception {
        return articleService.getArticlesByBoardName(boardName, page);
    }

    @ApiOperation(value = "게시글 조회", notes = "id에 해당하는 게시글 반환")
    @GetMapping("/{id}")
    public Article getArticle(
            @PathVariable Long id
    ) throws Exception {
        return articleService.getArticle(id);
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글 작성 후 게시글 반환")
    @PostMapping("")
    public Article writeArticle(
            @RequestBody Article article
    ) throws Exception {
        return articleService.createArticle(article);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제 후 boolean 반환")
    @DeleteMapping("/{id}")
    public boolean removeArticle(
            @PathVariable Long id
    ) throws Exception {
        return articleService.removeArticle(id);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정 후 게시글 반환")
    @PutMapping("/{id}")
    public Article modifyArticle(
            @PathVariable Long id,
            @RequestBody Article article
    ) throws Exception {
        return articleService.modifyArticle(id, article);
    }

}
