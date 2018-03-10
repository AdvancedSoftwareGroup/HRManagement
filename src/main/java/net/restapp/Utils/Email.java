package net.restapp.Utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/*
    В контролле который использует это методы создайте
    JavaMailSender с пометкой @Autowired и передавайте сюда.

    Пример:

    @Controller
    public class TestController {

    @Autowired
    private JavaMailSender sender;

    Вызов этого метода для отправки сообщения

     */

public class Email {

    //Отправить 1 простое текстовое сообщение на указаный email
    public void sendEmail( JavaMailSender sender, String email, String messageText) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(messageText);
            helper.setSubject("AdvansedSoftWareGroupInfo"); //Тема сообщения
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    //Отправляет уже созданый документ на почту
    public void sendPDF(JavaMailSender sender, String email, String massageText, File file) {
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

