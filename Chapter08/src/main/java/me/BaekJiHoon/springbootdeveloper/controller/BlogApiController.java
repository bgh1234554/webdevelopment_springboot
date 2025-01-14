package me.BaekJiHoon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;
import me.BaekJiHoon.springbootdeveloper.dto.AddArticleRequest;
import me.BaekJiHoon.springbootdeveloper.dto.ArticleResponse;
import me.BaekJiHoon.springbootdeveloper.dto.UpdateArticleRequest;
import me.BaekJiHoon.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //역할 명시
public class BlogApiController {
    private final BlogService blogService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 지금 정의하는 메서드와 매핑
    @PostMapping("api/articles")
    //@RequestBody로 요청 본문값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle); //안에 id, title, content 들어가 있음.
        //음식 주문후 음식이 돌아오는 응답이 return됨.
    }
    /*
        1. @RestController : 클래스에 붙이면 HTTP 응답으로 객체 데이터를 .json 형식으로 반환한다.
        2. @PostMapping() : HTTP 메서드가 POST일 때 요청받은 URL과 동일한 메서드와 매핑.
            지금의 경우 /api/articles는 addArticle() 메서드와 매핑.
        3. @RequestBody: HTTP 요청을 할 때, 응답에 해당하는 값을
        @RequestBody Annotation이 붙은 대상 객체인 AddArticleRequest에 매핑.
        (알아서 AddArticleRequest 메서드를 호출한다는 느낌?)
        4. ResponseEntity.status().body()는 응답코드로 201, 즉 Created를 응답하고 테이블에 저장된 객체를 반환한다.
        body에 표시할 내용을 담는다.

        200 OK
        201 Created
        400 Bad Request
        403 Forbidden
        404 Not Found
        500 Internal Server Error

        API가 잘 동작하는 지 테스트틀 하나 해볼 예정.
            H2 콘솔 활성화
            - Application.yml 파일 수정
            - 이후 postman 실행
            HTTP 메서드 - POST (Postman에 url 옆에 POST로 변경)
            이후 Body -> raw 선택 후
            {
                "title":"제목",
                "content":"내용"
            }
            작성 후 SEND 버튼 눌러서 요청.

            결과 창의 Body에 pretty모드(사람이 보기 좋게 정리해준 것)로 결과를 보여줬다.
            -> 스프링 부트 서버에 저장된 것.

            HTTP 메서드 POST로 서버에 요청을 한 후에 값을 저장하는 과정.

            이후 localhost:8080/h2-console 입력
            이후 JDBC URL에 jdbc:h2:mem:testdb 입력
            -> SQL문 (SELECT * FROM article;) 작성. -> postman에 입력한 정보가 나온다.

            실행 창에 " Hibernate: insert into article (content,title,id) values (?,?,default) "
            라는 문구가 POSTMAN을 통해 POST 명령을 실행할 때마다 올라온다.

            H2 DB에 저장된 데이터를 확인할 수 있다.

            요약
            어플리케이션을 실행하면 자동으로 생성한 엔티티 내용을 바탕으로 테이블이 생성되고,
            우리가 요청한 POST 요청에 의해 INSERT 문이 실행되어 실제로 데이터가 저장되었다.
     */

    //글을 전부 읽어온다.
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll().stream().map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(articles);
    }
    /*
        /api/articles GET 요청이 들어오면 Service 계층의 글 전체를 조회하는 findAll() 메서드를 호출.
        -> 다음 응답용 객체인 ArticleResponse를 파싱해서 body에 담아 클라이언트에 전송
        -> 여기선 .stream() 을 사용해 적용함

        * stream - JAVA 8에 추가된 기능.
     */
    @GetMapping("/api/articles/{id}")
    //URL 경로에서 id 값을 추출해서 가지고 온다.
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        // URL에서 {id}에 해당하는 값이 id로 들어가게 한다.
        Article article = blogService.findById(id);
        //글 하나니까 새로 객체를 다른 줄로 만들 필요 없이 바로 new 생성자로 만든다.
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }
    /*
    @PathVariable - URL에서 값을 가져오는 Annotation이다.
        /api/articles/3 GET 요청을 받으면 id에 3이 argument로 들어오게 된다.
        이 값이 서비스 클래스의 findById() 메서드로 넘어가서 3번 블로그 글을 찾아온다.
        그 글을 찾으면 3번 글의 정보를 body에 담아서 웹브라우저로 가지고 온다.
     */
    @DeleteMapping("/api/articles/{id}")
    //void도 참조 클래스가 있다.
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok().build(); //body에 담을 것이 없어서 build 사용.
    }
    /*
        @PathVariable 통해서 {id}에 해당하는 값이 들어온다.
        이후 POSTMAN 통해 확인하기
        GET으로 호출하면 sql에 저장된 데이터가 나오고
        이후 DELETE로 .../1 SEND, 다시 GET로 확인.
        사라져있는 것을 확인할 수 있음.
        이제 .../1로 GET해도 조회가 안됨.

        확인 이후 테스트 코드 작성.
     */
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id,request);
        return ResponseEntity.ok().body(updatedArticle);
    }
    /*
        "/api/articles/{id}" PUT 요청이 들어오면 Request Body 정보가 request로 넘어온다.
        그리고 다시 서비스 클래스의 update()메서드에 id와 request를 넘겨준다.
        응답 값은 body에 담아 전송한다.

        POSTMAN에 PUT으로 설정하고 /api/articles/1 주소 들어가서 내용을 바꾸고 SEND
        이후 GET을 하면 내용이 바뀐 것을 확인할 수 있다.
     */
}
