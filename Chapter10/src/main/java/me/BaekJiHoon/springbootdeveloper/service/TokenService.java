package me.BaekJiHoon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.config.jwt.TokenProvider;
import me.BaekJiHoon.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        //토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("unexpected token");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
    /*
        토큰 생성 및 유효성 검증 로직이 작성된거임.
       -> 실제로 토큰을 발급받는 API를 생성.

       토큰 생성 요청 및 응답을 담당할 DTO를 생성한다.
       CreateAccessTokenRequest.java
       CreateAccessTokenResponse.java
     */
}
