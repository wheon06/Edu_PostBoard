package com.wheon.edu_postboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "Member_Id")
    private MemberEntity memberEntity;

}
