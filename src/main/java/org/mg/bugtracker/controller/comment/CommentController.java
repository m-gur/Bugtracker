package org.mg.bugtracker.controller.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.controller.BugTrackerAbstractController;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.RequestedComment;
import org.mg.bugtracker.service.comment.CommentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController extends BugTrackerAbstractController {

    private final CommentService commentService;

    @GetMapping(value = "/comments/all")
    public List<CommentDTO> getAllComments() {
        return commentService.getAll();
    }

    @GetMapping(value = "/comments/{commentId}")
    public CommentDTO getCommentByCommentId(@PathVariable int commentId) {
        return commentService.getCommentById(commentId);
    }

    @PostMapping(value = "/comments/add")
    public CommentDTO addComment(@ModelAttribute RequestedComment requestedComment, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return commentService.addComment(requestedComment, multipartFile);
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public void deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
    }
}
