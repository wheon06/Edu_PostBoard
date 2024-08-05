package com.wheon.edu_postboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Member;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@Table(name = "Comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String date;

    @ManyToOne
    @JoinColumn(name = "Member_Id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "Post_Id")
    private PostEntity postEntity;

}
