package com.pm490.PM490.service.implementation;

import com.pm490.PM490.dto.EmailDto;
import com.pm490.PM490.service.EmailService;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;


@Component
public class EmailServiceImpl implements EmailService {

    public void sendEmail(EmailDto email)
    {
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("bolt.iowa@gmail.com", "1qaz@WSX3edc");
                }
            });
            Message msg = new MimeMessage(session);


            msg.setFrom(new InternetAddress(email.getFromAddress(), false));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email.getToAddress()));
            msg.setSubject(email.getMailSubject());
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(email.getBodyText(),
                    "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

//            MimeBodyPart attachPart = new MimeBodyPart();
//            attachPart.attachFile(email.getAttachFileAddress());
            // Part two is attachment
            MimeBodyPart attachPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(email.getAttachFileAddress());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(email.getAttachFileAddress());
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);
            Transport.send(msg);
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
    }
}


