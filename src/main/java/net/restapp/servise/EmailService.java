package net.restapp.servise;

import java.io.File;

public interface EmailService {

    /**
     * Send email
     * @param email - email address for sending
     * @param title - email title
     * @param massageText - letter body
     */
    void sendEmail(String email, String title, String massageText);

    /**
     * Send email with PDF file
     * @param email - email address for sending
     * @param title - email title
     * @param massageText - letter body
     * @param file - pdf file
     */
    void sendPDF(String email, String title, String massageText, File file);
}
