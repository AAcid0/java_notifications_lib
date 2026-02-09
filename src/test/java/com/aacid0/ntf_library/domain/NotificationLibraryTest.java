package com.aacid0.ntf_library.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.aacid0.ntf_library.NotificationLibrary;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.SmsContent;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;

public class NotificationLibraryTest {

    @Test
    void shouldRouteToCorrectProvider() {
        // 1. GIVEN: Un mock de proveedor que SOLO acepta SMS
        NotificationProvider smsProvider = Mockito.mock(NotificationProvider.class);
        Mockito.when(smsProvider.supports(Mockito.any())).thenAnswer(invocation -> {
            Notification n = invocation.getArgument(0);
            return n.getContent() instanceof SmsContent;
        });

        // 2. AND: La librería configurada con ese proveedor
        NotificationLibrary library = new NotificationLibrary.NotificationLibraryBuilder()
                .registerProvider(smsProvider)
                .build();

        // 3. AND: Una notificación de tipo SMS
        SmsContent content = SmsContent.builder()
                .recipientPhone("123")
                .senderPhone("+1234567890")
                .body("Hola")
                .build();
        Notification notification = Notification.builder().content(content).build();

        // 4. WHEN: Enviamos
        library.send(notification);

        // 5. THEN: El proveedor debió ser llamado 1 vez
        Mockito.verify(smsProvider, Mockito.times(1)).send(notification);
    }

    @Test
    void shouldThrowExceptionIfNoProviderFound() {
        // 1. GIVEN: Librería vacía (sin proveedores)
        NotificationLibrary library = new NotificationLibrary.NotificationLibraryBuilder().build();

        SmsContent content = SmsContent.builder()
                .recipientPhone("123")
                .senderPhone("+1234567890")
                .body("Hola")
                .build();
        Notification notification = Notification.builder().content(content).build();

        // 2. WHEN & THEN: Debe lanzar excepción
        Assertions.assertThrows(RuntimeException.class, () -> {
            library.send(notification);
        });
    }
}
