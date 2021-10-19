package com.hnzs.controller.common;

import com.hnzs.service.common.RecruitService;
import com.hnzs.util.JSON;
import com.hnzs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/recruit")
public class RecruitController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RecruitService recruitService;
    /**
     * 开启收录接口
     */
    @RequestMapping(value = "/OpenCollection.action" )
    public String OpenCollection(){
        HashMap datamap = new HashMap();
        String result="";
        try{
            String xml1 = request.getParameter("xml");//接收的参数
            //解析对方发来的xml数据
            //获取Http Post 无参数名 获取参数值的方法
            request.setCharacterEncoding("utf-8");
            StringBuffer info=new StringBuffer();
            InputStream in=request.getInputStream();
            BufferedInputStream buf=new BufferedInputStream(in);
            byte[] buffer=new byte[1024];
            int iRead;
            while((iRead=buf.read(buffer))!=-1)
            {
                info.append(new String(buffer,0,iRead,"UTF-8"));
            }
            String str_pamas= info.toString();
            System.out.println("获取加密的参数值:"+str_pamas);
            String xml= URLDecoder.decode(str_pamas, "UTF-8");
            HashMap map= StringUtil.readStringXml(xml);
            String res=recruitService.saveOpenCollectionInfos(map);
            if(res.indexOf("成功")>0){
                //说明接收成功
                datamap.put("message", "命令执行成功");
                datamap.put("state", "0");
            }else{
                datamap.put("message", res);
                datamap.put("state", "-1");
            }


        }catch (Exception e){
            e.printStackTrace();
            datamap.put("message", e.getMessage());
            datamap.put("state", "-1");
        }
        result = JSON.Encode(datamap);



        return result;
    }

    @RequestMapping(value = "/CloseCollection.action" )
    public String CloseCollection(){
        HashMap datamap = new HashMap();
        String result="";
        try{
            String xml1 = request.getParameter("xml");//接收的参数
            //解析对方发来的xml数据
            //获取Http Post 无参数名 获取参数值的方法
            request.setCharacterEncoding("utf-8");
            StringBuffer info=new StringBuffer();
            InputStream in=request.getInputStream();
            BufferedInputStream buf=new BufferedInputStream(in);
            byte[] buffer=new byte[1024];
            int iRead;
            while((iRead=buf.read(buffer))!=-1)
            {
                info.append(new String(buffer,0,iRead,"UTF-8"));
            }
            String str_pamas= info.toString();
            System.out.println("获取加密的参数值:"+str_pamas);
            String xml= URLDecoder.decode(str_pamas, "UTF-8");
            HashMap map= StringUtil.readStringXml(xml);
            String res=recruitService.saveCloseCollectionInfos(map);
            if(res.indexOf("成功")>0){
                //说明接收成功
                datamap.put("message", "命令执行成功");
                datamap.put("state", "0");
            }else{
                datamap.put("message", res);
                datamap.put("state", "-1");
            }




        }catch (Exception e){
            e.printStackTrace();
            datamap.put("message", e.getMessage());
            datamap.put("state", "-1");
        }
        result = JSON.Encode(datamap);



        return result;
    }
}
