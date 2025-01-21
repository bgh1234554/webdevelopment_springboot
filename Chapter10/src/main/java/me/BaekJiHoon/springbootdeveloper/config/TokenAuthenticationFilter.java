package me.BaekJiHoon.springbootdeveloper.config;

//액세스 토큰이 담긴 Authorization 헤더 값을 가져온 뒤에 액세스 토큰이 유효하다면 인증 정보를 생성한다.

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //요청 헤더의 Authorization 키의 키값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        //가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        //가져온 토큰이 유효한지 확인, 유효하면 인증 정보 설정
        if(tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String getAccessToken(String authorizationHeader){
        if(authorizationHeader!=null&&authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    /*
    요청 헤더에서 키가 Authorization인 필드의 값을 가져온 다음, 토크느이 접두사 Bearer을 제거한 값을 얻는다.
    만약 null이거나 Bearer로 시작되지 않으면 null을 반환, (getAccessToken()을 통과했는지 여부가 반환됨)
    이어서 가져온 토큰이 유효한지 확인하고, 유효하다면 인증 정보를 관리하는 시큐리티 컨텍스트에 인증 정보를 저장한다.
    위에서 작성한 코드가 실행되며 인증 정보가 설정된 이후에 컨텍스트 홀더에 getAuthentication() 메서드를 사용해
    인증 정보를 가져오면 유저 객체가 반환된다.
    유저 객체에는 유저 이름(username)과 권한 목록(Authorities)과 같은 인증 정보가 포함되어 있다.

    토큰 API를 구현
        리프레시 토큰을 전달 받아서 검증, 유효한 리프레시 토큰이라면 새로운 액세스 토큰을 생성하는 토큰 API를 구현.
        토큰 서비스, 토큰 컨트롤러를 차례로 구현한다.
     */
}
