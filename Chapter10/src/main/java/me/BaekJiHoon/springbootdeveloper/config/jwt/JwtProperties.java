package me.BaekJiHoon.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티값을 가져와 사용하는 Annotation
public class JwtProperties {
    private String issuer;
    private String secretKey;
    /*
        이렇게 하면 issuer 필드에서 applicatoin.yml에서 생성한 jwt.issuer값이,
        secretKey에는 jwt.secret_key값이 매핑된다.
     */
}
