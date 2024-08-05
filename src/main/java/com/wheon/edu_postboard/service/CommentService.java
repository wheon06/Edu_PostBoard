package com.wheon.edu_postboard.service;

import com.wheon.edu_postboard.dto.CommentRequestDto;
import com.wheon.edu_postboard.dto.LoginResponseDto;
import com.wheon.edu_postboard.dto.SaveCommentDto;
import com.wheon.edu_postboard.entity.CommentEntity;
import com.wheon.edu_postboard.entity.MemberEntity;
import com.wheon.edu_postboard.entity.PostEntity;
import com.wheon.edu_postboard.repository.CommentRepository;
import com.wheon.edu_postboard.repository.MemberRepository;
import com.wheon.edu_postboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void saveComment(SaveCommentDto saveCommentDto, LoginResponseDto userInfo, Long postId) {
        MemberEntity memberEntity = memberRepository.findByUsername(userInfo.getUsername()).get();
        PostEntity postEntity = postRepository.findById(postId).get();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = formatter.format(new Date());

        commentRepository.save(CommentEntity.builder()
                .content(saveCommentDto.getContent())
                .date(currentDate)
                .memberEntity(memberEntity)
                .postEntity(postEntity)
                .build()
        );
    }

    public List<CommentEntity> loadComments() {
        return commentRepository.findAllByOrderByDateDesc();
    }

    public CommentRequestDto getComment(Long commentId) {
        Optional<CommentEntity> findComment = commentRepository.findById(commentId);
        CommentRequestDto requestDto = new CommentRequestDto();

        if (findComment.isEmpty()) {
            return null;
        } else {
            requestDto.setId(findComment.get().getId());
            requestDto.setContent(findComment.get().getContent());
            requestDto.setPostEntity(findComment.get().getPostEntity());
        }

        return requestDto;
    }

    public void editComment(CommentRequestDto requestDto) {
        CommentEntity commentEntity = commentRepository.findById(requestDto.getId()).get();
        commentEntity.setContent(requestDto.getContent() + " (수정됨)");
        commentRepository.save(commentEntity);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
