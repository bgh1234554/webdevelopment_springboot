package me.BaekJiHoon.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.BaekJiHoon.springbootdeveloper.domain.Article;
import me.BaekJiHoon.springbootdeveloper.dto.AddArticleRequest;
import me.BaekJiHoon.springbootdeveloper.dto.UpdateArticleRequest;
import me.BaekJiHoon.springbootdeveloper.repository.BlogRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성. 테스트용 객체 만들기
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository; //데이터 가져와야 하니까.

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll(); //이전에 있던 기록을 지우고 테스트 할때 새로 실행.
    }
    /*
        ObjectMapper 클래스
        - 이 클래스로 만든 자바 객체를 JSON 데이터로 변환하는 것을 직렬화 (Serialization)
        - 역으로 JSON 데이터를 자바 객체로 변환하는 것을 역직렬화
        직렬화(Serialization) - 자바 객체 -> JSON
        역직렬화(Deserialization) - JSON -> 자바 객체

        HTTP에서는 JSON 데이터를, 자바에서는 객체를 활용하는데, 서로 형식이 다르기 때문에
        형식에 맞게 변환하는 과정이 필요하다. 이 과정을 직렬화/역직렬화라고 부른다.
            1) 직렬화 - Java 시스템 내부에서 사용되는 객체를 외부에서 사용하도록 변환
                ex - title은 제목, context는 내용이라는 값이 들어가 있는 개체가 있다고 할때, 자바 상에서는
                @AllArgsConstructor
                public class Article{  //클래스, 필드, 생성자
                    private String title;
                    private String content;
                    main 메서드{ //객체 생성
                        Article article = new Article("제목","내용");
                    }
                }
                그런데, JSON 상으로는
                {
                    "title":"제목",
                    "content":"내용"
                }
                형태로 정리되는 것을 POSTMAN에서 확인할 수 있었다.
                서로 변환하는 것을 도와주는 것을 ObjectMapper라고 한다.

        글 생성 API를 테스트하는 코드 작성
        Given - 블로그 글 추가에 필요한 요청 객체 생성
        When - 블로그 글 추가 API에 요청을 보낸다. 요청 타입은 JSON,
            given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보낸다.
        Then - 응답 코드가 201 Created 인지 확인.
            Blog를 조회했을 때, 전체 크기가 1인지 확인.
            실제 저장된 데이터와 요청값을 비교. (AssertThat 문을 활용)
     */
    @DisplayName("addArticle: 블로그에 글 추가 성공")
    @Test
    public void addArticle() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title", content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title,content);
        //객체를 JSON 형태로 직렬화 -> ObjectMapper 사용 Value를 String으로 쓰겠다는 메서드가 뜬다.
        final String requestBody = objectMapper.writeValueAsString(userRequest);
        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));
        //then //201 CREATED로 나오는지 확인
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        //저장된 전체 글을 Repository에서 java 객체 형태로 불러와 리스트에 담는다.
        assertThat(articles.size()).isEqualTo(1);
        //List의 element 개수가 1인지 확인한다. Lombok에서 제공된 @getter 덕에 getTitle, getContent가 존재한다.
        assertThat(articles.get(0).getTitle()).isEqualTo("title");
        assertThat(articles.get(0).getContent()).isEqualTo("content");
    }

    /*
    writeValueAsString() 메서드 - 객체를 JSON으로 직렬화.
    그 이후에 MockMvc를 사용해 HTTP 메서드, URL, 요엋ㅇ본문, 요청 타입 등을 설정 뒤 테스트 요청을 보냈다.
    contentType() 메서드 - JSON/XML 등 다양한 타입을 선택할 수 있는데, 여기선 JSON을 사용한다.
    Article객체의 제목, 내용이 "title","content"인지 확인한다.

    <assertThat 메서드 예시>
    assertThat(articles.size()).isEqualTo(1);   - 블로그의 글 크기가 1이어야 합니다.
    assertThat(articles.size()).isGreaterThan(2);   - 블로그의 글 크기가 2보다 커야 합니다.
    assertThat(articles.size()).isLessThan(5);   - 블로그의 글 크기가 5보다 작아야 합니다.
    assertThat(article.title()).isEqualTo("제목");   - 블로그의 글의 title 값이 "제목"이어야 합니다.
    assertThat(article.title()).isNotEmpty();   - 블로그의 글의 title 값이 비어있지 않아야 합니다.
    assertThat(article.title()).contains("제");   - 블로그의 글의 title 값이 "제"를 포함해야 합니다.
     */

    @DisplayName("findAllArticles: 블로그 글 목록 조회 성공")
    @Test
    void findAllArticles() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        //blogRepository에 Article 객체 생성 후 저장
        blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk()).andExpect(jsonPath("$[0].content").value(content));
        result.andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value(title));
        //data.sql은 특정 아이디를 가진 글을 조회하는 test를 작성할 때 사용할 예정.
    }
    @DisplayName("findArticle: 블로그 글 조회 성공")
    @Test
    void findArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        //when
        //특정 id값을 가져와하기 때문에 getId를 사용함.
        final ResultActions resultActions = mockMvc.perform(get(url,savedArticle.getId()));
        //then [0]이 빠졌다.
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.content").value(content));
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    void deleteArticle() throws Exception {
        //given - 1
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        //given - 2 -> 하나만 생성했다가 지운 다음에 List 전체 불러왔는데 크기가 0이면 성공.
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        mockMvc.perform(delete(url,savedArticle.getId())).andExpect(status().isOk());
        //findArticle과 같이 하나의 객체를 대상으로 할 때는, 매개변수로 url뿐만 아니라 getId를 2번째로 줘야 한다.
        //바로 status().isOk() 사용한 것이 특이하다.
        //then - 검증단계에서 블로그 목록을 전부 다 들고온 뒤, size()의 값이 0이거나, isEmpty()가 true인지 확인한다.
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty(); //아무것도 없는지 테스트한다.
        //Test 메서드는 메서드 단위로 실행한다.
        //given 단계에서 생성한 객체 하나만 있다가 그걸 삭제했기 때문에 articles 리스트에는 아무 값도 없어야 한다.
    }

    @DisplayName("updateArticle: 블로그 글 수정 성공")
    @Test
    void updateArticle() throws Exception {
        //given - 1
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        //위 필드 값을 가지는 Article 객체를 builder 패턴으로 생성
        //이후 그 객채의 필드 값들을 수정하고 일치하는지 확인한다.
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        final String newTitle = "newTitle";
        final String newContent = "newContent";
        //DTO를 이용해 새 내용을 포장해서 보낸다.
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle,newContent);
        //when
        ResultActions resultActions = mockMvc.perform(put(url,savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request)));
        //then
        resultActions.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo("newTitle");
        assertThat(article.getContent()).isEqualTo("newContent");
    }
}
//그전에 작성했던 QuizControllerTest와 TestControllerTest와 비교해보자.