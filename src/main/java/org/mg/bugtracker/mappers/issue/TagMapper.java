package org.mg.bugtracker.mappers.issue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mg.bugtracker.entity.issue.Tag;
import org.mg.bugtracker.entity.issue.dto.RequestedTag;
import org.mg.bugtracker.entity.issue.dto.TagDTO;

@Mapper
public interface TagMapper {

    @Mapping(target = "issueTags", ignore = true)
    Tag toTag(TagDTO dto);

    TagDTO toTagDTO(Tag tag);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "tagId", ignore = true)
    @Mapping(target = "issueTags", ignore = true)
    Tag fromRequest(RequestedTag requestedTag);
}
