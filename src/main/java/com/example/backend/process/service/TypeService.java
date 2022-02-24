package com.example.backend.process.service;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.TypeException;
import com.example.backend.process.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    private final TypeRepository repository;

    public TypeService(TypeRepository repository) throws BaseException {
        this.repository = repository;
        this.setDefault();
    }

    public List<Type> findByActive() {
        return repository.findAllByActiveIsTrue();
    }

    public List<Type> findAll() {
        return repository.findAll();
    }

    public void setDefault() throws BaseException {
        Optional<Type> type = repository.findById(1);
        if (!type.isEmpty()) return;

        Type entity = new Type();
        entity.setName("ไม่ระบุประเภท");
        entity.setActive(true);
        entity.setId(1);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void create(String name) throws BaseException {
        boolean type = repository.existsByName(name);
        if (type) throw TypeException.nameDuplicated();

        Type entity = new Type();
        entity.setName(name);
        entity.setActive(true);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }

    }

    public void edit(Integer id, String name) throws BaseException {
        Optional<Type> type = repository.findById(id);
        if (type.isEmpty()) throw TypeException.notFoundId();

        Type entity = type.get();

        if (entity.getName().equals(name)) return;
        if (repository.existsByName(name)) throw TypeException.nameDuplicated();

        entity.setName(name);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }


    public Type findById(Integer id) throws BaseException {
        Optional<Type> byId = repository.findById(id);
        if (byId.isEmpty()) throw TypeException.notFoundId();

        return byId.get();
    }

    public void deleteById(Integer id) throws BaseException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void changStatusById(Type entity, Boolean active) throws BaseException {
        entity.setActive(active);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }


}
