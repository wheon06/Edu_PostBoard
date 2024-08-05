package com.wheon.edu_postboard.dto;

import com.wheon.edu_postboard.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private Long id;

    private String title;

    private String content;

    private MemberEntity memberEntity;

}
