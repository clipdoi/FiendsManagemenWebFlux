package com.demo.FriendManagementWebFlux.utils;


import java.util.regex.Pattern;

public class EmailUtils {

    private EmailUtils(){}

    public static boolean isEmail(String email){
        var matcher = Pattern
                .compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}").matcher(email);
        return matcher.find();
    }

}
