package com.wheon.edu_postboard.controller;

import com.wheon.edu_postboard.dto.*;
import com.wheon.edu_postboard.entity.CommentEntity;
import com.wheon.edu_postboard.entity.PostEntity;
import com.wheon.edu_postboard.service.CommentService;
import com.wheon.edu_postboard.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/main")
    public String mainP(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute(postService.loadPosts(keyword));
        return "main";
    }

    @PostMapping("/search")
    public String searchProc(@RequestParam("keyword") String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            return "redirect:/main?keyword=" + encodedKeyword;
        } catch (UnsupportedEncodingException e) {
            return "redirect:/main";
        }
    }

    @GetMapping("/main/write")
    public String writeP(Model model, WritePostDto writePostDto) {
        model.addAttribute(writePostDto);
        return "write";
    }

    @PostMapping("/main/writeProc")
    public String writeProc(@ModelAttribute("writePostDto") WritePostDto writePostDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginResponseDto userInfo = (LoginResponseDto) session.getAttribute("userInfo");
        writePostDto.setUsername(userInfo.getUsername());
        postService.savePost(writePostDto);
        return "redirect:/main";
    }

    @RequestMapping("/main/detail")
    public String postP(@RequestParam Long postId, Model model, SaveCommentDto saveCommentDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginResponseDto userInfo = (LoginResponseDto) session.getAttribute("userInfo");
        PostRequestDto requestDto = postService.getPost(postId);
        List<CommentEntity> commentEntities = commentService.loadComments();
        model.addAttribute(requestDto);
        model.addAttribute(userInfo);
        model.addAttribute(saveCommentDto);
        model.addAttribute(commentEntities);
        return "post";
    }

    @PostMapping("/main/saveCommentProc")
    public String saveCommentProc(@ModelAttribute("saveCommentDto") SaveCommentDto saveCommentDto, @RequestParam Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginResponseDto userInfo = (LoginResponseDto) session.getAttribute("userInfo");
        commentService.saveComment(saveCommentDto, userInfo, postId);
        return "redirect:/main/detail?postId=" + postId;
    }

    @RequestMapping("/main/post/edit")
    public String postEditP(@RequestParam Long postId, Model model) {
        PostRequestDto requestDto = postService.getPost(postId);
        requestDto.setId(postId);
        model.addAttribute(requestDto);
        return "editPost";
    }

    @RequestMapping("/main/post/delete")
    public String postDeleteProc(@RequestParam Long postId) {
        postService.deletePost(postId);
        return "redirect:/main";
    }

    @PostMapping("/main/post/editProc")
    public String postEditProc(@RequestParam Long postId, @ModelAttribute("editPostDto") EditPostDto editPostDto) {
        editPostDto.setId(postId);
        postService.editPost(editPostDto);
        return "redirect:/main/detail?postId=" + postId;
    }

    @RequestMapping("/main/comment/edit")
    public String commentEditP(@RequestParam Long commentId, Model model) {
        CommentRequestDto requestDto = commentService.getComment(commentId);
        model.addAttribute(requestDto);
        return "editComment";
    }

    @PostMapping("/main/comment/editProc")
    public String commentEditProc(@ModelAttribute("commentRequestDto") CommentRequestDto commentRequestDto, @RequestParam Long commentId) {
        CommentRequestDto requestDto = commentService.getComment(commentId);
        commentRequestDto.setId(commentId);
        commentService.editComment(commentRequestDto);
        return "redirect:/main/detail?postId=" + requestDto.getPostEntity().getId();
    }

    @RequestMapping("/main/comment/delete")
    public String deleteCommentProc(@RequestParam Long commentId) {
        Long postId = commentService.getComment(commentId).getPostEntity().getId();
        commentService.deleteComment(commentId);
        return "redirect:/main/detail?postId=" + postId;
    }


    //todo 검색

}
