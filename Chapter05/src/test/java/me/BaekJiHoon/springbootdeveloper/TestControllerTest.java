package me.BaekJiHoon.springbootdeveloper;
/*
    Test 클래스를 만드는 방법
    1) main/java 내의 테스트하고자 하는 클래스를 열고, public class 테스트명에서 테스트 명을 클릭 후
    2) Alt+Enter을 누르면 팝업이 나오고, 테스트 만들기를 클릭한다. JUnit5로 테스트를 고정.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //테스트용 Annotation 컨텍스트 생성
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성. 테스트를 확인하기 위한 Annotation. Mock은 Mock Up의 Mock임.
class TestControllerTest {
    @Autowired
    protected MockMvc mockMvc; //빈 주입. new 안들어가도 이렇게 생성함.

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        //builder().build()의 응용형태 - 객체 생성을 편하게 하기 위해. (클래스의 필드를 몰라도 생성할 수 있다.)
    }

    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
        //given
        final String url = "/test"; //이렇게 자주 사용
        Member savedMember = memberRepository.save(new Member(1L,"홍길동" )); //참조 자료형
        //객체 하나를 생성후 데이터를 저장함.
        //GitHub web_development_java에 c15_reference 참조.

        //when
        final ResultActions result = mockMvc.perform(get(url) //(1)
                .accept(MediaType.APPLICATION_JSON)); //(2) import static 구문 확인.
        //json의 자료형은 String이다.
        //DB의 객체를 String 형태로 가져옴.
        //then
        result.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(savedMember.getId())) //(3, isOk까지), (4)
                .andExpect(jsonPath("$[0].name").value(savedMember.getName())); //
        //isOk 저장이 잘 됐느냐.
        //ID가 getId와 동일하냐, name이 getName과 동일하냐를 확인한다.

        /*
        (1): perform() 메서드의 요청을 전송하는 역할. return값으로 ResultActions를 전달.
        ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공함.
        (2): accept() 메서드는 요청을 보낼때 무슨 타입으로 응답을 받을지 결정하는 메서드. 우리는 JSON 사용 예정
        (3): andExpect() 메서드는 응답을 검증. TestController에서 만든 API는 응답으로 OK(200)을 반환하므로
        이에 해당하는 메서드인 isOk()를 사용해 응답 코드가 200(OK)인지 확인.
        (4): jsonPath("$[0].{필드명}")은 JSON 응답값의 value를 가져오는 역할을 하는 메서드.
        0번째 배열에 들어있는 객체의 id, name의 값을 가져오고 저장된 값과 같은지 확인
        (memberRepository.savedMember.getId()등을 이용해서)

        (현재는 흐름을 익히는 용도임)

        HTTP 주요 응답 코드

         */
    }
}
/*
    @SpringBootTest: 애플리케이션 클래스에 추가하는 애너테이션인 @SpringBootApplication이 있는 클래스를 찾고,
    그 클래스에 포함되어 있는 빈을 찾은 다음, 테스트용 애플리케이션 컨텍스트를 생성한다.
    @AutoConfigureMockMvc: MockMvc를 생성하고 자동으로 구성하는 Annotation으로, 애플리케이션을 서버에 배포하지 않고도
    테스트용 MvC 환경을 만들어 요청, 전송, 응답 기능을 제공하는 유틸리티 서비스.
    -> "컨트롤러를 테스트할 때 사용하는 클래스" (컨트롤러 - Web Browser와 가장 긴밀하게 연결되어 있는 클래스)
 */