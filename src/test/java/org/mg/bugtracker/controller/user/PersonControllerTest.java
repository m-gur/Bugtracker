package org.mg.bugtracker.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mg.bugtracker.entity.user.dto.PersonDTO;
import org.mg.bugtracker.entity.user.dto.RequestedPerson;
import org.mg.bugtracker.service.user.PersonService;
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
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    void getAll_withoutParameters_returns200ok() throws Exception {
        // given
        List<PersonDTO> personDTOArrayList = new ArrayList<>();
        personDTOArrayList.add(new PersonDTO());
        personDTOArrayList.add(new PersonDTO());

        // when
        when(personService.getAll()).thenReturn(personDTOArrayList);

        // then
        mockMvc.perform(get("/bugtracker/persons/all")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(personDTOArrayList.size()));
    }

    @Test
    void getPersonById_withoutParameters_returns200ok() throws Exception {
        // given
        int personId = 1;
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonId(personId);

        // when
        when(personService.getPersonById(personId)).thenReturn(personDTO);

        // then
        mockMvc.perform(get("/bugtracker/persons/{personId}", personId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(personId));
    }

    @Test
    void addPerson_withoutParameters_returns200ok() throws Exception {
        // given
        RequestedPerson requestedPerson = new RequestedPerson();
        requestedPerson.setName("name");

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("name");

        // when
        when(personService.addPerson(requestedPerson)).thenReturn(personDTO);

        // then
        mockMvc.perform(post("/bugtracker/persons/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(personDTO.getName()));
    }

    @Test
    void deletePerson_withoutParameters_returns200ok() throws Exception {
        // given
        int personId = 1;

        // when & then
        mockMvc.perform(delete("/bugtracker/persons/{personId}", personId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("admin").authorities(AuthorityUtils.createAuthorityList("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}