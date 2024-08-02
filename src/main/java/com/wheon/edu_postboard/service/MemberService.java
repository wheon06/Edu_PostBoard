package com.wheon.edu_postboard.service;

import com.wheon.edu_postboard.dto.JoinRequestDto;
import com.wheon.edu_postboard.dto.LoginRequestDto;
import com.wheon.edu_postboard.dto.LoginResponseDto;
import com.wheon.edu_postboard.entity.MemberEntity;
import com.wheon.edu_postboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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

    public LoginResponseDto login(LoginRequestDto requestDto) {

        Optional<MemberEntity> findMember = memberRepository.findByUsername(requestDto.getUsername());
        MemberEntity memberEntity;

        if (findMember.isEmpty()) {
            return null;
        } else {
            memberEntity = findMember.get();
        }

        if (!requestDto.getPassword().equals(memberEntity.getPassword())) {
            return null;
        }

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setUsername(requestDto.getUsername());

        return responseDto;
    }

}
