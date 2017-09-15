package com.resume.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;

public class MailUtil {

	/**
	 * 发送邮件
	 * @param from
	 * @param to
	 * @param title
	 * @param content
	 */
	public static void send(MailUser fromUser, String to, String title, String content) {
		String[] tos = { to };
		send(fromUser, tos, title, content);
	}
	
	public static void send(MailUser fromUser, String to, String title, String content,List<MailAffix> affixes) {
		String[] tos = { to };
		send(fromUser, tos, title, content,affixes);
	}

	/**
	 * 发送邮件
	 * @param from
	 * @param tos
	 * @param title
	 * @param content
	 */
	public static void send(MailUser fromUser, String[] tos, String title, String content) {
		send(fromUser, tos, null, title, content,null);
	}
	
	public static void send(MailUser fromUser, String[] tos, String title, String content,List<MailAffix> affixes) {
		send(fromUser, tos, null, title, content,affixes);
	}
	
	public static void send(MailUser fromUser, String[] tos,String[] copyTos, String title, String content) {
		send(fromUser, tos, copyTos, title, content,null);
	}

	/**
	 * 发送邮件
	 * @param from
	 * @param tos
	 * @param copyTos 抄送
	 * @param title
	 * @param content
	 */
	public static void send(MailUser fromUser, String[] tos, String[] copyTos, String title, String content,List<MailAffix> affixes) {
		Properties props = new Properties();
		
		//检测是否为smtp.gmail.com  
		if(fromUser.getHost().indexOf("smtp.gmail.com") >=0 ){  
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
			props.setProperty("mail.smtp.socketFactory.fallback", "false");   
			props.setProperty("mail.smtp.port", "465");   
			props.setProperty("mail.smtp.socketFactory.port", "465");  
		} 
		props.put("mail.smtp.host", fromUser.getHost());// 设置发送邮件的邮件服务器的属性
		props.put("mail.smtp.auth", "true");// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证

		Session session = Session.getDefaultInstance(props);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			String from = fromUser.getUser();
			message.setFrom(new InternetAddress(from.replace(";", "").replace("；", "").trim()));// 加载发件人地址

			// 加载收件人地址
			List<InternetAddress> addsTo = new ArrayList<InternetAddress>();
			for (String to : tos) {
				if (StringUtils.isNotBlank(to)) {
					addsTo.add(new InternetAddress(to.replace(";", "").replace("；", "").trim()));
				}
			}
			message.addRecipients(Message.RecipientType.TO, addsTo.toArray(new InternetAddress[addsTo.size()]));

			// 加载抄送人地址
			if (copyTos != null && copyTos.length != 0) {
				List<InternetAddress> addsCC = new ArrayList<InternetAddress>();
				for (String copyTo : copyTos) {
					if (StringUtils.isNotBlank(copyTo)) {
						addsCC.add(new InternetAddress(copyTo.replace(";", "").replace("；", "").trim()));
					}
				}
				message.addRecipients(Message.RecipientType.CC, addsCC.toArray((new InternetAddress[addsCC.size()])));
			}

			message.setSubject(title);// 加载标题

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);

			// 添加附件
			if (affixes != null && !affixes.isEmpty()) {
				
				for (MailAffix affix : affixes) {
					BodyPart messageBodyPart = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(affix.getAffixInput(), "application/pdf");
					// 添加附件的内容
					messageBodyPart.setDataHandler(new DataHandler(source));
					// 添加附件的标题(处理中文)
					if (StringUtils.isBlank(affix.getAffixName())) {
						affix.setAffixName("附件");
					}
					messageBodyPart.setFileName(MimeUtility.encodeText(affix.getAffixName()));
					multipart.addBodyPart(messageBodyPart);
				}
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(fromUser.getHost(), fromUser.getUser(), fromUser.getPwd());
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}


