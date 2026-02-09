package com.aacid0.ntf_library.domain.model.content;

import java.util.HashMap;
import java.util.Map;

import com.aacid0.ntf_library.shared.exceptions.push.validation.EmptyPushBodyException;
import com.aacid0.ntf_library.shared.exceptions.push.validation.EmptyPushRecipientException;
import com.aacid0.ntf_library.shared.exceptions.push.validation.EmptyPushTitleException;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class PushNotificationContent implements Content {
    @NonNull
    String recipient;

    @NonNull
    String title;

    @NonNull
    String body;

    @Builder.Default
    Map<String, String> data = new HashMap<>();

    public static class PushNotificationContentBuilder {
        private String recipient;
        private String title;
        private String body;
        private Map<String, String> data;

        public PushNotificationContent build() {
            if (recipient == null || recipient.isBlank()) {
                throw new EmptyPushRecipientException();
            }
            if (title == null || title.isBlank()) {
                throw new EmptyPushTitleException();
            }
            if (body == null || body.isBlank()) {
                throw new EmptyPushBodyException();
            }

            Map<String, String> finalData = (this.data == null) ? new HashMap<>() : this.data;

            return new PushNotificationContent(recipient, title, body, finalData);
        }
    }
}
