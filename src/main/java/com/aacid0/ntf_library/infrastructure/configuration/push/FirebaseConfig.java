package com.aacid0.ntf_library.infrastructure.configuration.push;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FirebaseConfig {
    @NonNull
    String projectId;

    @NonNull
    String accessToken;
}
