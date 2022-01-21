package com.kh.JpaSpringBoot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이 생성될 위치 지정 (parameter는 파라미터로 선언된 객체에 사용하곘다는 뜼)
@Retention(RetentionPolicy.RUNTIME)  //어노테이션의 라이프사이클을 결정 (runtime 단계에서 메모리유지)
public @interface LoginUser {

}
