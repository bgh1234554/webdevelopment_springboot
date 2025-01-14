package me.BaekJiHoon.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;
import me.BaekJiHoon.springbootdeveloper.dto.AddArticleRequest;
import me.BaekJiHoon.springbootdeveloper.dto.UpdateArticleRequest;
import me.BaekJiHoon.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Article> findAll(){
        return blogRepository.findAll();
    }
    /*
        JpaRepository에서 지원하는 findAll()을 호출해 article 테이블에 저장되어 있는 모든 데이터를 조회

        응답을 위한 dto 생성. ArticleResponse.java 만들기.
        (글을 올리는 건 request, 결과값을 받는건 response.)
        (dto 패키지 하에 Request와 Response 패키지를 따로 만드는 경우도 있다.)
        -> BlogApiController로 이동.
     */
    //특정 아이디를 가진 글만 조회하기. getMapping에 해당하는 메서드 하나 더 써보기.
    public Article findById(long id){ //id는 PK니까 단 하나밖에 존재하지 않는다.
        //JAVA 9에 추가된 Optional 클래스에 있는 메서드.
        return blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("not found"+id)); //null일 경우를 대비하기 위한 orElse
        //Service 작성했으니까 Controller 이동.
    }
    //삭제 메서드 정의
    public void delete(long id){
        blogRepository.deleteById(id);
    }
    //글 업데이트
    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("not found"+id));
        //domain->Article에서 정의한 update()를 사용.
        article.update(request.getTitle(),request.getContent());
        return article;
    }
    /*
    @Transaction
    트랜잭선 - DB에서 데이터를 바꾸기 위한 작업 단위.
        ex) 계좌 이체 시 다음과 같은 과정을 거친다고 가정하다.
            1) A계좌에서 출금
            2) B계좌에 입금
        그런데, 1)까지만 성공하고, 2)가 실패하면 출금은 됐는데 입금이 되지 않는 사태가 일어난다.
        이런 상황이 발생하지 않도록 하기 위해 출금과 입금을 "트랜잭션"이라는 하나의 작업 단위로 묶어,
        두 작업을 한 단위로 실행하면 된다. 중간에 실패한다면 트랜잭션의 처음 상태로 되돌아간다.(ROLLBACK)
     */
}
