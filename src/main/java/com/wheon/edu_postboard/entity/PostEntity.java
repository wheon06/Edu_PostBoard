package com.wheon.edu_postboard.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "Member_Id")
    private MemberEntity memberEntity;

}
