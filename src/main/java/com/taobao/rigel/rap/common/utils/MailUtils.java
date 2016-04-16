package com.taobao.rigel.rap.common.utils;

import com.taobao.rigel.rap.common.config.SystemConstant;
import com.taobao.rigel.rap.common.service.impl.ContextManager;

import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MailUtils {

	private static final Logger logger = LogManager.getFormatterLogger(MailUtils.class);

	private static final String bccAddresses =   SystemConstant.getConfig("mail.bccAddresses");
	private static final String ccAddresses =   SystemConstant.getConfig("mail.ccAddresses");
	private static final String hostName = SystemConstant.getConfig("mail.host");
	private static final int port = Integer.parseInt(SystemConstant.getConfig("mail.port"));
	//private static final String sslPort = SystemConstant.getConfig("mail.sslPort");

	private static final String userName = SystemConstant.getConfig("mail.username");
	private static final String password = SystemConstant.getConfig("mail.password");
	private static final String from = SystemConstant.getConfig("mail.from");


    public static void sendMail(String title, String content)
    {
    	String[] bccAddressList = bccAddresses.split(",");

    	String[] ccAddressList = ccAddresses.split(",");

		MailUtils.apacheSendMail(bccAddressList, ccAddressList, title, content);


    }


    public static void apacheSendMail(String[] bccAddressList, String[] ccAddressList, String title,
            String content)
    {
	    SimpleEmail email = new SimpleEmail();

	    email.setHostName(hostName);//设置使用发电子邮件的邮件服务

	    try {

	      for(String bccAddress:bccAddressList)
	      {
	    	  email.addTo(bccAddress);
	      }

	      for(String ccAddress:ccAddressList)
	      {
	    	  email.addCc(ccAddress);
	      }

	      //抄送给自己
	      Map session =  ContextManager.currentSession();
	      String  myEmail = (String)session.get(ContextManager.KEY_EMAIL);
	      email.addCc(myEmail);

	      //System.out.println("=======myEmail:" + myEmail);

	      email.setAuthentication(userName, password);
	      email.setFrom(from);
	      //email.setSSLOnConnect(true);
	      //email.setSslSmtpPort(sslPort);
	      email.setSmtpPort(port);
	      email.setSubject(title);
	      email.setMsg(content);

	      email.send();
	      logger.info("邮件发送成功！");

	    }
	    catch (EmailException ex) {
	      ex.printStackTrace();
	    }
	 }




}
