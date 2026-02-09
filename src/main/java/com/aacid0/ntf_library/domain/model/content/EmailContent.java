package com.aacid0.ntf_library.domain.model.content;

import java.io.File;
import java.util.List;

import com.aacid0.ntf_library.domain.exceptions.email.validation.EmptyEmailBodyException;
import com.aacid0.ntf_library.domain.exceptions.email.validation.EmptyEmailSubjectException;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class EmailContent implements Content {

    @NonNull
    String subject;
    @NonNull
    String bodyHtml;
    @NonNull
    String bodyText;
    List<File> attachments;
    List<String> cc;
    List<String> bcc;

    public static class EmailContentBuilder {
        public EmailContent build() {
            if (subject == null || subject.isBlank()) {
                throw new EmptyEmailSubjectException();
            }
            if ((bodyHtml == null || bodyHtml.isBlank()) &&
                    (bodyText == null || bodyText.isBlank())) {
                throw new EmptyEmailBodyException();
            }

            attachments = attachments == null ? List.of() : attachments;
            cc = cc == null ? List.of() : cc;
            bcc = bcc == null ? List.of() : bcc;

            return new EmailContent(subject, bodyHtml, bodyText, attachments, cc, bcc);
        }
    }

}
