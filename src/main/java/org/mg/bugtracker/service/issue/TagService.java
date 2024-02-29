package org.mg.bugtracker.service.issue;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.mg.bugtracker.entity.issue.Tag;
import org.mg.bugtracker.entity.issue.dto.RequestedTag;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.mappers.issue.TagMapper;
import org.mg.bugtracker.repository.issue.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public TagDTO getById(int tagId) {
        return tagRepository.findById(tagId)
                .map(tagMapper::toTagDTO)
                .orElseThrow(() -> new RuntimeException("Cannot find tag with requested id!"));
    }

    public TagDTO addTag(RequestedTag requestedTag) {
        Optional<Tag> tag = tagRepository.findByName(requestedTag.getName());
        if (tag.isEmpty()) {
            Tag newTag = tagMapper.fromRequest(requestedTag);
            return tagMapper.toTagDTO(tagRepository.save(newTag));
        } else throw new EntityExistsException("Cannot add this tag!");
    }

    public void deleteTag(int tagId) {
        tagRepository.deleteById(tagId);
    }
}
