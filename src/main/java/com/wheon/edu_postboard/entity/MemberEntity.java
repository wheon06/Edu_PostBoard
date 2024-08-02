package com.wheon.edu_postboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "Member")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

}
