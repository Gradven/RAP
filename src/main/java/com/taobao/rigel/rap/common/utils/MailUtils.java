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

	private static final String hostName = SystemConstant.getConfig("mail.host");
	private static final int port = Integer.parseInt(SystemConstant.getConfig("mail.port"));
	//private static final String sslPort = SystemConstant.getConfig("mail.sslPort");

	private static final String userName = SystemConstant.getConfig("mail.username");
	private static final String password = SystemConstant.getConfig("mail.password");
	private static final String from = SystemConstant.getConfig("mail.from");
    
    /**
     * 没有抄送，只有主送
     * @param bccAddressList
     * @param title
     * @param content
     */
    public static void apacheSendMail(String[] bccAddressArray,  String title,
            String content)
    {
    	MailUtils.apacheSendMail(bccAddressArray, null, title, content);
    }
    
        /**
     * 需要从调入方传入邮件地址
     * @param bccAddressList  
     * @param ccAddressList
     * @param title
     * @param content
     */
    public static void apacheSendMail(String[] bccAddressArray, String[] ccAddressArray, String title,
            String content)
    {
	    SimpleEmail email = new SimpleEmail();

	    email.setHostName(hostName);//设置使用发电子邮件的邮件服务

	    try {

	      for(String bccAddress:bccAddressArray)
	      {
	    	  email.addTo(bccAddress);
	      }

	      /**
	       * 如果没有抄送名单，那么就不抄送了
	       */
	      if (ccAddressArray != null)
	      {
		      for(String ccAddress:ccAddressArray)
		      {
		    	  email.addCc(ccAddress);
		      }
	    	  
	      }


	      //抄送给自己,项目组中肯定有自己，所以这段代码可以不需要了，不需要单独抄送给自己了。
	      //Map session =  ContextManager.currentSession();
	      //String  myEmail = (String)session.get(ContextManager.KEY_EMAIL);
	      //email.addCc(myEmail);

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
