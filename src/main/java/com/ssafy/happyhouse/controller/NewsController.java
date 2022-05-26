package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("")
    public String getNewsWithKeyword(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "30") int itemPerPage
    ) throws Exception {
        return newsService.getNewsWithKeyword(keyword, page, itemPerPage);
    }

}
