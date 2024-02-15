package org.mg.bugtracker.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.comment.dto.CommentDTO;
import org.mg.bugtracker.entity.comment.dto.RequestedComment;
import org.mg.bugtracker.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;


    @Test
    void getAllComments_withoutParameters_returns200ok() throws Exception {
        // given
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentDTOList.add(new CommentDTO());
        commentDTOList.add(new CommentDTO());

        //when
        when(commentService.getAll()).thenReturn(commentDTOList);

        // then
        mockMvc.perform(get("/bugtracker/comments/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(commentDTOList.size()));
    }

    @Test
    void getCommentByCommentId_withoutParameters_returns200ok() throws Exception {
        // given
        int commentId = 1;
        int personId = 1;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPersonId(personId);

        // when
        when(commentService.getCommentById(commentId)).thenReturn(commentDTO);

        // then
        mockMvc.perform(get("/bugtracker/comments/{commentId}", commentId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(personId));
    }

    @Test
    void addComment_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedComment requestedComment = new RequestedComment();
        requestedComment.setPersonId(1);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPersonId(1);

        //when
        when(commentService.addComment(requestedComment)).thenReturn(commentDTO);

        // then
        mockMvc.perform(post("/bugtracker/comments/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(commentDTO.getPersonId()));
    }

    @Test
    void deleteComment_withoutParameters_returns200ok() throws Exception {
        // given
        int commentId = 1;
        // when
        // then
        mockMvc.perform(delete("/bugtracker/comments/{commentId}", commentId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}