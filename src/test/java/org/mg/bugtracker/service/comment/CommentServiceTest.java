package org.mg.bugtracker.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.comment.Comment;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.CommentMapper;
import org.mg.bugtracker.entity.comment.dto.RequestedComment;
import org.mg.bugtracker.entity.user.Person;
import org.mg.bugtracker.repository.comment.AttachmentRepository;
import org.mg.bugtracker.repository.comment.CommentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AttachmentRepository attachmentRepository;

    @Test
    void getAll_withoutParameters_returnsNotEmptyList() {
        // given
        Person person = new Person();
        person.setPersonId(1);
        Comment comment = new Comment();
        comment.setPerson(person);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPersonId(1);
        // when
        when(commentRepository.findAll()).thenReturn(comments);
        when(commentMapper.toCommentDTO(comment)).thenReturn(commentDTO);
        List<CommentDTO> all = commentService.getAll();

        // then
        assertEquals(1, all.size());
        assertEquals(1, all.get(0).getPersonId());
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // given
        // when
        List<CommentDTO> all = commentService.getAll();

        // then
        assertEquals(0, all.size());
    }

    @Test
    void getCommentById_withoutParameters_returnsEquals() {
        // given
        Person person = new Person();
        person.setPersonId(1);
        Comment comment = new Comment();
        comment.setPerson(person);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPersonId(1);
        // when
        when(commentRepository.findCommentByCommentId(1)).thenReturn(Optional.of(comment));
        when(commentMapper.toCommentDTO(comment)).thenReturn(commentDTO);
        CommentDTO commentById = commentService.getCommentById(1);

        // then
        assertEquals(1, commentById.getPersonId());
    }

    @Test
    void getCommentById_withoutParameters_throwsRuntimeException() {
        // given
        // when
        // then
        assertThrows(RuntimeException.class, () -> commentService.getCommentById(1));
    }

    @Test
    void addComment_withoutParameters_success() {
        // given
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(1);
        commentDTO.setPersonId(1);
        Comment commentToSave = new Comment();
        RequestedComment requestedComment = new RequestedComment();
        requestedComment.setPersonId(1);

        // when
        when(commentMapper.addComment(requestedComment)).thenReturn(commentToSave);
        when(commentMapper.toCommentDTO(commentToSave)).thenReturn(commentDTO);
        when(commentRepository.save(commentToSave)).thenReturn(commentToSave);
        CommentDTO savedDto = commentService.addComment(requestedComment);

        // then
        assertNotNull(savedDto);
        assertEquals(commentDTO.getPersonId(), savedDto.getPersonId());
    }

    @Test
    void deleteComment_withoutParameters_verifyTrue() {
        // given
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setDeleted(false);

        // when
        commentService.deleteComment(comment.getCommentId());

        // then
        verify(attachmentRepository).deleteAttachmentsByCommentId(comment.getCommentId());
        verify(commentRepository).deleteById(comment.getCommentId());
    }

}
