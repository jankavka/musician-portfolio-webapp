package cz.kavka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class ApplicationConfig {

    @Bean
    JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jankavka89@gmail.com");
        mailSender.setPassword("zfhx pedy bekp nyqc");

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    // this is a template message that we can pre-load with default state
    @Bean
    SimpleMailMessage templateMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jankavka89@gmail.com");
        message.setSubject("Forgotten Password");
        return message;
    }





}
