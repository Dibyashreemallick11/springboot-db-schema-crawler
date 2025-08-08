package com.vistora.crawler.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vistora.crawler.model.TableMetadata;
import com.vistora.crawler.service.DatabaseCrawlerService;
import com.vistora.crawler.service.ModelGeneratorService;

import java.util.List;

@RestController
@RequestMapping("/api/crawlerr")
public class PostGreCrawlerController {

    @Autowired
    private DatabaseCrawlerService crawlerService;

    @Autowired
    private ModelGeneratorService modelGeneratorService;

    @GetMapping("/model")
    public List<TableMetadata> runCrawler() {
        // 1. Crawl DB and get metadata
        List<TableMetadata> table = crawlerService.crawlDatabase();

        // 2. Generate model .java files based on metadata
        modelGeneratorService.generateModels(table);

        // 3. Return metadata JSON in the API response
        return table;
    }
}
