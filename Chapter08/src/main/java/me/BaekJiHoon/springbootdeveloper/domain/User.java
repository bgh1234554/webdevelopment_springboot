package me.BaekJiHoon.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails { //UserDetails를 상족 받아 인증 객체로 사용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="email",nullable = false,unique = false)
    private String email;

    @Column(name="password")
    private String password;

    @Builder
    public User(String email, String password, String auth){
        this.email=email;
        this.password=password;
    }

    @Override //권한을 반환해준다.
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id를 반환 (고유한 값)
    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; //만료되지 않았다는 의미.
    }

    //계정 잠금 여부 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
/*
    User 클래스가 상속한 UserDetails 클래스는 스프링 시큐리티에서 사용자의 인증 정보를 담아두는 인터페이스,
    스프링 시큐리티에서 해당 객체를 통해 인증 정보를 가져오려면 필수 오버라이드 메서드들을 여러개 사용해야 하기 때문에
    입력해야할 코드가 많다.
 */