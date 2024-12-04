package com.example.demo.Service;

import com.example.demo.OrdersController;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import jakarta.annotation.Resource;
import org.apache.poi.hpsf.Decimal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private CommoditiesDao commoditiesDao;
    @Resource
    private LogDao logDao;
    @Resource
    private UserDao userDao;
    @Resource
    private TableDao tableDao;

    public void addLog(MyLog myLog){
        logDao.insertSelective(myLog);
    }

    public List<Orders> findAll() {
        return ordersDao.selectAll();
    }

    public void update(Integer id) {
        Orders orders=ordersDao.selectByPrimaryKey(id);
        orders.setState(2);
        ordersDao.updateByPrimaryKeySelective(orders);
        return;
    }

    public boolean add(Orders orders) {

        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        orders.setTime(formatter.format(date));
        orders.setState(1);
        Commodities commodities = commoditiesDao.selectByPrimaryKey(orders.getcId());
        orders.setPrice(commodities.getPrice()*orders.getNum());
        User user = userDao.selectByPrimaryKey(orders.getUserId());
        if(user.getBalance()<orders.getPrice()){
            return false;
        }
        BigDecimal balance = new BigDecimal(user.getBalance() - orders.getPrice());
        user.setBalance(balance.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        userDao.updateByPrimaryKeySelective(user);
        ordersDao.insertSelective(orders);
        MyLog myLog = new MyLog(orders.getSellerId(), orders.getUserId(),orders.getcId(),"购买");
        this.addLog(myLog);
        return true;
    }

    public List<Orders> getMyOrders(Integer userId,Integer role){
        String type;
        if(role == 1)
            return ordersDao.selectByUser(userId);
        else return ordersDao.selectBySeller(userId);
    }

    public List<Orders> getFinished(Integer userId){
        return ordersDao.selectFinished(userId);
    }

    public Orders comfirm(Integer id, Integer sellerId) {
        Orders order = ordersDao.selectById(id);
        Integer seller = ordersDao.findSeller(order.getcId());
        if(seller != sellerId){
            return null;
        }
        else {
            order.setState(2);
            ordersDao.updateByPrimaryKeySelective(order);
            return order;
        }
    }


    public Orders access(Integer id, Integer userId) {
        Orders order = ordersDao.selectByPrimaryKey(id);
        if(userId == order.getUserId()){
            order.setState(3);
            ordersDao.updateByPrimaryKeySelective(order);
            return order;
        }
        else {
            return null;
        }
    }


    public List<myTable> getTable(Integer id){
        List<myTable> table = tableDao.getTable(id);
        return table;
    }
}
