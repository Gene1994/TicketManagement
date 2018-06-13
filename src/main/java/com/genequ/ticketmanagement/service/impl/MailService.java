//公司内网不支持org.springframework.mail，暂时注掉
//package com.genequ.ticketmanagement.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.internet.MimeMessage;
//
//@Slf4j
//@Service
//public class MailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Value("${mail.fromMail.addr}")
//    private String from;
//
//    public void sendSimpleMail(String to, String subject, String content) {
//        try {
//            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
//            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//            message.setFrom(from);
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(content);
//            this.mailSender.send(mimeMessage);
//        } catch (Exception e) {
//        }
//    }
//}
