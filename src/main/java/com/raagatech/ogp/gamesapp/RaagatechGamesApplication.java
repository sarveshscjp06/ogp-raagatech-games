package com.raagatech.ogp.gamesapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources/games")
public class RaagatechGamesApplication {

    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about Raagatech Games Application";
    }
}
