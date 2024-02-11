package org.mg.bugtracker.entity.comment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mg.bugtracker.entity.comment.Attachment;
import org.mg.bugtracker.entity.comment.Comment;

@Mapper
public interface AttachmentMapper {

    @Mapping(target = "comment", source = "commentId", qualifiedByName = "getComment")
    Attachment toAttachment(AttachmentDTO dto);

    @Mapping(target = "commentId", source = "comment", qualifiedByName = "getCommentId")
    AttachmentDTO toAttachmentDTO(Attachment attachment);

    @Named("getComment")
    default Comment getComment(Integer commentId) {
        if (commentId != null) {
            Comment comment = new Comment();
            comment.setCommentId(commentId);
            return comment;
        }
        return null;
    }

    @Named("getCommentId")
    default Integer getCommentId(Comment comment) {
        return (comment != null) ? comment.getCommentId() : null;
    }
}
