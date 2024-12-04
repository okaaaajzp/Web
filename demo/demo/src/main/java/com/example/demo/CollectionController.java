package com.example.demo;


import com.example.demo.Service.CollectionService;
import com.example.demo.common.Result;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
@CrossOrigin
public class CollectionController {

    @Resource
    private CollectionService collectionService = new CollectionService();

    @GetMapping("/get/{id}")
    public Result get(@PathVariable Integer id){
        return Result.success(collectionService.get(id));
    }

    @PostMapping("/add/{userId}/{cId}")
    public Result add(@PathVariable Integer userId,@PathVariable Integer cId){
        collectionService.add(userId,cId);
        return Result.success();
    }

    @DeleteMapping("/delete/{userId}/{cId}")
    public Result delete(@PathVariable Integer userId,@PathVariable Integer cId){
        collectionService.delete(userId,cId);
        return Result.success();
    }


}
