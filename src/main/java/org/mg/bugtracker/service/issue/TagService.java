package org.mg.bugtracker.service.issue;

import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.entity.issue.dto.TagMapper;
import org.mg.bugtracker.repository.issue.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDTO> getAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toTagDTO)
                .collect(Collectors.toList());
    }
}
