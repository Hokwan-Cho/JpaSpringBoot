package com.kh.JpaSpringBoot.web;

import com.kh.JpaSpringBoot.domain.posts.Posts;
import com.kh.JpaSpringBoot.service.posts.PostsService;
import com.kh.JpaSpringBoot.web.dto.PostsResponseDto;
import com.kh.JpaSpringBoot.web.dto.PostsSaveRequestDto;
import com.kh.JpaSpringBoot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.udpate(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findbyId(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete (@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
