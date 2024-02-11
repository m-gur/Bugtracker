package org.mg.bugtracker.service.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.CommentMapper;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDTO> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
