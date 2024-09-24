package com.iastate.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class ItemsService {

    @Autowired
    ItemsRepo itemsRepo;

    //create
    public void createItem(Items item){
        itemsRepo.save(item);
    }

    //read
    public List<Items> getAllItems(){
        return itemsRepo.findAll();
    }

    public Items getItem(Long itemId){
        return itemsRepo.findById(itemId).orElseThrow();
    }

}
