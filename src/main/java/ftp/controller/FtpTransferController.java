package ftp.controller;

import ftp.common.FTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/ftp")
@RestController
public class FtpTransferController {

    @Autowired
    private FTPUtils ftpUtils;


    @RequestMapping("/getpic")
    public void ftpTransfer(@RequestParam String url, HttpServletResponse response){
        try {
            ftpUtils.transfer(response.getOutputStream(),url);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
