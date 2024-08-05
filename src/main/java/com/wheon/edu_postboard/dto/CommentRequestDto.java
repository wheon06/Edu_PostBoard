package com.wheon.edu_postboard.dto;

import com.wheon.edu_postboard.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long id;

    private String content;

    private PostEntity postEntity;

}
