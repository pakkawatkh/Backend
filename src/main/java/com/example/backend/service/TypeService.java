package com.example.backend.service;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeException;
import com.example.backend.model.Response;
import com.example.backend.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    private final TypeRepository repository;

    public TypeService(TypeRepository repository) {
        this.repository = repository;

        this.setDefault();
    }

    public List<Type> list() {
        return repository.findAll();
    }


    public void setDefault() {
        Optional<Type> type = repository.findById(1);
        if (!type.isEmpty()) {
            return;
        }
        Type entity = new Type();

        entity.setName("ไม่ระบุประเภท");
        entity.setId(1);

        repository.save(entity);
    }

    public Object create(String name) throws BaseException {
        boolean type = repository.existsByName(name);

        if (type) {
            throw TypeException.nameDuplicated();
        }
        Type entity = new Type();

        entity.setName(name);

        repository.save(entity);

        return new Response().success("create success");

    }

    public Object edit(Integer id, String name) throws BaseException {
        Optional<Type> type = repository.findById(id);

        if (type.isEmpty()) {
            throw TypeException.notFoundId();
        }
        Type entity = type.get();

        entity.setName(name);

        repository.save(entity);

        return new Response().success("edit success");

    }

    public Object delete(Integer id) throws BaseException {
        Optional<Type> type = repository.findById(id);

        if (type.isEmpty()) {
            throw TypeException.notFoundId();
        }

        Type entity = type.get();

        repository.deleteById(entity.getId());

        return new Response().success("delete success");
    }
}
