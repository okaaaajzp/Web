package com.example.demo.Service;


import com.example.demo.dao.CollectionDao;
import com.example.demo.entity.Commodities;
import com.example.demo.entity.UserCollection;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    @Resource
    private CollectionDao collectionDao;

    public List<Commodities> get(Integer id) {
        return collectionDao.get(id);
    }

    public void add(Integer userId, Integer cId) {
        UserCollection userCollection = new UserCollection();
        userCollection.setcId(cId);
        userCollection.setUserId(userId);
        if(collectionDao.exist(userId,cId) != null) return;
        collectionDao.insertSelective(userCollection);
        return ;
    }

    public void delete(Integer userId,Integer cId) {
        collectionDao.myDelete(userId,cId);
    }
}
