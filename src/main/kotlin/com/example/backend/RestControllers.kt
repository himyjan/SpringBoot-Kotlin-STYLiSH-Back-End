package com.example.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("api/v1/article")
class ArticleConttroller {

    val articles = mutableListOf(Article("My Title", "my Content"))
    
    @GetMapping
    fun articles() = articles
}