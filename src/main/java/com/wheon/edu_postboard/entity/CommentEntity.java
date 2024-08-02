package com.wheon.edu_postboard.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "Post_Id")
    private PostEntity postEntity;

}
