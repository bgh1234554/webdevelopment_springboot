package me.BaekJiHoon.springbootdeveloper.config.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.config.jwt.TokenProvider;
import me.BaekJiHoon.springbootdeveloper.domain.User;
import me.BaekJiHoon.springbootdeveloper.repository.RefreshTokenRepository;
import me.BaekJiHoon.springbootdeveloper.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
//OAuth2 인증 성공시 호출되는 기본 핸들러.
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String DEFAULT_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oauth2User.getAttributes().get("email"));
        //동일한 메서드를 사용해 2개의 토큰을 만든다.
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(),refreshToken);
        addRefreshTokenToCookie(request,response,refreshToken);
        String accessToken = tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);
        clearAuthenticationAttributes(request,response);
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {

    }

    private void saveRefreshToken(Long id, String refreshToken) {
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequestCookies(request,response);
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH).queryParam("token",token)
                .build().toUriString();
    }
}
/*
    현재 상황
    Email을 통한 유저 수정이 일어나고 있음.
    OAuth를 위한 로직이 어느정도 구현됐으므로, 작성한 글에 글쓴이를 추가하는 작업을 진행한다.
 */