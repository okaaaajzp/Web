package com.example.demo;


import com.example.demo.Service.EmailService;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.UserService;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    UserService userService=new UserService();
    @Resource
    EmailService emailService=new EmailService();
    @Resource
    RedisService redisService=new RedisService();

    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(userService.findAll());
    }

    @PostMapping("/register")
    public Result add(@RequestBody User user,@Param("code") String code){
        if(code != null & code.equals(redisService.getValue(user.getEmail()))) {
            String msg = userService.add(user);
            if(msg.equals("")) return Result.success();
            return Result.error(msg);
        }
        else{
            return Result.error("验证码错误");
        }
    }

    @PostMapping("/getCode/{email}")
    public Result getCode(@PathVariable String email){
        if(userService.searchByEmail(email) != null) return Result.error("该邮箱已注册");
        String body ="欢迎使用小黑子购物网站\n若非本人使用本网站请忽视该邮件\n以下是本次验证码:\n";
        SecureRandom random = new SecureRandom();
        //String randomInt = Integer.toString(random.nextInt(1000000)); // 生成0到999999的安全随机整数
        String randomInt = String.format("%06d", random.nextInt(1000000));
        System.out.println(randomInt);
        body = body + randomInt;
        if(emailService.sendEmail(email,"小黑子购物网站验证码",body)) {
            redisService.setValueWithExpiry(email, randomInt, 5, TimeUnit.MINUTES);
            return Result.success();
        }
        return Result.error("操作过于频繁");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User user1 = userService.login(user.getEmail(),user.getPassword());
        if(user1 != null) return Result.success(user1);
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/logOff/{userId}/{password}")
    public Result logOff(@PathVariable Integer userId,@PathVariable String password){
        if(userService.logOff(userId,password)) return Result.success();
        return Result.error("密码错误");
    }


    @PostMapping("/retrieve/getCode")
    public Result retrieve(@Param("email") String email ){
        if(userService.searchByEmail(email) == null) return Result.error("该邮箱未注册");
        String body ="欢迎使用小黑子购物网站\n若非本人使用本网站请忽视该邮件\n以下是本次验证码:\n";
        SecureRandom random = new SecureRandom();
        //String randomInt = Integer.toString(random.nextInt(1000000)); // 生成0到999999的安全随机整数
        String randomInt = String.format("%06d", random.nextInt(1000000));
        System.out.println(randomInt);
        body = body + randomInt;
        if(emailService.sendEmail(email,"小黑子购物网站验证码",body)) {
            redisService.setValueWithExpiry(email, randomInt, 5, TimeUnit.MINUTES);
            return Result.success();
        }
        return Result.error("操作过于频繁");
    }

    @PostMapping("/retrieve")
    public Result retrieve(@RequestBody User user,@Param("code") String code){
        if(code != null & code.equals(redisService.getValue(user.getEmail()))) {
            userService.update(user);
            return Result.success();
        }
        else{
            return Result.error("验证码错误");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }


    @GetMapping("/getMyLog/{sellerId}")
    public Result getMyLog(@PathVariable Integer sellerId){
        return Result.success(userService.getMyLog(sellerId));
    }

    @PostMapping("/recharge/{userId}/{money}")
    public Result recharge(@PathVariable Integer userId,@PathVariable Double money){
        userService.recharge(userId,money);
        return Result.success(userService.getById(userId));
    }

    @GetMapping("/getType")
    public Result getTypes(){
        return Result.success(userService.getTypes());
    }


}
