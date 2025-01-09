package com.hj.springboot.config.auth.dto;

import com.hj.springboot.web.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
    User Entity 클래스 직렬화 구현 대신 SessionUser Dto 클래스 사용
    Entity 클래스를 직렬화하게 되면 연관 Entity도 모두 직렬화 대상이 된다.
    성능 이슈, 부수 효과 발생 가능
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
