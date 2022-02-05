package com.example.backend.service;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.repository.TypeBuyingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeBuyingService {


    public final TypeBuyingRepository repository;

    public TypeBuyingService(TypeBuyingRepository repository) {
        this.repository = repository;
    }

    public void saveBuying(Shop shop, String name) throws BaseException {

        if (repository.existsByShopAndName(shop, name)) {
            throw TypeBuyingException.nameDuplicate();
        }

        TypeBuying entity = new TypeBuying();

        entity.setName(name);
        entity.setShop(shop);
        repository.save(entity);
    }

    public List<TypeBuying> findByShop(Shop shop) {
        List<TypeBuying> buying = repository.findAllByShop(shop);
        return buying;

    }
}
