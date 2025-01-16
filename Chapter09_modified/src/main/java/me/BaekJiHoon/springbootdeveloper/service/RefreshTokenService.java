package me.BaekJiHoon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.RefreshToken;
import me.BaekJiHoon.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new IllegalArgumentException("unexpected user"));
    }
    // TokenService.java를 만들어서 리프레시 토큰의 유효성 검사를 진행하고, 리프레시 토큰으로 유저 ID 찾을거다.
    // 유저 ID로 해당 유저를 찾은 후에 generateToken() 메서드를 호출하여 새로운 토큰을 발급해준다.
}
