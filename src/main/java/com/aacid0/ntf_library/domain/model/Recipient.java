package com.aacid0.ntf_library.domain.model;

public record Recipient(
        String id,
        String name,
        String email,
        String phone,
        String deviceToken) {

}
