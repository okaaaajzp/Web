package com.example.demo.dao;

import com.example.demo.entity.Commodities;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommoditiesDao extends Mapper<Commodities> {

    @Select("select a.*,b.name as typeName,c.name as sellerName " +
            "from commodities as a left join type as b on a.typeId = b.id left join user as c on a.sellerId = c.id where a.state = 1")
    List<Commodities> findAll();


    @Select("select a.*,b.name as typeName,c.name as sellerName " +
            "from commodities as a left join type as b on a.typeId = b.id left join user as c on a.sellerId = c.id " +
            "where a.name like #{name} and a.state = 1")
    List<Commodities> search(@Param("name") String name);

    @Select("select a.*,b.name as typeName,c.name as sellerName " +
            "from commodities as a left join type as b on a.typeId = b.id left join user as c on a.sellerId = c.id " +
            "where a.id = #{id} and a.state = 1 limit 1 ")
    Commodities selectById(@Param("id") Integer id);

    @Select("select a.*,b.name as typeName,c.name as sellerName " +
            "from commodities as a left join type as b on a.typeId = b.id left join user as c on a.sellerId = c.id " +
            "where a.typeId = #{type} and a.state =1")
    List<Commodities> selectByType(@Param("type") Integer type);

    @Select("select a.*,b.name as typeName,c.name as sellerName " +
            "from commodities as a left join type as b on a.typeId = b.id left join user as c on a.sellerId = c.id " +
            "where a.sellerId = #{sellerId} and a.state = 1")
    List<Commodities> selectBySeller(Integer sellerId);
}
