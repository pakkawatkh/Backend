package com.example.backend.service;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeException;
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

    public List<Type> findByActive() {
        return repository.findAllByActiveIsTrue();
    }
    public List<Type> findAll() {
        return repository.findAll();
    }


    public void setDefault() {
        Optional<Type> type = repository.findById(1);
        if (!type.isEmpty()) {
            return;
        }
        Type entity = new Type();

        entity.setName("ไม่ระบุประเภท");
        entity.setActive(true);
        entity.setId(1);

        repository.save(entity);
    }

    public void create(String name) throws BaseException {
        boolean type = repository.existsByName(name);

        if (type) {
            throw TypeException.nameDuplicated();
        }
        Type entity = new Type();

        entity.setName(name);
        entity.setActive(true);

        repository.save(entity);


    }

    public void edit(Integer id, String name) throws BaseException {
        Optional<Type> type = repository.findById(id);

        if (type.isEmpty()) {
            throw TypeException.notFoundId();
        }
        Type entity = type.get();

        if (entity.getName().equals(name)){
            System.out.println(entity.getName()+name);
            return;
        }
        if (repository.existsByName(name)) {
            throw TypeException.nameDuplicated();
        }
        entity.setName(name);

        repository.save(entity);
    }


    public Type findById(Integer id) throws BaseException {
        Optional<Type> byId = repository.findById(id);
        if (byId.isEmpty()){
            throw TypeException.notFoundId();
        }
        return byId.get();
    }

    public void deleteById(Integer id) throws BaseException {
        repository.deleteById(id);
    }

    public void changStatusById(Type type,Boolean active) throws BaseException {


        type.setActive(active);

        repository.save(type);
    }


}
