package org.mg.bugtracker.service.user;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.user.dto.AuthorityDTO;
import org.mg.bugtracker.entity.user.dto.AuthorityMapper;
import org.mg.bugtracker.repository.user.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public List<AuthorityDTO> getAll() {
        return authorityRepository.findAll()
                .stream()
                .map(authorityMapper::toAuthorityDTO)
                .collect(Collectors.toList());
    }
}
