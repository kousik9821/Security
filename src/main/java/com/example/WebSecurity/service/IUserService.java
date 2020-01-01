package com.example.WebSecurity.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.example.WebSecurity.entity.Article;
import com.example.WebSecurity.entity.User;
import com.example.WebSecurity.exception.RecordNotFoundException;

public interface IUserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
    public List<Article> getAllArticles();
   @Secured("USER")
    public Article getArticleeById(Long id) throws RecordNotFoundException ;
    public Article createOrUpdateEmployee(Article entity) ;
}
