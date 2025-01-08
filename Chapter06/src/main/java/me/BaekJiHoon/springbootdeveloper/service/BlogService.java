package me.BaekJiHoon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;
import me.BaekJiHoon.springbootdeveloper.dto.AddArticleRequest;
import me.BaekJiHoon.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
//서비스는 Repository와 통신을 한다.
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
        //ArticleRequest 클래스에 있는 toEntity를 이용해서 Article 타입으로 바꾼 다음에 저장한다.
    }
    /*
    @Service : 해당 클래스를 빈으로 서블릿 컨테이너에 등록해준다.
    save() : JpaRepository에서 지원하는 저장 메서드. AddArticleRequest 클래스에 저장된 값들을
    article 데이터베이스에 저장한다. (article은 테이블 명이라 클래스와 달리 소문자로 시작한다.)

    -> 글을 생성하는 서비스 계층에서의 코드 작성이 완료.
    -> 컨트롤러 메서드 코드 작성하기. URL에 매핑하기 위한 컨트롤러 메서드를 추가함.
    -> 컨트롤러 메서드 구현 자체는 한 적이 있는데, 컨트롤러 메서드에는 URL 매핑 Annotation인
    @PostMapping @GetMapping @PutMapping @DeleteMapping 등을 사용할 수 있다.
    이름에서 유추할 수 있듯이 각 Annotation들은 HTTP 메서드와 대응한다.

   /api/articles에 POST 요청이 들어오면 @PostMapping을 이용해 요청을 매핑한 뒤,
   블로그 글을 생성하는 BlogService의 save()메서드를 호출
   -> 생성된 블로그 글을 반환하는 작업을 하는 addArticle() 메서드를 작성할 예정.
     */
}
