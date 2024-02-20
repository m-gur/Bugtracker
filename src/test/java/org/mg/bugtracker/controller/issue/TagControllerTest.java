package org.mg.bugtracker.controller.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.issue.dto.RequestedTag;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.service.issue.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TagService tagService;
    @Test
    void getAll_withoutParameters_returns200ok() throws Exception {
        // given
        List<TagDTO> tagDTOArrayList = new ArrayList<>();
        tagDTOArrayList.add(new TagDTO());
        tagDTOArrayList.add(new TagDTO());

        // when
        when(tagService.getAll()).thenReturn(tagDTOArrayList);

        // then
        mockMvc.perform(get("/bugtracker/tags/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(tagDTOArrayList.size()));
    }

    @Test
    void getTagById_withoutParameters_returns200ok() throws Exception {
        // given
        int tagId = 1;
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagId(tagId);

        // when
        when(tagService.getById(tagId)).thenReturn(tagDTO);

        // then
        mockMvc.perform(get("/bugtracker/tags/{tagId}", tagId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tagId").value(tagId));
    }

    @Test
    void addTag_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedTag requestedTag = new RequestedTag();
        requestedTag.setName("tag");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("tag");

        // when
        when(tagService.addTag(requestedTag)).thenReturn(tagDTO);

        // then
        mockMvc.perform(post("/bugtracker/tags/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedTag)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(tagDTO.getName()));
    }

    @Test
    void deleteTag_withoutParameters_returns200ok() throws Exception {
        // given
        int tagId = 1;

        // when & then
        mockMvc.perform(delete("/bugtracker/tags/{tagId}", tagId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}