package com.iastate.shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "ItemsController", description = "REST APIs related to Items Entity!!!!")
@RestController
@EnableAutoConfiguration
@RequestMapping("/Items")
public class ItemsController {

    @Autowired
    ItemsService itemsService;

    @ApiOperation(value = "Get list of Items in the System ", response = Iterable.class, tags = "getItems")
    @GetMapping("")
    public List<Items> getAllItems(){
        return itemsService.getAllItems();
    }

    @ApiOperation(value = "Get specific Item in the System by ID ", response = Iterable.class, tags = "getItemById")
    @PutMapping("/{itemsId}")
    public Items getItemById(@PathVariable Long itemId){
        return itemsService.getItem(itemId);
    }

    @ApiOperation(value = "Create a new item (reserved for admins)", response = Iterable.class, tags = "updateItem")
    @PostMapping("/create")
    public void createItem(@RequestBody Items item){
        itemsService.createItem(item);
    }
}
