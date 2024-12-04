package com.example.demo.dao;

import com.example.demo.entity.myTable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TableDao extends Mapper<myTable> {
    @Select("SELECT a.*,b.name as name FROM " +
            "(SELECT cId as id, SUM(price) as sum, COUNT(*) as num FROM orders " +
            "WHERE sellerId = #{sellerId} " +
            "GROUP BY cId)a LEFT JOIN commodities AS b " +
            "ON b.id = a.id")
    List<myTable> getTable(@Param("sellerId") Integer sellerId);
}
