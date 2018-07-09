package ftp.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class FTPUtils {

    private FTPUtils(){

    }

    private static FTPClient ftpClient = new FTPClient();

    private static FtpProperties ftp;

    static{
        Yaml yaml = new Yaml();
        try{
            ftp =new FtpProperties(yaml.load(new FileInputStream(FTPUtils.class.getResource("/ftp.yml").getPath())));
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 连接FTP服务器
     * @return
     */
    private static boolean connect(){
        boolean stat;
        try {
            if(ftpClient.isConnected())
                return true;
            ftpClient.connect(ftp.getServer(), ftp.getPort());
            stat = true;
        } catch (IOException e) {
            stat = false;
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * 打开FTP服务器
     * @return
     */
    private static boolean open(){
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
            return true;
        }

        if(!connect()){
            return false;
        }

        boolean stat;
        try {
            stat = ftpClient.login(ftp.getUserName(), ftp.getPassword());
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close();
                stat = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
            stat = false;
        }
        return stat;
    }


    /**
     * 关闭FTP服务器
     */
    private static void close(){
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
    public static void transfer(OutputStream outputStream,String ftpFile) {
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