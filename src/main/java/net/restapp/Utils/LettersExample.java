package net.restapp.Utils;


public class LettersExample {


    public String createWelcomeMessage(String url) {

        return "Dear, employee.\n " +
                "Congratulations you with authorization in HRManager. \n" +
                "You can access the data by using your email as login. \n\n" +
                "We strongly recommend changing the password. To do this, follow the link "+
                url + "/employees/user/pass";
    }




}
