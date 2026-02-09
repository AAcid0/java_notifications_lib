package com.aacid0.ntf_library.infrastructure.configuration.sms;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TwilioConfig {
    String accountSid;
    String authToken;
    String defaultFrom;
}
