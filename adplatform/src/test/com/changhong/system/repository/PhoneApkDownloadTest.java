package com.changhong.system.repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changhong.common.utils.WebUtils;
import com.changhong.system.service.AppPhoneManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class PhoneApkDownloadTest {
	//请求地址
    private static final String host = "http://localhost:8080/adplatform/download/phoneapkversion.html";
    
    private static final String host1 = "http://localhost:8080/adplatform/download/phoneappdownload.html";
    
    @Value("${application.file.upload.path}")
    private String filePath;
    
    @Autowired
    private  AppPhoneManageService appPhoneManageService;
	 
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetVersion(){
    	PostMethod postMethod = new PostMethod(host);
    	String result = WebUtils.httpPostRequest(postMethod);
    	System.out.println("返回版本信息"+result);
    }
    
    @Test
    public void testDownloadApk(){
    	HttpURLConnection httpURLConnection = null;
        URL url = null;
        BufferedInputStream bis = null;
        byte[] buf = new byte[1024];
        int size = 0;
        String fileName = "ad.apk";
        RandomAccessFile rndFile = null;
        File file = new File(filePath + "\\" +  fileName);
        long localFileSize =0;
        if (file.exists()) {                      
           localFileSize = file.length();
        } else {
            // 建立文件
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }          
        }
        
        try {
            url = new URL(host1);      
            httpURLConnection = (HttpURLConnection)url.openConnection();
            // 设置User-Agent
            httpURLConnection.setRequestProperty( "User-Agent", "Net");
            // 设置续传开始
            System. out.println( "本地文件大小：" +localFileSize);
            httpURLConnection.setRequestProperty("Range", "bytes=" + localFileSize  + "-" );
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据 
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Length", buf.length + "");
            // 获取输入流
            bis = new BufferedInputStream(httpURLConnection.getInputStream());  
            
            httpURLConnection.connect();
           
            long remoteFileSize = httpURLConnection.getContentLength();
            System.out.println("返回结果码：" +httpURLConnection.getHeaderField("Content-Disposition"));
                 if (localFileSize < remoteFileSize) {
                      if (localFileSize==0) {
                           System. out.println( "文件不存在，开始下载..." );
                     } else{
                           System. out.println( "文件续传..." );
                     }
                } else {
                     System. out.println( "文件存在，重新下载..." );
                     file.delete();
                      try {
                           file.createNewFile();
                     } catch (Exception e) {
                           e.printStackTrace();
                     }
                }
                 
                 rndFile = new RandomAccessFile(filePath+ "\\" + fileName, "rw");
                 
                 rndFile.seek(localFileSize);
                 int i = 0;
                 while ((size = bis.read(buf)) != -1) {
                     //if (i > 500) break;              
                     rndFile.write(buf, 0, size);
                     i++;
                 }
                 System. out.println( "i=" + i);
                 httpURLConnection.disconnect();
                 rndFile.close();
             } catch (Exception e) {
                 e.printStackTrace();
             }
        
    }
        
    
}
