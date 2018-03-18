package net.restapp.servise.impl;

import net.restapp.servise.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * The service's layer of application for email
 */

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    /**
     * Send email
     *
     * @param email       - e-mail address of the recipient
     * @param massageText - text message
     */
    @Override
    public void sendEmail(String email, String title, String massageText) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            prepareEmail(email, title, massageText, helper);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }

    /**
     * Prepare email
     */
    private void prepareEmail(String email, String title, String massageText, MimeMessageHelper helper) throws MessagingException {
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText("<html><body>" + massageText + "<iframe src='cid:id101'></ifarme>/<body></html>", true);
    }

    /**
     * Send email with PDF file
     *
     * @param email       - e-mail address of the recipient
     * @param massageText - letter body
     * @param file        - pdf file
     */
    @Override
    public void sendPDF(String email, String title, String massageText, File file) {
        MimeMessage message = sender.createMimeMessage();

        // Enable the multipart flag!
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            prepareEmail(email,title,massageText,helper);
            helper.addInline("id101", file);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }


}
