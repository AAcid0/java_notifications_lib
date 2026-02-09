package com.aacid0.ntf_library.infrastructure.configuration.email;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SendGridConfig {
    @NonNull
    String apiKey;
    @NonNull
    String fromEmail;
    String fromName;
}
