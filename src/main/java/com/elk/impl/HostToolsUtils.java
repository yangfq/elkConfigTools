package com.elk.impl;

import com.elk.service.HostTools;
import com.sshtools.j2ssh.SshClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by yangfq on 2017/6/16.
 */
@Service
public class HostToolsUtils implements HostTools{

    @Autowired(required=false)
    @Qualifier("hostToolsImpl")
    private HostTools hostTools;

    @Override
    public String getFile(HostInfo hostInfo,String remoteFile, String localFile)
    {
        return hostTools.getFile(hostInfo, remoteFile, localFile);
    }

    @Override
    public boolean putFile(HostInfo hostInfo,String localFile ,String remoteFile)
    {
        return hostTools.putFile(hostInfo, remoteFile, localFile);
    }

    @Override
    public SshClient connectHost(HostInfo hostInfo)
    {
        return hostTools.connectHost(hostInfo);
    }
}
