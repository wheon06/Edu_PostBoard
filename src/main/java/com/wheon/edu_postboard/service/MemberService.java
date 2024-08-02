package com.wheon.edu_postboard.service;

import com.wheon.edu_postboard.dto.JoinRequestDto;
import com.wheon.edu_postboard.entity.MemberEntity;
import com.wheon.edu_postboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Boolean join(JoinRequestDto requestDto) {

        if (memberRepository.existsByUsername(requestDto.getUsername())) {
            return Boolean.FALSE;
        }

        memberRepository.save(MemberEntity.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build()
        );

        return Boolean.TRUE;

    }

}
