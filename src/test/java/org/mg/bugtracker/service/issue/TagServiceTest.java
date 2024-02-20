package org.mg.bugtracker.service.issue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.bugtracker.entity.issue.Tag;
import org.mg.bugtracker.entity.issue.dto.RequestedTag;
import org.mg.bugtracker.entity.issue.dto.TagDTO;
import org.mg.bugtracker.entity.issue.dto.TagMapper;
import org.mg.bugtracker.repository.issue.TagRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;

    @Test
    void getAll_withoutParameters_returnsNotEmptyList() {
        // given
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());
        tagList.add(new Tag());

        TagDTO dto = new TagDTO();

        // when
        when(tagRepository.findAll()).thenReturn(tagList);
        when(tagMapper.toTagDTO(any(Tag.class))).thenReturn(dto);
        List<TagDTO> all = tagService.getAll();

        // then
        assertEquals(2, all.size());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void getAll_withoutParameters_returnsEmptyList() {
        // when
        when(tagRepository.findAll()).thenReturn(List.of());
        List<TagDTO> all = tagService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    void getById_withoutParameters_TagFound() {
        // given
        Tag tag = new Tag();

        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagId(1);

        // when
        when(tagRepository.findById(anyInt())).thenReturn(Optional.of(tag));
        when(tagMapper.toTagDTO(any(Tag.class))).thenReturn(tagDTO);
        TagDTO tagById = tagService.getById(anyInt());

        // then
        assertEquals(tagDTO.getTagId(), tagById.getTagId());
    }

    @Test
    void getById_withoutParameters_ThrowsException() {
        // when & then
        assertThrows(RuntimeException.class, () -> tagService.getById(anyInt()));
    }

    @Test
    void addTag_withoutParameters_successTagAdded() {
        // given
        RequestedTag requestedTag = new RequestedTag("tag");

        Tag tag = new Tag();
        tag.setName("tag");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("tag");

        // when
        when(tagRepository.findByName(requestedTag.getName())).thenReturn(Optional.empty());
        when(tagMapper.fromRequest(requestedTag)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);
        TagDTO addedTag = tagService.addTag(requestedTag);

        // then
        assertEquals(requestedTag.getName(), addedTag.getName());
    }

    @Test
    void addTag_withoutParameters_throwsException() {
        // given
        RequestedTag requestedTag = new RequestedTag("tag");

        // when
        when(tagRepository.findByName(requestedTag.getName())).thenReturn(Optional.of(new Tag()));

        // then
        assertThrows(RuntimeException.class, () -> tagService.addTag(requestedTag));
    }

    @Test
    void deleteTag_withoutParameters_successDelete() {
        // when
        tagService.deleteTag(anyInt());

        // then
        verify(tagRepository, times(1)).deleteById(anyInt());
    }
}