package com.mindhub.homebanking.service;

import java.io.File;

public interface EmailService {

//    public boolean sendEmail(EmailBody emailBody);

    public boolean sendEmailTool(String textMessage, String email, String subject);

}
