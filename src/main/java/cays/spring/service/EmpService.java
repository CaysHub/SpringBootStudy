package cays.spring.service;

import cays.spring.dao.EmpDao;
import cays.spring.vo.Emp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 雇员信息服务类
 *
 * @author Chai yansheng
 * @create 2019-07-24 14:52
 **/
@Service
public class EmpService {
    @Resource
    private EmpDao empDao;

    public Emp getByEmpno(String empno) {
        return empDao.fingByEmpno(empno);
    }

    /**
     * 发送qq邮件
     * @throws AddressException
     * @throws MessagingException
     * @throws IOException
     */
    public void sendmail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.enable", "false");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("<your qq>@qq.com", "<your password>");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("<your qq>@qq.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("<your qq>@qq.com"));
        msg.setSubject("Spring Boot Cays email1");
        msg.setContent("Spring Boot Cays email2", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Spring Boot Cays email3", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("D:\\WorkSpace\\file\\3.jpg");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

}
