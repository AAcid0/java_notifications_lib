package com.aacid0.ntf_library.domain.model.content;

import com.aacid0.ntf_library.domain.exceptions.email.validation.EmptyEmailBodyException;
import com.aacid0.ntf_library.domain.exceptions.sms.validation.EmptySmsRecipientPhoneException;
import com.aacid0.ntf_library.domain.exceptions.sms.validation.EmptySmsSenderException;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class SmsContent implements Content {

    String recipientPhone;
    String body;
    String senderPhone; // senderId

    public static class SmsContentBuilder {
        public SmsContent build() {
            if (recipientPhone == null || recipientPhone.isBlank()) {
                throw new EmptySmsRecipientPhoneException();
            }
            if (body == null || body.isBlank()) {
                throw new EmptyEmailBodyException();
            }
            if (senderPhone == null || senderPhone.isBlank()) {
                throw new EmptySmsSenderException();
            }
            return new SmsContent(recipientPhone, body, senderPhone);
        }
    }

}
