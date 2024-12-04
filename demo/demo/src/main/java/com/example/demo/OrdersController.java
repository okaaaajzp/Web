package com.example.demo;

import com.example.demo.Service.CommoditiesService;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;
import com.example.demo.common.Result;
import com.example.demo.entity.Commodities;
import com.example.demo.entity.MyLog;
import com.example.demo.entity.Orders;
import com.example.demo.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Odd;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@Slf4j
public class OrdersController {

    @Resource
    private OrderService orderService = new OrderService();
    @Resource
    private EmailService emailService = new EmailService();
    @Resource
    private UserService userService = new UserService();
    @Resource
    private CommoditiesService commoditiesService = new CommoditiesService();

    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(orderService.findAll());
    }

    @PutMapping("/update/{id}")
    public Result update(@PathVariable Integer id){
        orderService.update(id);
        return Result.success();
    }

    @PostMapping("/sendOut/{id}/{sellerId}")
    public Result sendOut(@PathVariable("id") Integer id,@PathVariable("sellerId") Integer sellerId){
        Orders order = orderService.comfirm(id,sellerId);
        if(order != null){
            String body="您在小黑子购物平台购买的"+order.getcName()+"已发货";
            emailService.sendEmail(userService.getEmail(order.getUserId()),"您的订单已发货",body);
            return Result.success();
        }
        return Result.error("发货失败");
    }

    @PostMapping("/access/{id}/{userId}")
    public Result access(@PathVariable Integer id,@PathVariable Integer userId){
        Orders order = orderService.access(id,userId);
        if(order != null){
            return Result.success();

        }
        return Result.error("error");
    }

    @PostMapping("/add")
    public Result add(@RequestBody Orders orders){
        if(orderService.add(orders)) {
            User user = userService.getById(orders.getUserId());
            return Result.success(user.getBalance());
        }
        //log.info("用户id："+orders.getUserId()+"购买了"+orders.getNum()+"件商品："+orders.getcId());
        return Result.error("余额不足");
    }

    @GetMapping("/getMyOrders/{id}/{role}")
    public Result getMyOrders(@PathVariable Integer id,@PathVariable Integer role){
        return Result.success(orderService.getMyOrders(id,role));
    }

    @GetMapping("/getFinished/{id}")
    public Result getFinished(@PathVariable Integer id){
        return Result.success(orderService.getFinished(id));
    }

    @PostMapping("/buyAll")
    public Result buy(@RequestBody List<Orders> orders) {
        for(Orders order : orders){
            orderService.add(order);
        }
        User user = userService.getById(orders.get(0).getUserId());
        return Result.success(user.getBalance());
    }

    @PostMapping("/log")
    public Result addLog(@RequestBody MyLog myLog){
        orderService.addLog(myLog);
        return Result.success();
    }

    @PostMapping("/table/{id}")
    public Result getTable(@PathVariable Integer id){
        return Result.success(orderService.getTable(id));
    }
}
