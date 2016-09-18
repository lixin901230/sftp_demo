package com.lx.sftp;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 编写一个工具类，根据ip，用户名及密码得到一个SFTP channel对象，即ChannelSftp的实例对象，在应用程序中就可以使用该对象来调用SFTP的各种操作方法。
 *
 */
public class SFTPChannel {
	
    Session session = null;
    Channel channel = null;

    private static final Logger logger = Logger.getLogger(SFTPChannel.class.getName());

    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {

        String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);

        int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        logger.debug(">>>>>>Session created.");
        
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put(">>>>>>StrictHostKeyChecking", "no");
        
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        logger.debug(">>>>>>Session connected.");

        logger.debug(">>>>>>Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        
        logger.debug(">>>>>>Connected successfully to ftpHost = " + ftpHost 
        		+ ",as ftpUserName = " + ftpUserName + ", returning: " + channel);
        
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}
