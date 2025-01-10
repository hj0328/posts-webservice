package com.hj.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)          // parameter로 선언된 객체에 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {           // annotation class 지정

}
