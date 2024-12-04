package com.example.demo;
import com.example.demo.Service.CommoditiesService;
import com.example.demo.common.Result;
import com.example.demo.entity.Commodities;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commodities")
@CrossOrigin
@Slf4j
public class CommoditiesController {
    @Resource
    CommoditiesService commoditiesService = new CommoditiesService();

    @GetMapping("/findAll/{pageNo}/{size}")
    public Result findAll(@PathVariable Integer pageNo,@PathVariable Integer size){
        PageInfo<Commodities> info = commoditiesService.findAll(pageNo,size);
        return Result.success(info);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Commodities commodities){
        commoditiesService.add(commodities);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        commoditiesService.delete(id);
        return Result.success();
    }

    @GetMapping("/getBySeller/{pageNo}/{size}/{sellerId}")
    public Result getBySeller(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable Integer sellerId){
        PageInfo<Commodities> info = commoditiesService.getBySeller(pageNo,size,sellerId);
        return Result.success(info);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Commodities commodities){
        //id为空，即添加新商品，否则为修改原有商品
        if(commodities.getId()==null) commoditiesService.add(commodities);
        else commoditiesService.update(commodities);
        return Result.success();
    }

    @GetMapping("/search/{pageNo}/{size}")
    public Result search(@PathVariable Integer pageNo,@PathVariable Integer size,@Param("name") String name){
        PageInfo<Commodities> info = commoditiesService.search(pageNo,size,name);
        return Result.success(info);
    }

    @GetMapping("getCurrent/{userId}/{id}")
    public Result getCurrent(@PathVariable Integer userId,@PathVariable Integer id){
        //log.info("用户id："+userId+"浏览了商品："+id);
        //return Result.success(commoditiesService.getCurrent(id));
        return Result.success();
    }

    @GetMapping("/getByType/{pageNo}/{size}/{type}")
    public Result getByType(@PathVariable Integer pageNo,@PathVariable Integer size,@PathVariable Integer type){
        PageInfo<Commodities> info = commoditiesService.getByType(pageNo,size,type);
        return Result.success(info);
    }
}
