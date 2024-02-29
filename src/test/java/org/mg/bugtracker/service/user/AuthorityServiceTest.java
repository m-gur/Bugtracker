package org.mg.bugtracker.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.user.Authority;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.RequestedAuthority;
import org.mg.bugtracker.mappers.user.AuthorityMapper;
import org.mg.bugtracker.repository.user.AuthorityRepository;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceTest {

    @InjectMocks
    private AuthorityService authorityService;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private LoginRepository loginRepository;
    @Mock
    private AuthorityMapper authorityMapper;

    @Test
    void getAll_withoutParameters_returnsEquals() {
        // given
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority());
        authorityList.add(new Authority());

        // when
        when(authorityRepository.findAll()).thenReturn(authorityList);
        List<AuthorityDTO> all = authorityService.getAll();

        // then
        assertEquals(2, all.size());
        verify(authorityRepository, times(1)).findAll();
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        List<AuthorityDTO> all = authorityService.getAll();

        // then
        assertEquals(0, all.size());
    }

    @Test
    void getAuthorityById_withoutParameters_returnsAuthority() {
        // given
        Authority authority = new Authority();
        authority.setAuthorityId(1);

        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setAuthorityId(1);

        // when
        when(authorityRepository.findById(1)).thenReturn(Optional.of(authority));
        when(authorityMapper.toAuthorityDTO(authority)).thenReturn(authorityDTO);
        AuthorityDTO authorityById = authorityService.getAuthorityById(1);

        // then
        assertEquals(1, authorityById.getAuthorityId());
    }

    @Test
    void getAuthorityById_withoutParameters_throwsRuntimeException() {
        // given, when & then

        assertThrows(RuntimeException.class, () -> authorityService.getAuthorityById(anyInt()));
    }

    @Test
    void addAuthority_withoutParameters_returnsEquals() {
        // given
        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setAuthorityId(1);
        authorityDTO.setName("supp");
        Authority authorityToSave = new Authority();
        RequestedAuthority requestedAuthority = new RequestedAuthority("supp");

        // when
        when(authorityMapper.fromAuthorityRequest(requestedAuthority)).thenReturn(authorityToSave);
        when(authorityMapper.toAuthorityDTO(authorityToSave)).thenReturn(authorityDTO);
        when(authorityRepository.save(authorityToSave)).thenReturn(authorityToSave);
        AuthorityDTO savedDto = authorityService.addAuthority(requestedAuthority);

        // then
        assertNotNull(savedDto);
        assertEquals(authorityDTO.getName(), savedDto.getName());
    }

    @Test
    void deleteAuthority_withoutParameters_verifiesOk() {
        // given
        Authority authority = new Authority();
        authority.setAuthorityId(1);
        authority.setDeleted(false);

        // when
        authorityService.deleteAuthority(authority.getAuthorityId());

        // then
        verify(loginRepository).deleteByAuthorityId(authority.getAuthorityId());
        verify(authorityRepository).deleteById(authority.getAuthorityId());
    }
}