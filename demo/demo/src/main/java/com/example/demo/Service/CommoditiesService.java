package com.example.demo.Service;

import com.example.demo.dao.CommoditiesDao;
import com.example.demo.entity.Commodities;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommoditiesService {

    @Resource
    private CommoditiesDao commoditiesDao;


    public PageInfo<Commodities> findAll(Integer pageNo,Integer size){
        PageHelper.startPage(pageNo,size);
        List<Commodities> list = commoditiesDao.findAll();
        return PageInfo.of(list);
    }

    public void add(Commodities commodities){
        commoditiesDao.insertSelective(commodities);
        return;
    }

    public void delete(Integer id) {
        Commodities commodities =  commoditiesDao.selectByPrimaryKey(id);
        commodities.setState(false);
        commoditiesDao.updateByPrimaryKeySelective(commodities);
    }


    public void update(Commodities commodities) {
        commoditiesDao.updateByPrimaryKeySelective(commodities);
    }

    public PageInfo<Commodities> search(Integer pageNo, Integer size, String name) {
        name = '%'+name+'%';
        PageHelper.startPage(pageNo,size);
        List<Commodities> list = commoditiesDao.search(name);
        return PageInfo.of(list);
    }

    public Commodities getCurrent(Integer id) {
        return commoditiesDao.selectById(id);
    }

    public PageInfo<Commodities> getByType(Integer pageNo,Integer size,Integer type) {
        PageHelper.startPage(pageNo,size);
        List<Commodities> list = commoditiesDao.selectByType(type);
        return PageInfo.of(list);
    }

    public PageInfo<Commodities> getBySeller(Integer pageNo, Integer size,Integer sellerId) {
        PageHelper.startPage(pageNo,size);
        List<Commodities> list = commoditiesDao.selectBySeller(sellerId);
        return PageInfo.of(list);
    }
}
