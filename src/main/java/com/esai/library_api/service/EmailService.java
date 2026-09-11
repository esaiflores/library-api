package com.esai.library_api.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "Reset your Library Manager password";
        Content content = new Content("text/html",
                "<h2>Password Reset</h2>" +
                        "<p>Click the link below to reset your password. This link expires in 1 hour.</p>" +
                        "<a href='" + resetLink + "' style='background:#185FA5;color:white;padding:12px 24px;text-decoration:none;border-radius:8px;display:inline-block;margin:16px 0'>Reset Password</a>" +
                        "<p>If you didn't request this, ignore this email.</p>"
        );

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("SendGrid status: " + response.getStatusCode());
            System.out.println("SendGrid body: " + response.getBody());
        } catch (IOException e) {
            System.out.println("SendGrid error: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}