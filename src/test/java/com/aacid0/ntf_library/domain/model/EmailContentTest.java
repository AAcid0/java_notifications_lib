package com.aacid0.ntf_library.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aacid0.ntf_library.domain.model.content.EmailContent;

public class EmailContentTest {

    @Test
    void shouldBuildValidEmail() {
        EmailContent email = EmailContent.builder()
                .recipient("test@test.com")
                .subject("Hola")
                .bodyText("Cuerpo")
                .build();

        Assertions.assertNotNull(email);
        Assertions.assertEquals("test@test.com", email.getRecipient());
    }

    @Test
    void shouldFailIfRecipientIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            EmailContent.builder()
                    .subject("Hola")
                    .bodyText("Cuerpo")
                    .build(); // Falta recipient
        });
    }

    @Test
    void shouldFailIfBothBodiesAreMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            EmailContent.builder()
                    .recipient("test@test.com")
                    .subject("Hola")
                    // Falta bodyText y bodyHtml
                    .build();
        });
    }
}
