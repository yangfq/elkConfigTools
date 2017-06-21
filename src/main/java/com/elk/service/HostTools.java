package com.elk.service;

import com.elk.impl.HostInfo;
import com.sshtools.j2ssh.SshClient;

/**
 * Created by yangfq on 2017/6/16.
 */
public interface HostTools {

    /**
     * get fire from remote host to localhost
     * @param remoteFile  the file in remote host
     * @param localFile   the file in local host
     * @return
     */
    String getFile(HostInfo hostInfo, String remoteFile, String localFile);

    boolean putFile(HostInfo hostInfo,String localFile ,String remoteFile);

    SshClient connectHost(HostInfo hostInfo);

}
