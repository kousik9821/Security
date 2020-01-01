package com.example.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.example.WebSecurity.entity.Article;
@Repository
//@PreAuthorize("hasRole('ADMIN')")
public interface ArticleRepositoy  extends CrudRepository<Article, Long>{

}
