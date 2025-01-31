package me.BaekJiHoon.springbootdeveloper.repository;

import me.BaekJiHoon.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
/*
JpaRepository 클래스를 상속 받을 때, 엔티티 Article과 PK의 Long을 Argument로 받았다.
해당 Repository를 사용할때, JpaRepository에서 제공하는 여러 메서드 사용 가능.

블로그 글 작성을 위한 API 구현하기
    구현 과정:
        1. Service 클래스에서 method 구현
        2. Controller 클래스에서 사용할 method 구현
        API를 실제로 테스트할 예정.

                   요청                    save()                   save()
        클라이언트 <----> 2. 컨트롤러 <-------------------> 1. 서비스 <------> 리포지토리
                   응답  (BlogController.java)          (BlogService.java)  (BlogRepository.java)
                   POST
               /api/articles

        서비스 코드 메서드 작성
            Create
            서비스 계층에서 요청을 받을 객체엔 AddArticleRequest 객체 생성,
            BlogService 클래스를 생성 후에 -> 글 추가 메서드인 save() 구현.

            repository 패키지와 같은 라인에 dto 패키지 생성
            -> 컨트롤러에서 요청한 본문을 받을 객체인 AddArticleRequest.java 파일을 생성.

                DTO (Data Transfer Object) - 계층끼리 데이터를 교환하기 위해 사용하는 객체
                DAO는 데이터베이스와 연결되고, 데이터를 조회하고 수정하는 사용되는 객체라 비교가 필요하다.
                DAO의 경우에는 데이터 수정 관련된 로직이 포함되지만,
                DTO는 단순하게 데이터를 옮기기 위해 사용하는 전달자 역할
                -> 그래서 별도의 비지니스 로직이 필요하지 않다.

            이후 service와 동일한 라인에 controller 패키지 생성 후, BlogApiController.java 생성.

            //혼자 정리
            클라이언트에서 글을 적는다, 글을 적으면 @PostMapping된 함수를 호출하고, 매개변수에 있는
            @ResponceBody가 붙어있는 매개변수 함수 호출해서, 값을 넘겨준다.
            이후 Service 객체한테 넘겨주면 toEntity를 이용해 Request를 Article로 만들고,
            BlogRepository를 통해 DB에 저장한 뒤에, 결과값으로 저장된 Article을 돌려준다.
            이후 브라우저 창에 CREATED status를 돌려주면서 저장된 글을 보여준다.
 */
