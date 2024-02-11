package org.mg.bugtracker.entity.issue.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mg.bugtracker.entity.issue.Tag;

@Mapper
public interface TagMapper {

    @Mapping(target = "issueTags", ignore = true)
    Tag toTag(TagDTO dto);

    TagDTO toTagDTO(Tag tag);
}
