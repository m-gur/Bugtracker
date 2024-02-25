package org.mg.bugtracker.service.comment;

import jakarta.activation.UnsupportedDataTypeException;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.Attachment;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.entity.comment.dto.AttachmentMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public List<AttachmentDTO> getAll() {
        return attachmentRepository.findAll()
                .stream()
                .map(attachmentMapper::toAttachmentDTO)
                .collect(Collectors.toList());
    }

    public void addAttachment(Comment comment, MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        if (!contentType.equalsIgnoreCase("image/jpg") || !contentType.equalsIgnoreCase("/image/png")) throw new UnsupportedDataTypeException("Cannot add file with this extension!");
        byte[] bytes = multipartFile.getBytes();

        Attachment attachment = new Attachment();
        attachment.setDeleted(false);
        attachment.setName(originalFilename);
        attachment.setType(contentType);
        attachment.setData(bytes);
        attachment.setComment(comment);
        attachment.setDateAdded(LocalDate.now());
        attachmentRepository.save(attachment);
    }

    public AttachmentDTO getAttachmentById(Integer attachmentId) {
        return attachmentRepository.findById(attachmentId)
                .map(attachmentMapper::toAttachmentDTO)
                .orElseThrow();
    }
}
