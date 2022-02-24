package com.example.backend.api;

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
        Object list = business.getList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detailNews(@PathVariable Integer id){
        Object detailById = business.getDetailById(id);

        return ResponseEntity.ok(detailById);
    }

}
