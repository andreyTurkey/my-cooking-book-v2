package com.example.application.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.application.dto.UserDto;
import com.example.application.exception.ReadPropertiesException;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Component
@Slf4j
public class RegistrationMail {

    @Value("${mail.user}")
    String login;
    @Value("${mail.password}")
    String password;

    public RegistrationMail() {
    }

    public void sendMessage(UserDto userDto) throws ReadPropertiesException, MessagingException {

        if (userDto.getEmail().isBlank()) {
            return;
        }
        final Properties props = new Properties();
        try (InputStream fis = new FileInputStream("/properties/mail.properties")) {
            props.load(fis);
        } catch (Exception e) {
            throw new ReadPropertiesException("Unable to find the specified mail.properties file");
        }

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("andrei4-09@mail.ru"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(userDto.getEmail())
            );
            message.setSubject("Регистрация на сайте my-cooking-book.ru");

            message.setText(String.format("Здравствуйте!\n" +
                    "Поздравляем с успешной регистрацией в приложении для хранения рецептов.\n" +
                    "\n"+
                    "Данные для входа:\n" +
                    "\n" +
                    "Логин: %s\n" +
                    "Пароль: %s", userDto.getName(), userDto.getPassword()));

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
