package org.mg.bugtracker.service.comment;

import jakarta.activation.UnsupportedDataTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mg.bugtracker.entity.comment.Attachment;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.mappers.comment.AttachmentMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mg.bugtracker.utils.AttachmentValidatorTest.failureValidate;
import static org.mg.bugtracker.utils.AttachmentValidatorTest.successValidate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceTest {

    @InjectMocks
    private AttachmentService attachmentService;
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private AttachmentMapper attachmentMapper;

    @ParameterizedTest
    @MethodSource("successValidateContentType")
    void addAttachment_withoutParameters_successfullyAttachmentAdded(String contentType) throws IOException {
        // given
        Comment comment = new Comment();

        byte[] fileContent = "Text".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", contentType, fileContent);

        // when
        attachmentService.addAttachment(comment, multipartFile);

        // then
        verify(attachmentRepository,times(1)).save(any(Attachment.class));
    }

    @ParameterizedTest
    @MethodSource("failureValidateContentType")
     void addAttachment_withoutParameters_throwsException(String contentType) throws IOException {
        // given
        Comment comment = new Comment();

        byte[] fileContent = "Text".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", contentType, fileContent);

        // when & then
        assertThrows(UnsupportedDataTypeException.class, () -> attachmentService.addAttachment(comment, multipartFile));
    }

    @Test
    void getAttachmentById_withoutParameters_returnsAttachment() {
        // given
        int attachmentId = 1;

        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setAttachmentId(attachmentId);

        // when
        when(attachmentRepository.findById(attachmentId)).thenReturn(Optional.of(new Attachment()));
        when(attachmentMapper.toAttachmentDTO(any(Attachment.class))).thenReturn(attachmentDTO);
        AttachmentDTO attachmentById = attachmentService.getAttachmentById(attachmentId);

        // then
        assertEquals(attachmentId, attachmentById.getAttachmentId());
    }

    @Test
    void getAttachmentById_withoutParameters_throwsException() {
        // when
        when(attachmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> attachmentService.getAttachmentById(anyInt()));
    }

    public static Stream<Arguments> successValidateContentType() {
        return successValidate();
    }

    public static Stream<Arguments> failureValidateContentType() {
        return failureValidate();
    }
}