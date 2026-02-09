package com.aacid0.ntf_library.domain.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aacid0.ntf_library.domain.model.content.PushContent;

public class PushContentTest {

    @Test
    void shouldBuildValidPushNotification() {
        PushContent push = PushContent.builder()
                .recipient("DEVICE_TOKEN_XYZ_123")
                .title("Oferta Flash")
                .body("50% de descuento en zapatos solo por hoy")
                .build();

        Assertions.assertNotNull(push);
        Assertions.assertEquals("DEVICE_TOKEN_XYZ_123", push.getRecipient());
        Assertions.assertEquals("Oferta Flash", push.getTitle());
        Assertions.assertEquals("50% de descuento en zapatos solo por hoy", push.getBody());
        Assertions.assertNotNull(push.getData());
        Assertions.assertTrue(push.getData().isEmpty());
    }

    @Test
    void shouldBuildPushWithCustomData() {
        Map<String, String> customData = new HashMap<>();
        customData.put("screen", "settings");
        customData.put("promo_id", "DESCUENTO_20");

        PushContent push = PushContent.builder()
                .recipient("DEVICE_TOKEN_ABC")
                .title("Nueva Promoción")
                .body("Revisa las ofertas")
                .data(customData)
                .build();

        Assertions.assertNotNull(push);
        Assertions.assertEquals(2, push.getData().size());
        Assertions.assertEquals("settings", push.getData().get("screen"));
        Assertions.assertEquals("DESCUENTO_20", push.getData().get("promo_id"));
    }

    @Test
    void shouldFailIfRecipientIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            PushContent.builder()
                    .title("Título")
                    .body("Cuerpo")
                    .build(); // Falta recipient
        });
    }

    @Test
    void shouldFailIfTitleIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            PushContent.builder()
                    .recipient("DEVICE_TOKEN")
                    .body("Cuerpo")
                    .build(); // Falta title
        });
    }

    @Test
    void shouldFailIfBodyIsMissing() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            PushContent.builder()
                    .recipient("DEVICE_TOKEN")
                    .title("Título")
                    .build(); // Falta body
        });
    }

    @Test
    void shouldInitializeDataAsEmptyMapByDefault() {
        PushContent push = PushContent.builder()
                .recipient("DEVICE_TOKEN")
                .title("Título")
                .body("Cuerpo")
                .build();

        Assertions.assertNotNull(push.getData());
        Assertions.assertTrue(push.getData().isEmpty());
    }
}
