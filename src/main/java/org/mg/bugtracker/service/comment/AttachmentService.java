package org.mg.bugtracker.service.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.entity.comment.dto.AttachmentMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.springframework.stereotype.Service;

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
}
