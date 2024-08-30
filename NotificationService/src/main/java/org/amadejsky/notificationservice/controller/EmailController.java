package org.amadejsky.notificationservice.controller;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.amadejsky.notificationservice.dto.EmailDto;
import org.amadejsky.notificationservice.service.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class EmailController {

    private final EmailSender emailSender;

    public EmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDto emailDto){
        try {
            emailSender.sendEmail(emailDto);
        } catch (MessagingException e) {
            log.error("Email to "+ emailDto.getTo() +"cannot be sent! ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email to "+ emailDto.getTo() +"cannot be sent! ");
        }
        return ResponseEntity.ok("Email has been sent "+emailDto.getTo());
    }
}
