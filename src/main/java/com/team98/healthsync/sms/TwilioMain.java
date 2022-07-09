package com.team98.healthsync.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class TwilioMain {

    //These should be non-test credentials
    private static String  ACCOUNT_SID ="ACec655c4c6c2189e30d3a585ffececd62";

    private static String AUTH_TOKEN ="9819e6a73eae7703349aaf200e2ad9cc";

    private String phoneNo;
    private String messagebody;
    private boolean isSuccess = false;

    public TwilioMain(String phoneNo, String messagebody) {
        this.phoneNo = phoneNo;
        this.messagebody = messagebody;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Boolean SendMessage() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                //Format ("<+94><xxxxxxxxx>")
                new com.twilio.type.PhoneNumber(phoneNo),
                //From number(Trial account number)
                new com.twilio.type.PhoneNumber("+14092482448"), messagebody
        ).create();

        String msgSid = message.getSid();

        if (msgSid.contains("SM"))
            isSuccess = true;
        else
            isSuccess = false;

        return isSuccess;
    }
}
