package net.restapp.Utils;

/**
 * Messages for emails
 */
public class LettersExample {

    /**
     * create messa for welcome letter
     * @param url - domain url
     * @return - message
     */
    public String createWelcomeMessage(String url) {

        return "Dear, employee.\n " +
                "Congratulations you with authorization in HRManager. \n" +
                "You can access the data by using your email as login. \n\n" +
                "We strongly recommend changing the password. To do this, follow the link "+
                url + "/employees/user/pass";
    }




}
