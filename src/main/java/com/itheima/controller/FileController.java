package com.itheima.controller;

import com.itheima.utils.UploadUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @Author:cm
 * @Date:2021/1/18
 * @Description:com.itheima.controller
 * @version:1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/upload")
    public String upload(MultipartFile upload, HttpServletRequest request,String pdesc){
        try {
            //获取客户端上传的那个文件(就是upload)
            //准备接收存储客户端用于上传的那个文件
            ServletContext servletContext = request.getSession().getServletContext();
            //确保所有文件不是放在同一文件夹内
            String dir = UploadUtils.getDir();
            String realPath = servletContext.getRealPath("upload")+dir;
            File file = new File(realPath);
            if (file != null) {
                file.mkdirs();
            }
            //将客户端上传的文件写入准备好的文件中,确保文件名唯一
            String filename = upload.getOriginalFilename();
            String uuidName = UploadUtils.getUUIDName(filename);
            upload.transferTo(new File(file,uuidName));
            System.out.println("获取的请求参数文件描述为:"+pdesc);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
}
