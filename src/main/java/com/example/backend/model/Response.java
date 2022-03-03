package com.example.backend.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class Response {

    private final Date timestamp = new Date();

    public Object ok(String message, String key, Object value) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        Map<Object, Object> data = new HashMap<>();

        data.put(key, value);
        res.put("data", data);

        return res;
    }
    public Object ok2(String message, String key, Object value, String key2, Object value2) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        Map<Object, Object> data = new HashMap<>();

        data.put(key, value);
        data.put(key2, value2);
        res.put("data", data);

        return res;
    }
    public Object login(String message, String key, Object value, String key2, Object value2, String key3, Object value3) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        Map<Object, Object> data = new HashMap<>();

        data.put(key, value);
        data.put(key2, value2);
        data.put(key3, value3);
        res.put("data", data);

        return res;
    }

    public Object success(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);

        return res;


    }

    public Object failed(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.EXPECTATION_FAILED.value());
        res.put("message", message);

        return res;
    }
}
