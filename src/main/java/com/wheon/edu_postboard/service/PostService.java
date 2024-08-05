package com.wheon.edu_postboard.service;

import com.wheon.edu_postboard.dto.EditPostDto;
import com.wheon.edu_postboard.dto.PostRequestDto;
import com.wheon.edu_postboard.dto.WritePostDto;
import com.wheon.edu_postboard.entity.MemberEntity;
import com.wheon.edu_postboard.entity.PostEntity;
import com.wheon.edu_postboard.repository.MemberRepository;
import com.wheon.edu_postboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public List<PostEntity> loadPosts(String keyword) {
        List<PostEntity> findPostEntities = postRepository.findAllByOrderByDateDesc();

        if (keyword == null) {
            return findPostEntities;
        }

        List<PostEntity> postEntities = new ArrayList<>();

        for (PostEntity postEntity : findPostEntities) {
            if (postEntity.getTitle().contains(keyword)) {
                postEntities.add(postEntity);
            }
        }

        return postEntities;
    }

    public void savePost(WritePostDto writePostDto) {
        MemberEntity memberEntity = memberRepository.findByUsername(writePostDto.getUsername()).get();

        postRepository.save(PostEntity.builder()
                .title(writePostDto.getTitle())
                .content(writePostDto.getContent())
                .date(new Date())
                .memberEntity(memberEntity)
                .build()
        );
    }

    public PostRequestDto getPost(Long postId) {
        Optional<PostEntity> findPost = postRepository.findById(postId);
        PostEntity postEntity;
        PostRequestDto requestDto = new PostRequestDto();

        if (findPost.isEmpty()) {
            return null;
        } else {
            postEntity = findPost.get();
        }

        requestDto.setId(postEntity.getId());
        requestDto.setTitle(postEntity.getTitle());
        requestDto.setContent(postEntity.getContent());
        requestDto.setMemberEntity(postEntity.getMemberEntity());

        return requestDto;
    }

    public Long editPost(EditPostDto editPostDto) {
        Optional<PostEntity> findPost = postRepository.findById(editPostDto.getId());
        PostEntity postEntity;

        if (findPost.isEmpty()) {
            return null;
        } else {
            postEntity = findPost.get();
        }

        postEntity.setTitle(editPostDto.getTitle());
        postEntity.setContent(editPostDto.getContent());
        postEntity.setDate(new Date());

        postRepository.save(postEntity);

        return postEntity.getId();
    }

    public Long deletePost(Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }

}
