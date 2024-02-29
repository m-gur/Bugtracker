package org.mg.bugtracker.utils;

import org.springframework.stereotype.Component;

@Component
public class AttachmentValidator {
    public static boolean validateAttachmentType(String contentType) {
        return contentType.equalsIgnoreCase("image/jpg") || contentType.equalsIgnoreCase("image/png");
    }
}
