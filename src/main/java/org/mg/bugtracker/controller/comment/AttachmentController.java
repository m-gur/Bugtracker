package org.mg.bugtracker.controller.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.service.comment.AttachmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttachmentController extends BugTrackerAbstractController {

    private final AttachmentService attachmentService;

    @GetMapping(value = "/attachments/all")
    public List<AttachmentDTO> getAll() {
        return attachmentService.getAll();
    }
}
