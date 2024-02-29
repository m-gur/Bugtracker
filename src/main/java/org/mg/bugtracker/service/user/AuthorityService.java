package org.mg.bugtracker.service.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.Authority;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.RequestedAuthority;
import org.mg.bugtracker.mappers.user.AuthorityMapper;
import org.mg.bugtracker.repository.user.AuthorityRepository;
import org.mg.bugtracker.repository.user.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final LoginRepository loginRepository;
    private final AuthorityMapper authorityMapper;

    public List<AuthorityDTO> getAll() {
        return authorityRepository.findAll()
                .stream()
                .map(authorityMapper::toAuthorityDTO)
                .collect(Collectors.toList());
    }

    public AuthorityDTO getAuthorityById(int authorityId) {
        return authorityRepository.findById(authorityId)
                .map(authorityMapper::toAuthorityDTO)
                .orElseThrow(() -> new RuntimeException("Authority with given id does not exist"));
    }

    public AuthorityDTO addAuthority(RequestedAuthority requestedAuthority) {
        Authority authorityToSave = authorityMapper.fromAuthorityRequest(requestedAuthority);
        return authorityMapper.toAuthorityDTO(authorityRepository.save(authorityToSave));
    }

    public void deleteAuthority(int authorityId) {
        loginRepository.deleteByAuthorityId(authorityId);
        authorityRepository.deleteById(authorityId);
    }
}
