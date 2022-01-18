package com.kh.JpaSpringBoot.service.posts;

import com.kh.JpaSpringBoot.domain.posts.Posts;
import com.kh.JpaSpringBoot.domain.posts.PostsRepository;
import com.kh.JpaSpringBoot.web.dto.PostsListResponseDto;
import com.kh.JpaSpringBoot.web.dto.PostsResponseDto;
import com.kh.JpaSpringBoot.web.dto.PostsSaveRequestDto;
import com.kh.JpaSpringBoot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @org.springframework.transaction.annotation.Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }


    @org.springframework.transaction.annotation.Transactional
    public Long udpate(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" +id ));
        posts.udpate(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity =postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly=true )
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional
    public void delete(Long id){
        Posts posts =postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
