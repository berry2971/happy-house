package com.ssafy.happyhousemay5.service;

import com.ssafy.happyhousemay5.domain.entity.Article;
import com.ssafy.happyhousemay5.mapper.ArticleMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public Article getArticle(
            Long id
    ) throws Exception {
        return articleMapper.findById(id);
    }

    public List<Article> getArticlesByBoardName(
            String boardName,
            int page
    ) throws Exception {
        final int ARTICLES_PER_PAGE = 15;
        int offset = ARTICLES_PER_PAGE * (page - 1);
        return articleMapper.findByBoardNameAndOffsetAndLimit(boardName, offset, ARTICLES_PER_PAGE);
    }

    public Article createArticle(
            Article article
    ) throws Exception {
        articleMapper.save(article);
        return articleMapper.findById(article.getId());
    }

    public boolean removeArticle(
            Long id
    ) throws Exception {
        Article findArticle = articleMapper.findById(id);
        if (findArticle == null) {
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }
        articleMapper.remove(id);
        return true;
    }

    public Article modifyArticle(
            Long id,
            Article article
    ) throws Exception {
        Article findArticle = articleMapper.findById(id);
        if (findArticle == null) {
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }
        articleMapper.modify(article);
        return article;
    }

}
