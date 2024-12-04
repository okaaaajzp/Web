package com.example.demo.dao;

import com.example.demo.entity.Commodities;
import com.example.demo.entity.UserCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CollectionDao extends Mapper<UserCollection> {
    @Select("select b.* from collection as a left join commodities as b on a.cId = b.id where a.userId = #{userId} ")
    List<Commodities> get(@Param("userId") Integer userId);

    @Select("select * from collection where userId = #{userId} and cId = #{cId} limit 1")
    UserCollection exist(@Param("userId") Integer userId,@Param("cId") Integer cId);

    @Delete("delete from collection where userId = #{userId} and cId = #{cId}")
    void myDelete(@Param("userId") Integer userId,@Param("cId") Integer cId);
}
