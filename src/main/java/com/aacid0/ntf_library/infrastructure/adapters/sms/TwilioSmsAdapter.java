package com.aacid0.ntf_library.infrastructure.adapters.sms;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.aacid0.ntf_library.domain.exceptions.NotSupportNotificationException;
import com.aacid0.ntf_library.domain.exceptions.sms.delivery.SmsDeliveryException;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.SmsContent;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;
import com.aacid0.ntf_library.infrastructure.configuration.sms.TwilioConfig;

public class TwilioSmsAdapter implements NotificationProvider {

    private final TwilioConfig config;

    public TwilioSmsAdapter(TwilioConfig config) {
        this.config = config;
    }

    @Override
    public void send(Notification notification) {
        if (!supports(notification)) {
            throw new NotSupportNotificationException("El tipo de notificación no es soportado por este proveedor.");
        }

        try {
            SmsContent smsContent = (SmsContent) notification.getContent();

            String url = String.format(
                    "https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json",
                    config.getAccountSid());

            String auth = config.getAccountSid() + ":" + config.getAuthToken();
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            StringBuilder bodyBuilder = new StringBuilder();

            bodyBuilder.append("To=").append(smsContent.getRecipientPhone());

            bodyBuilder.append("&Body=").append(smsContent.getBody());

            String senderToUse = (smsContent.getSenderPhone() != null) ? smsContent.getSenderPhone()
                    : config.getDefaultFrom();

            if (senderToUse != null) {
                if (senderToUse.startsWith("MG")) {
                    bodyBuilder.append("&MessagingServiceSid=").append(senderToUse);
                } else {
                    bodyBuilder.append("&From=").append(senderToUse);
                }
            }

            // SIMULACIÓN DEL ENVÍO
            System.out.println("\n========== [TWILIO ADAPTER OUTBOUND] ==========");
            System.out.println("Endpoint: POST " + url);
            System.out.println("Headers: ");
            System.out.println("  Authorization: Basic " + encodedAuth);
            System.out.println("  Content-Type: application/x-www-form-urlencoded");
            System.out.println("Payload: ");
            System.out.println("  " + bodyBuilder.toString());
            System.out.println("Status: 201 CREATED (Simulated)");
            System.out.println("==============================================\n");

        } catch (Exception e) {
            throw new SmsDeliveryException("Error al enviar la notificación SMS con Twilio.", e);
        }

    }

    @Override
    public boolean supports(Notification notification) {
        return notification.getContent() instanceof SmsContent;
    }

}
