package com.demo.FriendManagementWebFlux.utils;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private EmailUtils(){}

    public static boolean isEmail(String email){
        var matcher = Pattern
                .compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}").matcher(email);
        return matcher.find();
    }

    public static Set<String> getEmailsFromText(String text) {
        Set<String> listEmail = new HashSet<>();
        Matcher matcher = Pattern
                .compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}").matcher(text);
        while (matcher.find()) {
            listEmail.add(matcher.group());
        }
        return listEmail;
    }

}
