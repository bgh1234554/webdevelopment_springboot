package me.BaekJiHoon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.BaekJiHoon.springbootdeveloper.dto.ArticleListViewResponse;
import me.BaekJiHoon.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    //글 하나 보여주는거, 여러개 보여주는 걸 메서드로 정리할 것.
    private final BlogService blogService;

    //api 통하지 않고, 보여주는거니까 바로 연결함.
    @GetMapping("/articles")
    public String getArticles(Model model){
        //Chapter 6 에서 했던 부분
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new).toList();
        //ExampleController.java에서 했던 부분
        model.addAttribute("articles",articles);
        return "articleList";
    }//html에 보여줘야하니까 model이 필요하다
}
