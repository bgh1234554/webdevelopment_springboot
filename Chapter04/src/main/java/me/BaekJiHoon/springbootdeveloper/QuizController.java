package me.BaekJiHoon.springbootdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //Controller에서 자주 보는 표현.
public class QuizController {

    @GetMapping("/quiz") //(1)
    public ResponseEntity<String> quiz(@RequestParam("code") int code){
        switch(code){
            case 1:
                return ResponseEntity.created(null).body("Created!");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }

    @PostMapping("/quiz") //(2)
    public ResponseEntity<String> quiz2(@RequestBody Code code){
        switch(code.value()){
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }
}

//!!Annotation 잘 읽기!!
//JAVA 14에 새로 추가된 키워드. 기본 생성자와 게터 세터를 자동으로 제공하며,
//필드는 자동으로 final로 선언된다.
record Code(int value) {} //(3)

/*
        1. quiz 패스로 GET 요청이 오면 quiz()라는 메서드에서 요청을 처리. 이 메서드는 요청 파라미터의 키가 "code"
            이면 int 자료형의 code 변수와 매핑되며, code 값에 따라 다른 응답을 보냅니다.

        2. quiz 패스로 POST 요청이 오면 quiz2()라는 메서드에서 요청을 처리. 이 메서드는 요청 값을 Code라는 객체로
            매핑 후에 value 값에 따라 다른 응답을 보냅니다.

        3. 2에서 매핑할 객체로 사용하기 위해 선언한 레코드입니다. 레코드는 2장 스프링 부트 3와 자바 버전에서 잠깐
            설명한 기능. 데이터 전달을 목적으로 하는 객체를 더 빠르고 간편하게 만들기 위한 기능으로 레코드를 사용하면
            필드, 생성자, 게터, equals(), hashCode(), toString() 메서드 등을 자동으로 생성합니다.
 */