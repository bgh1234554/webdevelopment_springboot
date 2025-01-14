package me.BaekJiHoon.springbootdeveloper.dto;

import lombok.Getter;
import me.BaekJiHoon.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {
    //final - 생성자에 필수적으로 들어가야 한다.
    private final String title;
    private final String content;
    //글을 읽을거기 때문에 Article을 받아온다.
    public ArticleResponse(Article article){
        this.title=article.getTitle();
        this.content=article.getContent();
    }
    /*
        글은 제목과 내용 구성으로 이루어져 있으므로 해당 필드를 가지는 클래스를 만들었다.
        -> ArticleResponse 클래스를 만들었다는 의미.

        Entity를 매개변수로 받는 생성자를 추가 -> lombok이 지원하지 않기 때문.
     */
}
