package com.example.demo.dao;

import com.example.demo.entity.MyLog;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {

    @Select("select * from user where name = #{name} and user.state = 1 limit 1")
    User selectByName(@Param("name") String name);

    @Select("select * from user where email = #{email} and user.state = 1 limit 1")
    User searchByEmail(@Param("email") String email);

    @Select("select log.*,user.name as userName,commodities.name as cName from log left join user on user.id = log.userId left join commodities on commodities.id = cId where log.sellerId = #{sellerId}")
    List<MyLog> GetMyLog(@Param("sellerId") Integer sellerId);
}
