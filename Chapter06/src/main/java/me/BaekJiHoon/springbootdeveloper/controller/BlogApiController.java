package me.BaekJiHoon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;
import me.BaekJiHoon.springbootdeveloper.dto.AddArticleRequest;
import me.BaekJiHoon.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
