package me.BaekJiHoon.springbootdeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class TestController {
    @Autowired //TestService 빈 주입.
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers(){
        List<Member> members=testService.getAllMembers();
        return members;
    }
}

//여기서 사용자한테 요청 받고