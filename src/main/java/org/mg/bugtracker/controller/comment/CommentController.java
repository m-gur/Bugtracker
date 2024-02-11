package org.mg.bugtracker.controller.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.service.comment.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController extends BugTrackerAbstractController {

    private final CommentService commentService;

    @GetMapping(value = "/comments/all")
    public List<CommentDTO> getAllComments() {
        return commentService.getAll();
    }
}
