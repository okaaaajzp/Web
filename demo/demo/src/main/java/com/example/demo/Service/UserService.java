package com.example.demo.Service;


import com.example.demo.common.JwtProperties;
import com.example.demo.common.MD5Utils;
import com.example.demo.dao.TypeDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.MyLog;
import com.example.demo.entity.myTable;
import com.example.demo.entity.Type;
import com.example.demo.entity.User;
import com.example.demo.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserDao userDao;
    @Autowired
    private JwtProperties jwtProperties;
    @Resource
    private TypeDao typeDao;

    public List<User> findAll() {
        return userDao.selectAll();
    }

    public String add(User user) {
        user.setPassword(MD5Utils.encrypByMd5(user.getPassword()));
        User temp = userDao.selectByName(user.getName());
        if(temp != null) return "用户已存在";
        user.setState(true);
        userDao.insertSelective(user);
        return "";
    }

    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
        return;
    }

    public void update(User user) {
        user.setPassword(MD5Utils.encrypByMd5(user.getPassword()));
        userDao.updateByPrimaryKeySelective(user);
        return;
    }

    public User login(String email, String password){
        User user = userDao.searchByEmail(email);
        password =  MD5Utils.encrypByMd5(password);
        if(user == null) return null;
        if(password.equals(user.getPassword())) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("userId",user.getId());
            user.setToken(JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims));
            return user;
        }
        return null;
    }

    public User searchByEmail(String email) {
        return userDao.searchByEmail(email);
    }

    public String getEmail(Integer id){
        User user = userDao.selectByPrimaryKey(id);
        return user.getEmail();
    }

    public boolean logOff(Integer userId,String password) {
        User user = userDao.selectByPrimaryKey(userId);
        if(MD5Utils.encrypByMd5(password).equals(user.getPassword())) {
            user.setState(false);
            userDao.updateByPrimaryKeySelective(user);
            return true;
        }
        return false;
    }

    public List<MyLog> getMyLog(Integer sellerId){
        return userDao.GetMyLog(sellerId);
    }

    public void recharge(Integer userId, Double money) {
        User user = userDao.selectByPrimaryKey(userId);
        user.setBalance(user.getBalance() + money);
        userDao.updateByPrimaryKeySelective(user);
        return;
    }

    public List<Type> getTypes(){
        return typeDao.getTypes();
    }

    public User getById(Integer id){
        return userDao.selectByPrimaryKey(id);
    }

}
