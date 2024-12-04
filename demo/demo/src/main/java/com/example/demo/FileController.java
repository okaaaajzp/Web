package com.example.demo;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {
    //文件磁盘路径
    private String fileUploadPath = System.getProperty("user.dir")+"/files/";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        System.out.println(fileUploadPath);
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);
        //获取文件大小
        long size = file.getSize();

        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = System.currentTimeMillis()+"";

        File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT + type);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        return Result.success(uuid);
    }

    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response){
        if(!FileUtil.isDirectory(fileUploadPath)){
            FileUtil.mkdir(fileUploadPath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(fileUploadPath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if(StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(fileUploadPath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}

