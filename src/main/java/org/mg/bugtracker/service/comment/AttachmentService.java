package org.mg.bugtracker.service.comment;

import jakarta.activation.UnsupportedDataTypeException;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.Attachment;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.mappers.comment.AttachmentMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

import static org.mg.bugtracker.utils.AttachmentValidator.validateAttachmentType;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public void addAttachment(Comment comment, MultipartFile multipartFile) throws IOException {
        String contentType = multipartFile.getContentType();
        if (contentType != null && !validateAttachmentType(contentType))
            throw new UnsupportedDataTypeException("Cannot add file with this extension!");

        Attachment attachment = createAttachment(contentType, multipartFile, comment);
        attachmentRepository.save(attachment);
    }

    public AttachmentDTO getAttachmentById(Integer attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .map(attachmentMapper::toAttachmentDTO)
                .orElseThrow(() -> new RuntimeException("Cannot find attachment with requested id!"));
    }

    private Attachment createAttachment(String contentType, MultipartFile multipartFile, Comment comment) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        byte[] bytes = multipartFile.getBytes();

        Attachment attachment = new Attachment();
        attachment.setDeleted(false);
        attachment.setName(originalFilename);
        attachment.setType(contentType);
        attachment.setData(bytes);
        attachment.setComment(comment);
        attachment.setDateAdded(LocalDate.now());
        return attachment;
    }
}
