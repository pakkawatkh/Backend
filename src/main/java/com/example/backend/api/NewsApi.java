package com.example.backend.api;

import com.example.backend.exception.BaseException;
import com.example.backend.process.business.NewsBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsApi {

    private final NewsBusiness business;

    public NewsApi(NewsBusiness business) {
        this.business = business;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listNews(){
        Object res = business.getList();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detailNews(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.getDetailById(id);

        return ResponseEntity.ok(res);
    }
    @GetMapping("/recommend")
    public ResponseEntity<Object> recommend(){
        Object res = business.getRecommend();
        return ResponseEntity.ok(res);
    }

}
