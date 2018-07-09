package ftp.controller;

import ftp.common.FTPUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/ftp")
@RestController
public class FtpTransferController {

    /**
     * 转发获取图片（直接展示）
     * @param url  文件路径
     * @param response
     */
    @RequestMapping("/img")
    public void ftpTransferImg(@RequestParam String url, HttpServletResponse response){
        try {
            FTPUtils.transfer(response.getOutputStream(),url);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 转发文件（直接下载）
     * @param url  文件路径
     * @param response
     */
    @RequestMapping("/file")
    public void ftpTransferFile(@RequestParam String url, HttpServletResponse response){
        try {
            List<String> list = Arrays.asList(url.split("/"));
            String fileName = list.get(list.size()-1) ;
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" +URLEncoder.encode(fileName,"UTF-8") );
            FTPUtils.transfer(response.getOutputStream(),url);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
