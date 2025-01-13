package me.BaekJiHoon.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ArticleViewResponse(Article article){
        this.id=article.getId();
        this.content=article.getContent();
        this.createdAt=article.getCreatedAt();
    }
}
