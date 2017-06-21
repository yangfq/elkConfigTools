package com.elk.impl;

/**
 * Created by yangfq on 2017/6/16.
 */
public class HostInfo {

    private String hostIp;
    private String userName;
    private String userPass;
    private String hostFile;
    private String localTmpFile;
    private int    port;

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getHostFile() {
        return hostFile;
    }

    public void setHostFile(String hostFile) {
        this.hostFile = hostFile;
    }

    public String getLocalTmpFile() {
        return localTmpFile;
    }

    public void setLocalTmpFile(String localTmpFile) {
        this.localTmpFile = localTmpFile;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "hostIp='" + hostIp + '\'' +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", hostFile='" + hostFile + '\'' +
                ", localTmpFile='" + localTmpFile + '\'' +
                ", port=" + port +
                '}';
    }
}
