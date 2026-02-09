package com.aacid0.ntf_library.domain.model.content;

public sealed interface Content
                permits EmailContent, SmsContent, PushNotificationContent {
}