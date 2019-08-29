 /**   
 * @Title: EmailUtil.java 
 * @Package com.yn.common.util 
 * @Description: TODO
 * @author YeNing
 * @date 2019年8月27日 下午3:51:13
 */
package com.yn.common.util;

import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.mail.util.MailSSLSocketFactory;

/** 
  * @ClassName: EmailUtil 
  * @Description: 网易企业邮箱发送工具类
  * @author YN
  * @date 2019年8月27日 下午3:51:13  
  */
public class EmailUtil {
	
	@Value("${email.username}")
	private static String username;//企业邮箱
	
	@Value("${email.from}")
	private static String from;//发件人昵称展示
	
	@Value("${email.password}")
	private static String password;//企业邮箱密码 授权密码不是登录密码
	
	@Value("${email.host}")
	private static String host;//163企业邮箱smtp
	/**
	 * 
	 * @param to 接收邮箱
	 * @param subject 邮件主题
	 * @param text 邮件内容
	 */
    public static void sendHtmlMail(String[] to,String subject,String text) {
        try{
            //设置服务器验证信息
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.timeout", "994"); // 加密端口(ssl)  可通过 https://qiye.163.com/help/client-profile.html 进行查询
    
            MailSSLSocketFactory sf = new MailSSLSocketFactory();// SSL加密
            sf.setTrustAllHosts(true); // 设置信任所有的主机
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            //设置邮件内容
            JavaMailSenderImpl javaMailSend = new JavaMailSenderImpl();
            MimeMessage message = javaMailSend.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            String nick = MimeUtility.encodeText(from);//设置昵称
            messageHelper.setFrom(new InternetAddress(nick + " <"+username+">"));// 邮件发送者
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
    
            //设置邮件服务器登录信息
            javaMailSend.setHost(host);
            javaMailSend.setUsername(username);
            javaMailSend.setPassword(password);
            javaMailSend.setJavaMailProperties(prop);
            javaMailSend.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	String[] to = {"1678549524@qq.com"};
    	String subject = "邮件主题";
        String text = "邮件内容";
    	sendHtmlMail(to,subject,text);
	}
}
