package com.aacid0.ntf_library.infrastructure.adapters.email;

import com.aacid0.ntf_library.domain.exceptions.NotSupportNotificationException;
import com.aacid0.ntf_library.domain.exceptions.email.delivery.EmailDeliveryException;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.EmailContent;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;
import com.aacid0.ntf_library.infrastructure.configuration.email.SendGridConfig;

public class SendGridEmailAdapter implements NotificationProvider {

    private final SendGridConfig config;

    public SendGridEmailAdapter(SendGridConfig config) {
        this.config = config;
    }

    @Override
    public void send(Notification notification) {
        if (!supports(notification)) {
            throw new NotSupportNotificationException("El tipo de notificación no es soportado por este proveedor.");
        }

        try {
            EmailContent emailContent = (EmailContent) notification.getContent();
            String url = "https://api.sendgrid.com/v3/mail/send";

            boolean isHtml = emailContent.getBodyHtml() != null && !emailContent.getBodyHtml().isBlank();
            String contentType = isHtml ? "text/html" : "text/plain";
            String contentValue = isHtml ? emailContent.getBodyHtml() : emailContent.getBodyText();

            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("{");

            bodyBuilder.append(
                    "\"personalizations\": [{\"to\": [{\"email\": \"" + emailContent.getRecipient() + "\"}]}],");

            bodyBuilder.append("\"from\": {\"email\": \"" + config.getFromEmail() + "\", \"name\": \""
                    + config.getFromName() + "\"},");

            bodyBuilder.append("\"subject\": \"" + emailContent.getSubject() + "\",");

            bodyBuilder.append("\"content\": [{\"type\": \"" + contentType + "\", \"value\": \""
                    + escapeJson(contentValue) + "\"}]");

            bodyBuilder.append("}");

            // SIMULACIÓN DEL ENVÍO
            System.out.println("\n========== [SENDGRID ADAPTER OUTBOUND] ==========");
            System.out.println("Endpoint: POST " + url);
            System.out.println("Headers: ");
            System.out.println("  Authorization: Bearer " + config.getApiKey());
            System.out.println("  Content-Type: application/json");
            System.out.println("Payload: ");
            System.out.println("  " + bodyBuilder.toString());
            System.out.println("Status: 202 ACCEPTED (Simulated)");
            System.out.println("===============================================\n");

        } catch (Exception e) {
            throw new EmailDeliveryException("Error al simular envío con SendGrid", e);
        }
    }

    private String escapeJson(String raw) {
        if (raw == null)
            return "";
        return raw.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    @Override
    public boolean supports(Notification notification) {
        return notification.getContent() instanceof EmailContent;
    }
}
