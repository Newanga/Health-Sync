package com.team98.healthsync.sms;

public class TwillioHelper {

    public static String FormatSenderNumber(String name){
        String formattedString=name.substring(1);
        formattedString="+94"+formattedString  ;
        formattedString=formattedString.replace("-","");
        return formattedString;
    }
}
