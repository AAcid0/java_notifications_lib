package com.aacid0.ntf_library.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aacid0.ntf_library.domain.model.content.SmsContent;

public class SmsContentTest {

    @Test
    void shouldBuildValidSms() {
        SmsContent sms = SmsContent.builder()
                .recipientPhone("+573001234567")
                .senderPhone("+15005550006")
                .body("Hola, este es un mensaje de prueba")
                .build();

        Assertions.assertNotNull(sms);
        Assertions.assertEquals("+573001234567", sms.getRecipientPhone());
        Assertions.assertEquals("+15005550006", sms.getSenderPhone());
        Assertions.assertEquals("Hola, este es un mensaje de prueba", sms.getBody());
    }

    @Test
    void shouldFailIfRecipientPhoneIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .senderPhone("+15005550006")
                    .body("Mensaje")
                    .build(); // Falta recipientPhone
        });
    }

    @Test
    void shouldFailIfRecipientPhoneIsBlank() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .recipientPhone("   ")
                    .senderPhone("+15005550006")
                    .body("Mensaje")
                    .build(); // recipientPhone vacío
        });
    }

    @Test
    void shouldFailIfBodyIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .recipientPhone("+573001234567")
                    .senderPhone("+15005550006")
                    .build(); // Falta body
        });
    }

    @Test
    void shouldFailIfBodyIsBlank() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .recipientPhone("+573001234567")
                    .senderPhone("+15005550006")
                    .body("")
                    .build(); // body vacío
        });
    }

    @Test
    void shouldFailIfSenderPhoneIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .recipientPhone("+573001234567")
                    .body("Mensaje")
                    .build(); // Falta senderPhone
        });
    }

    @Test
    void shouldFailIfSenderPhoneIsBlank() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            SmsContent.builder()
                    .recipientPhone("+573001234567")
                    .senderPhone("   ")
                    .body("Mensaje")
                    .build(); // senderPhone vacío
        });
    }
}
