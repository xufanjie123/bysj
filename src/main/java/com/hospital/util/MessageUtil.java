package com.hospital.util;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class MessageUtil {
	public static void sendMsg(String num,String text) throws HttpException, IOException{
		HttpClient client = new HttpClient();  
        PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn");  
        post.addRequestHeader("Content-Type",  
                "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码  
        NameValuePair[] data = { new NameValuePair("Uid", "xufanjie1"),  
                new NameValuePair("Key", "171430282b90e0c915bc"),  
                new NameValuePair("smsMob", num),  
                new NameValuePair("smsText", text) };  
        post.setRequestBody(data);  
        client.executeMethod(post);  
        Header[] headers = post.getResponseHeaders();  
        int statusCode = post.getStatusCode();  
        System.out.println("statusCode:" + statusCode);  
        for (Header h : headers) {  
            System.out.println(h.toString());  
        }  
        String result = new String(post.getResponseBodyAsString().getBytes(  
                "utf-8"));  
        System.out.println(result);  
  
        post.releaseConnection();  
	}
}
