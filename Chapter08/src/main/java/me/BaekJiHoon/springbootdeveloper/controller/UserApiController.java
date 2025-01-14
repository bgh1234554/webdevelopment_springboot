package me.BaekJiHoon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.dto.AddUserRequest;
import me.BaekJiHoon.springbootdeveloper.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request); //회원가입 메서드 호출
        return "redirect:/login"; //회원가입 완료된 이후에 로그인 페이지로 이동.
    }
    /*
        회원 가입 절차가 완료된 이후에 로그인 페이지로 이동하기 위해 "redirect:" 접두사를 붙였다.
        이렇게 하면 회원 가입 절차가 끝났을 때 강제로 /login url에 해당하는 회원으로 이동한다.

        회원 가입, 로그인 뷰 작성하기

            뷰 관련 컨트롤러 구현 예정.
     */
}
