package com.example.backend.process.service;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.process.repository.TypeBuyingListRepository;
import com.example.backend.process.repository.TypeBuyingRepository;
import org.springframework.data.domain.PageRequest;
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

    public void saveCreate(Shop shop, String name) throws BaseException {
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

    public void saveEdit(TypeBuying buying, String name) throws BaseException {

        if (repository.existsByShopAndName(buying.getShop(), name)) {
            if (buying.getName().equals(name)) return;
            throw TypeBuyingException.nameDuplicate();
        }

        buying.setName(name);
        try {
            repository.save(buying);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void deleteById(Integer id, Shop shop) throws BaseException {
        if (!repository.existsByIdAndShop(id, shop)) throw MainException.accessDenied();

        try {
        repository.deleteById(id);
        }catch (Exception e){
            throw MainException.errorSave();
        }
    }

    public List<TypeBuying> findByShop(Shop shop) {
        return repository.findAllByShop(shop);
    }

    public List<TypeBuying> findByShopAndPage(Shop shop, Integer page) {
        PageRequest limit = PageRequest.of(page, 8);

        return repository.findAllByShop(shop, limit);

    }

    public Long countByShop(Shop shop) {
        return repository.count(shop);
    }


    public TypeBuying findByIdAndShop(Integer id, Shop shop) throws TypeBuyingException {
        Optional<TypeBuying> buying = repository.findByIdAndShop(id, shop);

        if (buying.isEmpty()) throw TypeBuyingException.notFound();

        return buying.get();

    }

    public TypeBuying findById(Integer id) throws BaseException {
        Optional<TypeBuying> byId = repository.findById(id);
        if (byId.isEmpty()) throw TypeBuyingException.notFound();

        return byId.get();
    }

    public void saveChild(TypeBuying buying, String name, String price) throws BaseException {

        boolean exists = buyingListRepository.existsByName(name);
        if (exists) throw TypeBuyingException.nameDuplicate();

        TypeBuyingList buyingList = new TypeBuyingList();
        buyingList.setBuying(buying);
        buyingList.setName(name);
        buyingList.setPrice(price);

        try {
            buyingListRepository.save(buyingList);
        } catch (Exception e) {
            throw MainException.errorSave();
        }

    }

    public void deleteChildById(Integer id) throws BaseException {
        try {
            buyingListRepository.deleteById(id);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void childExistsByIdAndBuying(TypeBuying buying, Integer id) throws BaseException {
        boolean exists = buyingListRepository.existsByIdAndBuying(id, buying);

        if (!exists) throw TypeBuyingException.notFound();
    }

    public TypeBuyingList childFindByIdAndBuying(TypeBuying buying, Integer id) throws BaseException {
        Optional<TypeBuyingList> buyingListById = buyingListRepository.findByIdAndBuying(id, buying);

        if (buyingListById.isEmpty()) throw TypeBuyingException.notFound();
        return buyingListById.get();
    }

    public void childEdit(TypeBuyingList buyingList, String name, String price) throws BaseException {

        if (buyingList.getName().equals(name)) return;

        if (buyingListRepository.existsByName(name)) throw TypeBuyingException.nameDuplicate();
        buyingList.setPrice(price);
        buyingList.setName(name);
        try {
            buyingListRepository.save(buyingList);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }
    public List<TypeBuyingList> childByBuyingId(TypeBuying buying){
        return buyingListRepository.findAllByBuying(buying);
    }
}
