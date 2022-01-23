package com.kh.JpaSpringBoot.web;

import com.kh.JpaSpringBoot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/*
error 해결 1
WebMvcTest는 CustomOAuth2UserService 를 스캔하지 않음.
@WebMvcTest
- 읽는 것: WebSecurityConfigurerAdapter, WebMvcConfigurer,  @ControllerAdvice, @Controller
- 읽지 않는 것: @Repository, @Service, @Component, ...

결국 SecurityConfig는 읽었지만, SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService를 읽을 수 없어서 에러 발생
해결방법: SecurityConfig를 스캔 대상에서 제외 후 @WithMockUser를 통해 가짜 인증 사용자 등록

error 해결 2
Application.java를 보면 @EnableJpaAuditing 어노테이션이 있는데, 이를 사용하기 위해선 최소 하나의 @Entity 클래스가 필요하다.
하지만 아래 코드는 @WebMvcTest이다보니 Entity가 없다.
그래서 Application과 @EnableJpaAuditing을 분리해야함 (JpaConfig 생성)
 */


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class, excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) })
public class HelloControllerTest {

    @Autowired private MockMvc mvc;

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name",name)
                        .param("amount", String.valueOf(amount))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));

    }
}
