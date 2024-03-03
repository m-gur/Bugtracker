package org.mg.bugtracker.controller.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.service.comment.AttachmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AttachmentController extends BugTrackerAbstractController {

    private final AttachmentService attachmentService;

    @GetMapping("/attachments/image/{attachmentId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer attachmentId) throws HttpMediaTypeNotSupportedException {
        AttachmentDTO file = attachmentService.getAttachmentById(attachmentId);
        MediaType mediaType;
        if (file.getType().equalsIgnoreCase("image/png")) mediaType = MediaType.IMAGE_PNG;
        else if (file.getType().equalsIgnoreCase("image/jpg")) mediaType = MediaType.IMAGE_JPEG;
        else throw new HttpMediaTypeNotSupportedException("Not supported media type!");
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(file.getData());
    }

}
