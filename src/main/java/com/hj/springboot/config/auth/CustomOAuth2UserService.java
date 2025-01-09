package com.hj.springboot.config.auth;

import com.hj.springboot.config.auth.dto.OAuthAttributes;
import com.hj.springboot.config.auth.dto.SessionUser;
import com.hj.springboot.web.domain.user.User;
import com.hj.springboot.web.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 *  구글 로그인 후 가져온 사용자 정보(email, name, picture 등) 기반으로 가입 및 세션저장 등 기능 지원
 *  - Access Token 사용하여 받은 사용자 정보 처리
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 기본서비스 호출
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId로 로그인 진행중인 서비스 구분 (google, naver 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 정보 조회 (google의 경우 기본키: "sub")
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 사용자 정보 매핑
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName
                , oAuth2User.getAttributes());

        // 세션 저장
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        // Spring Security의 OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
