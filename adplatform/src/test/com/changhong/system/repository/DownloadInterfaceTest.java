package com.changhong.system.repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HostParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.web.controller.HandleResponse;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.WebUtils;
import com.changhong.common.utils.excel.poi.tools.DateUtils;


/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-4-5 
 * Time: 11:18:48
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml","/applicationContext.xml"})
public class DownloadInterfaceTest extends TestCase{
	private Logger log = LoggerFactory.getLogger(DownloadInterfaceTest.class);
	
	private static final String HOST = "http://localhost:8080/adplatform/download";
	
	//@Test
	public void testPhoneAdDownload() throws IOException{
		String html = "/phoneaddownload.html";
		String uuid = "201703011455391503514";
		String filePath = "d:/";
		String fileName = uuid+".jpg";
		RandomAccessFile randomAccessFile = null;
		BufferedInputStream bufferedInputStream = null;
		byte[] buffer = new byte[512];
		
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, "11:11:11:11:11:20");
		
		JSONObject body = new JSONObject();
		body.put("uuid", "201703011455391503514");
		
		request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));
		
		
		long downloadedSize = 0;
		File f = new File(filePath+fileName);
		 if (!f.getParentFile().exists()) {
             f.getParentFile().mkdirs();
         }
         if (f.exists()) {
             downloadedSize = f.length();
             log.info("file is existed, downloadedSize = " + downloadedSize);
         } else {
             log.info("file not exists, create file : " + f.getAbsolutePath());
             f.createNewFile();
         }

         PostMethod postMethod = new PostMethod(HOST+html);
		//以(Range : bytes=0-)开始 
		postMethod.setRequestHeader("Range", "bytes="+downloadedSize+"-");
		postMethod.addParameter("json", request.toJSONString());
		
		System.out.println("json: "+ request.toJSONString());
		HttpClient httpClient = new HttpClient();
		httpClient.setConnectionTimeout(1000*60);
		
		try {
			httpClient.executeMethod(postMethod);
			int code = postMethod.getStatusCode();
			long len = postMethod.getResponseContentLength();
			System.out.println("status code = "+code);
			System.out.println("length = "+len);

			Header [] headers = postMethod.getResponseHeaders();
			for(Header h: headers){
				System.out.println(h.getName()+":"+h.getValue());
			}
			if(code==200 && len>0){
				InputStream is = postMethod.getResponseBodyAsStream();
				bufferedInputStream = new BufferedInputStream(is);
				randomAccessFile = new RandomAccessFile(f, "rwd");
				randomAccessFile.seek(downloadedSize);
				int readSize = 0;
				 while ((readSize = bufferedInputStream.read(buffer)) != -1) {
		                randomAccessFile.write(buffer, 0, readSize);
		            } 
			}
			
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(randomAccessFile!=null){
			randomAccessFile.close();
			randomAccessFile = null;
		}
		if(postMethod!=null){
			postMethod.releaseConnection();
		}

	}
	
	//@Test
	public void testSTBAdDownload() throws IOException{
		String html = "/addownload.html";
		String uuid = "201703011501496918828";
		String filePath = "d:/";
		String fileName = uuid+".jpeg";
		String asyncCode = "x0a4";
		RandomAccessFile randomAccessFile = null;
		BufferedInputStream bufferedInputStream = null;
		byte[] buffer = new byte[512];
		
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, "66:B6:66:FA:44:83");
		
		JSONObject body = new JSONObject();
		body.put("uuid", uuid);
		
		request.put(HandleResponse.REQUEST_BODY, AesUtils.randomEncrypt(body.toJSONString(), asyncCode));
		
		
		long downloadedSize = 0;
		File f = new File(filePath+fileName);
		 if (!f.getParentFile().exists()) {
             f.getParentFile().mkdirs();
         }
         if (f.exists()) {
             downloadedSize = f.length();
             log.info("file is existed, downloadedSize = " + downloadedSize);
         } else {
             log.info("file not exists, create file : " + f.getAbsolutePath());
             f.createNewFile();
         }

         PostMethod postMethod = new PostMethod(HOST+html);
		//以(Range : bytes=0-)开始 
		postMethod.setRequestHeader("Range", "bytes="+downloadedSize+"-");
		postMethod.addParameter("json", request.toJSONString());
		
		System.out.println("json: "+ request.toJSONString());
		HttpClient httpClient = new HttpClient();
		httpClient.setConnectionTimeout(1000*60);
		
		try {
			httpClient.executeMethod(postMethod);
			int code = postMethod.getStatusCode();
			long len = postMethod.getResponseContentLength();
			System.out.println("status code = "+code);
			System.out.println("length = "+len);

			Header [] headers = postMethod.getResponseHeaders();
			for(Header h: headers){
				System.out.println(h.getName()+":"+h.getValue());
			}
			if(code==200 && len>0){
				InputStream is = postMethod.getResponseBodyAsStream();
				bufferedInputStream = new BufferedInputStream(is);
				randomAccessFile = new RandomAccessFile(f, "rwd");
				randomAccessFile.seek(downloadedSize);
				int readSize = 0;
				 while ((readSize = bufferedInputStream.read(buffer)) != -1) {
		                randomAccessFile.write(buffer, 0, readSize);
		            } 
			}
			
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(randomAccessFile!=null){
			randomAccessFile.close();
			randomAccessFile = null;
		}
		if(postMethod!=null){
			postMethod.releaseConnection();
		}

	}
	
	//@Test
	public void testPhoneADConfigDownload(){
		String html = "/phoneconfigdownload.html";
		String mac = "66:B6:66:FA:44:83";
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, mac);
		
		PostMethod postMethod = new PostMethod(HOST+html);
		postMethod.addParameter("json", request.toJSONString());
		System.out.println("请求参数：\n"+request.toJSONString());
		String result = WebUtils.httpPostRequest(postMethod);
		
		JSONObject resultJSON = JSONObject.parseObject(result);
		System.out.println("result: \n"+resultJSON.toJSONString());
		String body = resultJSON.getString("body");
		if(StringUtils.hasText(body)){
			JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(resultJSON.getString("body")));
			System.out.println("解密后：\n"+responseBody.toJSONString());
			
			JSONObject b = new JSONObject();
			b.put("body", responseBody);
			System.out.println(b.toJSONString());
		}
		
	}
	
	@Test
	public void testSTBADConfigDownload(){
		String html = "/configdownload.html";
		String mac = "66:B6:66:FA:44:83";
		String asyncCode = "x0a4";
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, mac);
		
		PostMethod postMethod = new PostMethod(HOST+html);
		postMethod.addParameter("json", request.toJSONString());
		System.out.println("请求参数：\n"+request.toJSONString());
		String result = WebUtils.httpPostRequest(postMethod);
		
		JSONObject resultJSON = JSONObject.parseObject(result);
		System.out.println("result: \n"+resultJSON.toJSONString());
		String body = resultJSON.getString("body");
		if(StringUtils.hasText(body)){
			JSONObject responseBody = JSONObject.parseObject(AesUtils.randomDecrypt(resultJSON.getString("body"), asyncCode));
			System.out.println("解密后：\n"+responseBody.toJSONString());
			
			JSONObject b = new JSONObject();
			b.put("body", responseBody);
			System.out.println(b.toJSONString());
		}
		
	}
	
	//@Test
	public void testSTBapkVersionAcquire(){
		String html = "/apkversionacquire.html";
		String mac = "66:B6:66:FA:44:83";
		String asyncCode = "x0a4";
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, mac);
		
		PostMethod postMethod = new PostMethod(HOST+html);
		postMethod.addParameter("json", request.toJSONString());
		System.out.println("请求参数：\n"+request.toJSONString());
		String result = WebUtils.httpPostRequest(postMethod);
		
		JSONObject resultJSON = JSONObject.parseObject(result);
		System.out.println("result: \n"+resultJSON.toJSONString());
		String body = resultJSON.getString("body");
		if(StringUtils.hasText(body)){
			JSONObject responseBody = JSONObject.parseObject(AesUtils.randomDecrypt(resultJSON.getString("body"), asyncCode));
			System.out.println("解密后：\n"+responseBody.toJSONString());
			
			JSONObject b = new JSONObject();
			b.put("body", responseBody);
			System.out.println(b.toJSONString());
		}
	}
	
	//@Test
	public void testSTBapkDownload() throws IOException{
		String html = "/apkdownload.html";
		String apk_version = "2.0";
		String filePath = "d:/";
		String fileName = "stbClient_"+apk_version+".apk";
		String asyncCode = "x0a4";
		RandomAccessFile randomAccessFile = null;
		BufferedInputStream bufferedInputStream = null;
		byte[] buffer = new byte[512];
		
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, "66:B6:66:FA:44:83");
		
		JSONObject body = new JSONObject();
		body.put("apk_version", apk_version);
		
		request.put(HandleResponse.REQUEST_BODY, AesUtils.randomEncrypt(body.toJSONString(), asyncCode));
		
		
		long downloadedSize = 0;
		File f = new File(filePath+fileName);
		 if (!f.getParentFile().exists()) {
             f.getParentFile().mkdirs();
         }
         if (f.exists()) {
             downloadedSize = f.length();
             log.info("file is existed, downloadedSize = " + downloadedSize);
         } else {
             log.info("file not exists, create file : " + f.getAbsolutePath());
             f.createNewFile();
         }

         PostMethod postMethod = new PostMethod(HOST+html);
		//以(Range : bytes=0-)开始 
		postMethod.setRequestHeader("Range", "bytes="+downloadedSize+"-");
		postMethod.addParameter("json", request.toJSONString());
		
		System.out.println("json: "+ request.toJSONString());
		HttpClient httpClient = new HttpClient();
		httpClient.setConnectionTimeout(1000*60);
		
		try {
			httpClient.executeMethod(postMethod);
			int code = postMethod.getStatusCode();
			long len = postMethod.getResponseContentLength();
			System.out.println("status code = "+code);
			System.out.println("length = "+len);

			Header [] headers = postMethod.getResponseHeaders();
			for(Header h: headers){
				System.out.println(h.getName()+":"+h.getValue());
			}
			if(code==200 && len>0){
				InputStream is = postMethod.getResponseBodyAsStream();
				bufferedInputStream = new BufferedInputStream(is);
				randomAccessFile = new RandomAccessFile(f, "rwd");
				randomAccessFile.seek(downloadedSize);
				int readSize = 0;
				 while ((readSize = bufferedInputStream.read(buffer)) != -1) {
		                randomAccessFile.write(buffer, 0, readSize);
		            } 
			}
			
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(randomAccessFile!=null){
			randomAccessFile.close();
			randomAccessFile = null;
		}
		if(postMethod!=null){
			postMethod.releaseConnection();
		}
		
	}
	
	//@Test
	public void testSTBdataUpload(){
		String html = "http://localhost:8080/adplatform/upload/uploadData.html";
		String mac = "66:B6:66:FA:44:83";
		String asyncCode = "x0a4";
		JSONObject request = new JSONObject();
		request.put(HandleResponse.APP_TYPE,"ANDROID");
		request.put(HandleResponse.APP_VERSION, "1.0");
		request.put(HandleResponse.REQUEST_MAC, mac);
		
		JSONObject uploadObj = new JSONObject();
		uploadObj.put("date", DateUtils.format(new Date(), "yyyy-MM-dd"));
		JSONArray uploadItems = new JSONArray();
			JSONObject uploadItem = new JSONObject();
			uploadItem.put("uuid", "20160525130243444334");
			uploadItem.put("times", "50");
			uploadItem.put("duration", "2");
			
			JSONObject uploadItem2 = new JSONObject();
			uploadItem2.put("uuid", "20160525131043444335");
			uploadItem2.put("times", "50");
			uploadItem2.put("duration", "2");
		uploadItems.add(uploadItem);
		uploadItems.add(uploadItem2);	
		uploadObj.put("playList", uploadItems);		
		request.put(HandleResponse.REQUEST_BODY, AesUtils.randomEncrypt(uploadObj.toJSONString(), asyncCode));
		
		PostMethod postMethod = new PostMethod(html);
		postMethod.addParameter("json", request.toJSONString());
		System.out.println("请求参数：\n"+request.toJSONString()+"\n请求参数 body:\n"+uploadObj.toJSONString());
		String result = WebUtils.httpPostRequest(postMethod);
		
		JSONObject resultJSON = JSONObject.parseObject(result);
		System.out.println("result: \n"+resultJSON.toJSONString());
	}
	
}
