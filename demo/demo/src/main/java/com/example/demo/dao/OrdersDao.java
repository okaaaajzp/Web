package com.example.demo.dao;

import com.example.demo.entity.Orders;
import com.example.demo.entity.myTable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrdersDao extends Mapper<Orders> {
    @Select("select orders.*,commodities.name as cName,commodities.img as img,commodities.introduce as intro from orders left join commodities on orders.cId = commodities.id where userId = #{userId}")
    List<Orders> selectByUser(@Param("userId") Integer userId);

    @Select("select orders.*,commodities.name as cName from orders left join commodities on orders.cId = commodities.id where userId = #{userId} and state = 3")
    List<Orders> selectFinished(@Param("userId") Integer userId);

    @Select("select sellerId from commodities where id = #{cId} limit 1")
    Integer findSeller(@Param("cId") Integer cId);

    @Select("select a.*,commodities.name as cName from orders as a left join commodities on a.cId = commodities.id where a.id = #{id}")
    Orders selectById(@Param("id") Integer id);

    @Select("select orders.*,commodities.name as cName,commodities.img as img,commodities.introduce as intro,commodities.price as price from orders left join commodities on orders.cId = commodities.id where orders.sellerId = #{sellerId}")
    List<Orders> selectBySeller(@Param("sellerId") Integer sellerId);


}
