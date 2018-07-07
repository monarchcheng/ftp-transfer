package ftp.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FTPUtils {

    private FTPUtils(){

    }

    private FTPClient ftpClient = new FTPClient();

    private String server;

    private int port;

    private String userName;

    private String userPassword;

    @Value("${ftp.server}")
    public void setServer(String server) {
        this.server = server;
    }

    @Value("${ftp.port}")
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${ftp.username}")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Value("${ftp.password}")
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 连接FTP服务器
     * @return
     */
    private boolean connect(){
        boolean stat;
        try {
            if(ftpClient.isConnected())
                return true;
            ftpClient.connect(server, port);
            stat = true;
        } catch (IOException e) {
            System.out.println("59-------------");
            stat = false;
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * 打开FTP服务器
     * @return
     */
    private boolean open(){
        System.out.println(ftpClient.getReplyCode());
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
            return true;
        }

        if(!connect()){
            return false;
        }

        boolean stat;
        try {
            stat = ftpClient.login(userName, userPassword);
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close();
                stat = false;
            }
        } catch (IOException e) {
            System.out.println("90-------------");
            e.printStackTrace();
            close();
            stat = false;
        }
        return stat;
    }


    /**
     * 关闭FTP服务器
     */
    private void close(){
        try {
            if(ftpClient != null){
                if(ftpClient.isConnected()){
                    ftpClient.logout();
                    ftpClient.disconnect();
                }

                ftpClient = new FTPClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 转发ftp上的文件内容
     * @param ftpFile
     * @return
     */
    public void transfer(OutputStream outputStream,String ftpFile) {
        try {
            boolean open = open();
            if(open){
                ftpClient.retrieveFile(ftpFile, outputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}