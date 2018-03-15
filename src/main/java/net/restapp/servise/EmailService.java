package net.restapp.servise;

import org.springframework.mail.javamail.JavaMailSender;

import java.io.File;

public interface EmailService {

    //Отправить 1 простое текстовое сообщение на указаный email
    void sendEmail(String email, String massageText);

    void sendPDF(String email, String massageText, File file);
}
