package com.lx.sftp.monitor;

import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;
import com.lx.sftp.SFTPChannel;
import com.lx.sftp.SFTPConstants;

/**
 * 测试监控传输进度
 */
public class SFTPCustomProgressMonitorTest {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	SFTPCustomProgressMonitorTest test = new SFTPCustomProgressMonitorTest();

        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.9.167.55");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "arthur");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        
        String src = "D:\\DevSoft\\HB-SnagIt1001.rar"; // 本地文件名
        String dst = "/home/omc/ylong/sftp/HB-SnagIt1001.rar"; // 目标文件名
              
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        
        /**
         * 代码段1
        OutputStream out = chSftp.put(dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
        byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
        int read;
        if (out != null) {
            System.out.println("Start to read input stream");
            InputStream is = new FileInputStream(src);
            do {
                read = is.read(buff, 0, buff.length);
                if (read > 0) {
                    out.write(buff, 0, read);
                }
                out.flush();
            } while (read >= 0);
            System.out.println("input stream read done.");
        }
        **/
        
        chSftp.put(src, dst, new CustomSftpProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段2
        
        // chSftp.put(new FileInputStream(src), dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE); // 代码段3
        
        chSftp.quit();
        channel.closeChannel();
    }
}
