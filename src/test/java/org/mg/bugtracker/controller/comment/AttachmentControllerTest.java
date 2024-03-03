package org.mg.bugtracker.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.comment.dto.AttachmentDTO;
import org.mg.bugtracker.service.comment.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AttachmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AttachmentService attachmentService;

    @Test
    void getImage_withoutParameters_returns200okAndMediaTypePNG() throws Exception {
        // given
        int attachmentId = 1;

        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setType("image/png");
        attachmentDTO.setAttachmentId(1);

        // when
        when(attachmentService.getAttachmentById(attachmentId)).thenReturn(attachmentDTO);

        // then
        mockMvc.perform(get("/bugtracker/attachments/image/{attachmentId}", attachmentId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    void getImage_withoutParameters_returns200okAndMediaTypeJPG() throws Exception {
        // given
        int attachmentId = 1;

        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setType("image/jpg");
        attachmentDTO.setAttachmentId(1);

        // when
        when(attachmentService.getAttachmentById(attachmentId)).thenReturn(attachmentDTO);

        // then
        mockMvc.perform(get("/bugtracker/attachments/image/{attachmentId}", attachmentId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }
}