package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.domain.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void getArticle() {
    }

    @Test
    void getArticlesByBoardName() {
    }

    @Test
    void createArticle() throws Exception {
        Article article1 = new Article(
                -1L,
                "게시판1",
                "테스트1",
                "글쓴이1",
                "비밀번호1",
                "글쓴시간1",
                "내용1"
        );
        Article article2 = new Article(
                -2L,
                "게시판2",
                "테스트2",
                "글쓴이2",
                "비밀번호2",
                "글쓴시간2",
                "내용2"
        );
        Article articleResult1 = articleService.createArticle(article1);
        Article articleResult2 = articleService.createArticle(article2);
        assertThat(articleResult1.getTitle()).isEqualTo("테스트1");
        assertThat(articleResult2.getTitle()).isEqualTo("테스트2");
        assertThat(articleResult1.getId()).isNotEqualTo(-1L);
        assertThat(articleResult2.getId()).isNotEqualTo(-2L);
    }

    @Test
    void removeArticle() {
    }

    @Test
    void modifyArticle() {
    }

}