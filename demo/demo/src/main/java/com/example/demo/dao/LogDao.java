package com.example.demo.dao;

import com.example.demo.entity.MyLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface LogDao extends Mapper<MyLog> {
}
