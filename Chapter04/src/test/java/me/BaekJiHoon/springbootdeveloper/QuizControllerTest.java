package me.BaekJiHoon.springbootdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("quiz(): GET /quiz?code=1 이면 응답코드는 201, 응답 본문은 Created!를 반환한다.")
    @Test
    public void getQuiz1() throws Exception{
        //given
        final String url = "/quiz";
        //when
        final ResultActions result = mockMvc.perform(get(url).param("code","1"));
        //values가 "1"인 이유는 JSON이라서.
        //then
        result.andExpect(status().isCreated()).andExpect(content().string("Created!"));
    }

    @DisplayName("quiz(): GET /quiz?code=2 이면 응답코드는 400, 응답 본문은 Bad Request!를 반환한다.")
    @Test
    public void getQuiz2() throws Exception{
        //given
        final String url = "/quiz";
        //when
        final ResultActions result = mockMvc.perform(get(url).param("code","2"));
        //then
        result.andExpect(status().isBadRequest()).andExpect(content().string("Bad Request!"));
    }

    @DisplayName("quiz(): POST /quiz?code=1 이면 응답코드는 403, 응답 본문은 Forbidden!를 반환한다.")
    @Test
    public void getQuiz3() throws Exception{
        //given
        final String url = "/quiz";
        //when //Code(1)을 JSON으로 변환해줌. 코드 밑 주석 objectMapper 참조.
        //그러니까 위의 "code":"2"를 내가 만든 객체로 할 수 있게 함.
        //JSON으로 변환했으니까 앞의 TestControllerTest의 MediaType.APPLICATION_JSON이 필요한듯...?
        final ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Code(1))));
        //then
        result.andExpect(status().isForbidden()).andExpect(content().string("Forbidden!"));
    }

    @DisplayName("quiz(): POST /quiz?code=99 이면 응답코드는 200, 응답 본문은 OK!를 반환한다.")
    @Test
    public void getQuiz4() throws Exception{
        //given
        final String url = "/quiz";
        //when
        final ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new Code(99))));
        //then
        result.andExpect(status().isOk()).andExpect(content().string("OK!"));
    }
}
/*
ObjectMapper: Jackson 라이브러리에서 제공하는 클래스로 객체와 JSON간 변환을 처리한다.
Code code = new Code(13); //QuizController.java에서 확인하기
objectMapper.writeValueAsString(code);

-> new Code(13)을 통해서 Code() 객체를 생성했고, writeValueAsString 메서드를 통해서 JSON문자열 형태로 객체가 변환된다.
이런 식으로, 객체 -> JSON 문자열로의 변화를 '객체 직렬화'라고 한다.
JSON 문자열로의 변환 예서
{
'value':13
}
 */