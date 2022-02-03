package com.example.backend.api;

import com.example.backend.entity.News;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsApi {

    @PostMapping("/list")
    public ResponseEntity<Object> listNews(){

        return ResponseEntity.ok(5);
    }

    @PostMapping("/detail")
    public ResponseEntity<Object> detailNews(@RequestBody News req){

        return ResponseEntity.ok(req);
    }


}
