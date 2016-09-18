package com.lx.sftp.monitor;

import com.jcraft.jsch.SftpProgressMonitor;

/**
 * 对传输进度的监控。
 * 可以实现JSch提供的SftpProgressMonitor接口来完成这个功能
 *
 */
public class CustomSftpProgressMonitor implements SftpProgressMonitor {
    private long transfered;

    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        System.out.println("Currently transferred total size: " + transfered + " bytes");
        return true;
    }

    @Override
    public void end() {
        System.out.println("Transferring done.");
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        System.out.println("Transferring begin.");
    }
}