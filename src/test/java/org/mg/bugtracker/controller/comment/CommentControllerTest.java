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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        // when
        when(commentService.getAll()).thenReturn(commentDTOList);

        // then
        mockMvc.perform(get("/bugtracker/comments/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
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
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(personId));
    }

    @Test
    void addComment_withoutParameters_returns200ok() throws Exception {
        // given
        int issueId = 1;
        RequestedComment requestedComment = new RequestedComment();
        requestedComment.setIssueId(issueId);


        byte[] fileContent = "Text".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/bugtracker/comments/add")
                        .file(multipartFile)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(requestedComment))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteComment_withoutParameters_returns200ok() throws Exception {
        // given
        int commentId = 1;
        // when & then
        mockMvc.perform(delete("/bugtracker/comments/{commentId}", commentId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}