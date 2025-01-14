package me.BaekJiHoon.springbootdeveloper.dto;

import lombok.Getter;
import me.BaekJiHoon.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article){
        this.id=article.getId();
        this.title= article.getTitle();
        this.content= article.getContent();
    }
}
//Article을 받아서 html에 뿌려줄 객체가 필요하다.
//BlogViewController 파일에서 사용할 에정.