package com.elk.web;

import com.alibaba.fastjson.JSONObject;
import com.elk.impl.HostInfo;
import com.elk.service.HostTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by yangfq on 2017/6/20.
 */
@Controller
public class WebControl {

    @Autowired(required=false)
    @Qualifier("hostToolsUtils")
    private HostTools hostTools;

    private static final String localFile = "./conf.txt";

    @RequestMapping("/")
    public String index(Model model){
        System.out.println("-----------------------------");
        System.out.println(System.getProperty("user.home"));
        return "configContext";
    }

    @RequestMapping("/getConfig")
    @ResponseBody
    public void getConfig(HttpServletRequest req, HttpServletResponse response){

        HostInfo hostInfo = new HostInfo();
        hostInfo.setHostIp(req.getParameter("hostIp").toString());
        hostInfo.setPort(Integer.valueOf(req.getParameter("hostPort")));
        hostInfo.setUserName(req.getParameter("userName").toString());
        hostInfo.setUserPass(req.getParameter("userPass").toString());
        hostInfo.setHostFile(req.getParameter("filePath").toString());
        hostInfo.setLocalTmpFile(localFile);

        String retStr = hostTools.getFile(hostInfo, hostInfo.getHostFile(), hostInfo.getLocalTmpFile());
        //String retStr = "123\n123";

        JSONObject responseJSONObject =   new JSONObject();
        responseJSONObject.put("retStr", retStr);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @RequestMapping("/saveConfig")
    @ResponseBody
    public void saveConfig(HttpServletRequest req, HttpServletResponse response){

        String fileCon = req.getParameter("fileCon").toString();

        HostInfo hostInfo = new HostInfo();
        hostInfo.setHostIp(req.getParameter("hostIp").toString());
        hostInfo.setPort(Integer.valueOf(req.getParameter("hostPort")));
        hostInfo.setUserName(req.getParameter("userName").toString());
        hostInfo.setUserPass(req.getParameter("userPass").toString());
        hostInfo.setHostFile(req.getParameter("filePath").toString());
        hostInfo.setLocalTmpFile(localFile);

        JSONObject responseJSONObject =   new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            String localFilePath = System.getProperty("user.dir") + "/" + localFile;
            Path path= Paths.get(localFile);
            BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class);
            BasicFileAttributes basicfile=basicview.readAttributes();

            System.out.println("filesize:"+basicfile.size());
            if("".equals(fileCon) && basicfile.size()==0)
            {
                responseJSONObject.put("retStr", "File is empty, add config context first");
            }
            else if(!"".equals(fileCon))
            {
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(localFile));
                    writer.write(fileCon);
                    writer.newLine();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally{
                    writer.flush();
                    writer.close();
                }
                //boolean retResult = hostTools.putFile(hostInfo, hostInfo.getHostFile(), hostInfo.getLocalTmpFile());
                boolean retResult = hostTools.putFile(hostInfo, hostInfo.getHostFile(), localFilePath);

                if(retResult) {
                    responseJSONObject.put("retStr", "Upload file to remote host successful!");
                }
                else
                {
                    responseJSONObject.put("retStr", "Upload file to remote host failed!");
                }
            }

            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(responseJSONObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
