package org.mg.bugtracker.service.comment;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.RequestedComment;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.mappers.comment.CommentMapper;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.mg.bugtracker.service.user.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;
    private final PersonService personService;
    private final CommentMapper commentMapper;

    public List<CommentDTO> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(int commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::toCommentDTO)
                .orElseThrow(() -> new RuntimeException("Comment with given id not exist!"));
    }

    public CommentDTO addComment(RequestedComment requestedComment, MultipartFile multipartFile) throws IOException {
        Comment commentToSave = prepareCommentToSave(requestedComment);
        Comment savedComment = commentRepository.save(commentToSave);

        if (!multipartFile.isEmpty()) {
            attachmentService.addAttachment(savedComment, multipartFile);
        }

        return commentMapper.toCommentDTO(commentToSave);
    }

    public void deleteComment(int commentId) {
        attachmentRepository.deleteAttachmentsByCommentId(commentId);
        commentRepository.deleteById(commentId);
    }

    private Comment prepareCommentToSave(RequestedComment requestedComment) {
        Comment commentToSave = commentMapper.addComment(requestedComment);
        commentToSave.setDateCreated(LocalDate.now());
        commentToSave.setLastUpdate(LocalDate.now());
        Person actuallyLoggedPerson = personService.findPersonForContextLogin();
        commentToSave.setPerson(actuallyLoggedPerson);
        return commentToSave;
    }
}
