package com.example.demo.dao;

import com.example.demo.entity.Type;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TypeDao extends Mapper<Type> {
    @Select("select * from type")
    List<Type> getTypes();
}
