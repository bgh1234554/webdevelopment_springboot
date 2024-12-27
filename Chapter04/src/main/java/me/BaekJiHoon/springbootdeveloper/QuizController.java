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

//JAVA 14에 새로 추가된 키워드. 기본 생성자와 게터 세터를 자동으로 제공하며,
//필드는 자동으로 final로 선언된다.
record Code(int value) {} //(3)

/*
    QuizControllerTest.java 파일 만들기
 */