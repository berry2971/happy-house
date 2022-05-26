package com.ssafy.happyhouse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceTest {

    @Autowired NewsService newsService;

    @Test
    void getNewsWithKeyword() throws UnsupportedEncodingException, JsonProcessingException {
        String res = newsService.getNewsWithKeyword("얼룩말", 1, 15);
        System.out.println(res);
    }

}