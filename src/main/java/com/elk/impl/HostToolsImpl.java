package com.elk.impl;

import com.elk.service.HostTools;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by yangfq on 2017/6/16.
 */
@Service
public class HostToolsImpl implements HostTools {

    @Override
    public String getFile(HostInfo hostInfo, String remoteFile, String localFile)
    {
        StringBuilder retStr = new StringBuilder();
        SshClient client = connectHost(hostInfo);

        if (client != null) {
            try {
                OutputStream localOS = new FileOutputStream(localFile);
                client.openSftpClient().get(remoteFile, localOS);

                //read file after get file from remote host and put file in local dir
                File file = new File(localFile);
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String tempString = null;

                    while ((tempString = reader.readLine()) != null) {
                        retStr.append(tempString).append("\n");
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        client.disconnect();
        return retStr.toString();
    }

    @Override
    public boolean putFile(HostInfo hostInfo,String localFile ,String remoteFile) {
        boolean ret = true;
        SshClient client = connectHost(hostInfo);
        if (client != null) {
            try {
                client.openSftpClient().put(localFile, remoteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.disconnect();
        }
        else
        {
            ret = false;
        }

        return ret;
    }

    @Override
    public SshClient connectHost(HostInfo hostInfo) {
        SshClient client = new SshClient();

        try {
            ConsoleKnownHostsKeyVerification console = new ConsoleKnownHostsKeyVerification();
            client.connect(hostInfo.getHostIp(), hostInfo.getPort(), console);

            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername(hostInfo.getUserName());
            pwd.setPassword(hostInfo.getUserPass());

            int result = client.authenticate(pwd);
            if (result == AuthenticationProtocolState.COMPLETE) {
                System.out.println("Connect host successful！");
            } else {
                client = null;
            }
            return client;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }
}
