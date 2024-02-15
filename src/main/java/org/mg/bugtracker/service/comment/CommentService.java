package org.mg.bugtracker.service.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.CommentMapper;
import org.mg.bugtracker.entity.comment.dto.RequestedComment;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AttachmentRepository attachmentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDTO> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(int commentId) {
        return commentRepository.findCommentByCommentId(commentId)
                .map(commentMapper::toCommentDTO)
                .orElseThrow(() -> new RuntimeException("Comment with given id not exist!"));
    }

    public CommentDTO addComment(RequestedComment requestedComment) {
        Comment commentToSave = commentMapper.addComment(requestedComment);
        commentRepository.save(commentToSave);
        return commentMapper.toCommentDTO(commentToSave);
    }

    public void deleteComment(int commentId) {
        attachmentRepository.deleteAttachmentsByCommentId(commentId);
        commentRepository.deleteById(commentId);
    }
}
