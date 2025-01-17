package me.BaekJiHoon.springbootdeveloper.repository;

import me.BaekJiHoon.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}