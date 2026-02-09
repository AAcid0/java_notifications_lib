package com.aacid0.ntf_library.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.aacid0.ntf_library.domain.model.content.Content;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Notification {
    @Builder.Default
    String id = UUID.randomUUID().toString();

    @Builder.Default
    Instant createdAt = Instant.now();

    @NonNull
    Content content;
}
