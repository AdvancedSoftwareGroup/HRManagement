package net.restapp.servise.impl;

import net.restapp.servise.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

public class EmailServiseImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    //Отправить 1 простое текстовое сообщение на указаный email
    @Override
    public void sendEmail(String email, String massageText) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(massageText);
            helper.setSubject("AdvansedSoftWareGroupInfo"); //Тема сообщения
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    //Отправляет уже созданый документ на почту
    @Override
    public void sendPDF(String email, String massageText, File file) {
        MimeMessage message = sender.createMimeMessage();

        // Enable the multipart flag!
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("AdvancedSoftwareGroup Info");

            helper.setText("<html><body>" + massageText + "<iframe src='cid:id101'></ifarme>/<body></html>", true);
            helper.addInline("id101", file);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }

}
