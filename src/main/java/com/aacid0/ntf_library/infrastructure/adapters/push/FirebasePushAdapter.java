package com.aacid0.ntf_library.infrastructure.adapters.push;

import java.util.stream.Collectors;

import com.aacid0.ntf_library.domain.exceptions.push.delivery.PushDeliveryException;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.PushContent;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;
import com.aacid0.ntf_library.infrastructure.configuration.push.FirebaseConfig;

public class FirebasePushAdapter implements NotificationProvider {

    private final FirebaseConfig config;

    public FirebasePushAdapter(FirebaseConfig config) {
        this.config = config;
    }

    @Override
    public void send(Notification notification) {
        try {
            PushContent content = (PushContent) notification.getContent();

            String url = String.format("https://fcm.googleapis.com/v1/projects/%s/messages:send",
                    config.getProjectId());

            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"message\": {");

            json.append("\"token\": \"").append(content.getRecipient()).append("\",");

            json.append("\"notification\": {");
            json.append("\"title\": \"").append(escapeJson(content.getTitle())).append("\",");
            json.append("\"body\": \"").append(escapeJson(content.getBody())).append("\"");
            json.append("}");

            if (content.getData() != null && !content.getData().isEmpty()) {
                json.append(", \"data\": {");
                String dataJson = content.getData().entrySet().stream()
                        .map(entry -> "\"" + escapeJson(entry.getKey()) + "\": \"" + escapeJson(entry.getValue())
                                + "\"")
                        .collect(Collectors.joining(", "));

                json.append(dataJson);
                json.append("}");
            }

            json.append("}");
            json.append("}");

            // SIMULACIÓN DEL ENVÍO
            System.out.println("\n========== [FCM ADAPTER OUTBOUND] ==========");
            System.out.println("Endpoint: POST " + url);
            System.out.println("Headers:");
            System.out.println("  Authorization: Bearer " + config.getAccessToken());
            System.out.println("  Content-Type: application/json; UTF-8");
            System.out.println("Payload:");
            System.out.println("  " + json.toString());
            System.out.println("Status: 200 OK (Simulated)");
            System.out.println(
                    "Response: {\"name\": \"projects/" + config.getProjectId() + "/messages/0:16123456789...\"}");
            System.out.println("============================================\n");

        } catch (Exception e) {
            throw new PushDeliveryException("Error simulando envío a FCM", e);
        }
    }

    private String escapeJson(String text) {
        if (text == null)
            return "";
        return text.replace("\"", "\\\"").replace("\n", "\\n");
    }

    @Override
    public boolean supports(Notification notification) {
        return notification.getContent() instanceof PushContent;
    }

}
