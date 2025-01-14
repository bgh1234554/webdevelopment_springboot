package me.BaekJiHoon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.User;
import me.BaekJiHoon.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    //사용자 이름으로 사용자 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException((email)));
    }
}
/*
스프링 시큐리티에서 사용자 정보를 가져오는 UserDetailsService 인터페이스를 구현
필수로 구현해야하는 loadUserByUsername() 메서드를 오버라이딩해서 사용자 정보를 가져오는 로직을 완성했다.

시큐리티 설정 파일을 만들 예정
    config 패키지 생성 후 WebSecurityConfig.java 클래스 생성.
 */