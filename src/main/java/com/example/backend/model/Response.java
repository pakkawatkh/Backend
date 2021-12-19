package com.example.backend.model;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class Response {

    private final LocalDateTime timestamp = LocalDateTime.now();
    public Object success(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp",timestamp);
        res.put("status", 1);
        res.put("message", message);

        return res;


    }

    public Object failed(String message){

        Map<Object, Object> res = new HashMap<>();
        res.put("timestamp",timestamp);
        res.put("status", 0);
        res.put("message", message);

        return res;
    }
}
