package com.example.backend.process.service;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.process.repository.TypeBuyingListRepository;
import com.example.backend.process.repository.TypeBuyingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeBuyingService {


    public final TypeBuyingRepository repository;
    public final TypeBuyingListRepository buyingListRepository;

    public TypeBuyingService(TypeBuyingRepository repository, TypeBuyingListRepository buyingListRepository) {
        this.repository = repository;
        this.buyingListRepository = buyingListRepository;
    }

    public void saveBuying(Shop shop, String name) throws BaseException {
        if (repository.existsByShopAndName(shop, name)) throw TypeBuyingException.nameDuplicate();

        TypeBuying entity = new TypeBuying();
        entity.setName(name);
        entity.setShop(shop);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public List<TypeBuying> findByShop(Shop shop) {
        List<TypeBuying> buying = repository.findAllByShop(shop);

        return buying;

    }

    public TypeBuying findById(Integer id) throws BaseException {
        Optional<TypeBuying> byId = repository.findById(id);
        if (byId.isEmpty()) throw TypeBuyingException.notFound();

        return byId.get();
    }

    public void saveChild(TypeBuying buying ,String name,Integer price){
        TypeBuyingList buyingList = new TypeBuyingList();
        buyingList.setBuying(buying);
        buyingList.setName(name);
        buyingList.setPrice(price);
        buyingListRepository.save(buyingList);

    }
}
