package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.exception.BadRequestException;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import com.ignacio.rudyk.generic.ecommerce.repository.IEmailTemplateRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.EmailTemplate;
import com.ignacio.rudyk.generic.ecommerce.service.IEmailSenderService;
import com.ignacio.rudyk.generic.ecommerce.util.EmailTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailSenderService implements IEmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

    private final JavaMailSender mailSender;

    private final IEmailTemplateRepository emailTemplateRepository;

    private final String fromAddress;

    public EmailSenderService(JavaMailSender mailSender,
                              IEmailTemplateRepository emailTemplateRepository,
                              @Value("${app.mail.from}") String fromAddress) {
        this.mailSender = mailSender;
        this.emailTemplateRepository = emailTemplateRepository;
        this.fromAddress = fromAddress;
    }

    @Override
    public void send(String to, String templateCode) {
        send(to, templateCode, Map.of());
    }

    @Override
    public void send(String to, String templateCode, Map<String, String> variables) {
        validateRecipient(to);
        validateTemplateCode(templateCode);

        EmailTemplate template = emailTemplateRepository.findByEmailCodeAndEnabledTrue(templateCode)
                .orElseThrow(() -> new DataNotFoundException("Template de email no encontrado o deshabilitado"));

        Map<String, String> safeVariables = variables != null ? variables : Map.of();
        String subject = EmailTemplateUtil.applyVariables(template.getEmailSubject(), safeVariables);
        String title = EmailTemplateUtil.applyVariables(template.getEmailTitle(), safeVariables);
        String message = EmailTemplateUtil.applyVariables(template.getMessage(), safeVariables);
        String htmlBody = EmailTemplateUtil.buildHtmlBody(title, message);

        try {
            mailSender.send(mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
                helper.setFrom(fromAddress);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(htmlBody, true);
            });
            LOGGER.info("Email enviado a {} usando template {}", to, templateCode);
        } catch (MailException e) {
            LOGGER.error("Error al enviar email a {} con template {}", to, templateCode, e);
            throw new EcommerceException("Error al enviar el email");
        }
    }

    private void validateRecipient(String to) {
        if (to == null || to.isBlank()) {
            throw new BadRequestException("El destinatario del email es obligatorio");
        }
    }

    private void validateTemplateCode(String templateCode) {
        if (templateCode == null || templateCode.isBlank()) {
            throw new BadRequestException("El codigo del template es obligatorio");
        }
    }

}
