package com.aacid0.ntf_library;

import java.util.HashMap;
import java.util.Map;

import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.EmailContent;
import com.aacid0.ntf_library.domain.model.content.PushContent;
import com.aacid0.ntf_library.domain.model.content.SmsContent;
import com.aacid0.ntf_library.infrastructure.adapters.email.SendGridEmailAdapter;
import com.aacid0.ntf_library.infrastructure.adapters.push.FirebasePushAdapter;
import com.aacid0.ntf_library.infrastructure.adapters.sms.TwilioSmsAdapter;
import com.aacid0.ntf_library.infrastructure.configuration.email.SendGridConfig;
import com.aacid0.ntf_library.infrastructure.configuration.push.FirebaseConfig;
import com.aacid0.ntf_library.infrastructure.configuration.sms.TwilioConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 INICIANDO PRUEBA DE INTEGRACIÓN...\n");

        // ---------------------------------------------------------
        // 1. CONFIGURACIÓN (Simulando datos de entorno)
        // ---------------------------------------------------------
        TwilioConfig twilioConfig = TwilioConfig.builder()
                .accountSid("AC_FAKE_ACCOUNT_123")
                .authToken("AUTH_TOKEN_SECRET")
                .defaultFrom("+15005550006")
                .build();

        SendGridConfig sendGridConfig = SendGridConfig.builder()
                .apiKey("SG.FAKE_KEY_123456789")
                .fromEmail("no-reply@miempresa.com")
                .fromName("Soporte IT")
                .build();

        FirebaseConfig firebaseConfig = FirebaseConfig.builder()
                .projectId("mi-super-app-movil")
                .accessToken("ya29.a0AfH6S...")
                .build();

        // ---------------------------------------------------------
        // 2. INICIALIZACIÓN DE LA LIBRERÍA
        // ---------------------------------------------------------
        NotificationLibrary library = new NotificationLibrary.NotificationLibraryBuilder()
                .registerProvider(new TwilioSmsAdapter(twilioConfig))
                .registerProvider(new SendGridEmailAdapter(sendGridConfig))
                .registerProvider(new FirebasePushAdapter(firebaseConfig))
                .build();

        // ---------------------------------------------------------
        // 3. PRUEBA DE FUEGO: ENVÍO DE NOTIFICACIONES
        // ---------------------------------------------------------

        try {
            // CASO A: Enviar SMS (Twilio)
            System.out.println(">>> Enviando SMS...");
            SmsContent sms = SmsContent.builder()
                    .recipientPhone("+573001234567")
                    .senderPhone("+15005550006")
                    .body("Hola Lucho, tu código de verificación es 8921.")
                    .build();

            library.send(Notification.builder().content(sms).build());

            // CASO B: Enviar Email (SendGrid)
            System.out.println(">>> Enviando Email...");
            EmailContent email = EmailContent.builder()
                    .recipient("lucho@dominio.com")
                    .subject("Bienvenido a la plataforma")
                    .bodyHtml("<h1>Hola!</h1><p>Gracias por registrarte.</p>")
                    .build();

            library.send(Notification.builder().content(email).build());

            // CASO C: Enviar Push (FCM)
            System.out.println(">>> Enviando Push Notification...");
            Map<String, String> data = new HashMap<>();
            data.put("screen", "settings");
            data.put("promo_id", "DESCUENTO_20");

            PushContent push = PushContent.builder()
                    .recipient("DEVICE_TOKEN_XYZ_123")
                    .title("Oferta Flash ⚡")
                    .body("50% de descuento en zapatos solo por hoy.")
                    .data(data)
                    .build();

            library.send(Notification.builder().content(push).build());

            System.out.println("\n✅ TODAS LAS PRUEBAS FINALIZARON EXITOSAMENTE.");

        } catch (Exception e) {
            System.err.println("\n❌ ERROR CRÍTICO DURANTE LA PRUEBA:");
            e.printStackTrace();
        }
    }
}