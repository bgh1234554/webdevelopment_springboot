package me.BaekJiHoon.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    public Article toEntity(){ //생성자를 이용해 객체를 생성.
        //Entity 형태로 바꿔준다. 우리가 만든 Entity는 Article 형태니까 그 형태로 바꾼다.
        return Article.builder().title(title).content(content).build();
        //저기 builder의 메서드로 있는 title과 content는 저 Article 클래스에 있는 title과 content를 말하는거임.
    }
    /*
        toEntity()는 빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드이다.
        이 메서드는 추후 블로그에 글을 추가할 때 저장할 엔티티로 변환하는 용도로 사용된다.
     */
}
