package org.mg.bugtracker.utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AttachmentValidatorTest {

    @ParameterizedTest
    @MethodSource("successValidate")
    void validateAttachmentType_withValidate_returnsTrue(String contentType) {
        // when
        boolean validated = AttachmentValidator.validateAttachmentType(contentType);

        // then
        assertTrue(validated);
    }

    @ParameterizedTest
    @MethodSource("failureValidate")
    void validateAttachmentType_withValidate_returnsFalse(String contentType) {
        // when
        boolean validated = AttachmentValidator.validateAttachmentType(contentType);

        // then
        assertFalse(validated);
    }

    public static Stream<Arguments> successValidate() {
        return Stream.of(
                Arguments.of("image/jpg"),
                Arguments.of("image/png")
        );
    }

    public static Stream<Arguments> failureValidate() {
        return Stream.of(
                Arguments.of("text/txt"),
                Arguments.of("json"),
                Arguments.of("java"),
                Arguments.of("js"),
                Arguments.of("php"),
                Arguments.of("bat"),
                Arguments.of("sh")
        );
    }
}