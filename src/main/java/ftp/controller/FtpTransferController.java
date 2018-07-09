package ftp.controller;

import ftp.common.FTPUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/ftp")
@RestController
public class FtpTransferController {

    @RequestMapping("/getpic")
    public void ftpTransfer(@RequestParam String url, HttpServletResponse response){
        try {
//            List<String> list = Arrays.asList(url.split("/"));  //转发文件时可设置文件名
//            String fileName = list.get(list.size()-1) ;
//            response.setCharacterEncoding("UTF-8");
//            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            FTPUtils.transfer(response.getOutputStream(),url);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
