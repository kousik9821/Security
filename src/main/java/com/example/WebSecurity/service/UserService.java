package com.example.WebSecurity.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.WebSecurity.entity.Article;
import com.example.WebSecurity.entity.Role;
import com.example.WebSecurity.entity.User;
import com.example.WebSecurity.exception.RecordNotFoundException;
import com.example.WebSecurity.repository.ArticleRepositoy;
import com.example.WebSecurity.repository.RoleRepository;
import com.example.WebSecurity.repository.UserRepository;
@Service("userService")
public class UserService implements IUserService{
	 private UserRepository userRepository;
	    private RoleRepository roleRepository;
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	    private ArticleRepositoy articleRepository;

	    @Autowired
	    public UserService(UserRepository userRepository,
	                       RoleRepository roleRepository,
	                       BCryptPasswordEncoder bCryptPasswordEncoder,ArticleRepositoy articleRepositoy) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	        this.articleRepository=articleRepositoy;
	    }

	    public User findUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }

	    public void saveUser(User user) {
	        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setActive(1);
	        Role userRole = roleRepository.findByRole("ADMIN");
	        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	        userRepository.save(user);
	    }
	    public List<Article> getAllArticles()
	    {
	        List<Article> result = (List<Article>) articleRepository.findAll();
	         
	        if(result.size() > 0) {
	            return result;
	        } else {
	            return new ArrayList<Article>();
	        }
	    }
	 
	    public Article getArticleeById(Long id) throws RecordNotFoundException 
	    {
	        Optional<Article> employee = articleRepository.findById(id);
	         
	        if(employee.isPresent()) {
	            return employee.get();
	        } else {
	            throw new RecordNotFoundException("No employee record exist for given id");
	        }
	    }
	    public Article createOrUpdateEmployee(Article entity) 
	    {
	        if(entity.getArticleId()== null) 
	        {
	            entity = articleRepository.save(entity);
	             
	            return entity;
	        } 
	        else
	        {
	            Optional<Article> employee = articleRepository.findById(entity.getArticleId());
	             
	            if(employee.isPresent()) 
	            {
	            	Article newEntity = employee.get();
	            	newEntity.setCategory(entity.getCategory());
	                newEntity.setTitle(entity.getTitle());
	                newEntity = articleRepository.save(newEntity);
	                 
	                return newEntity;
	            } else {
	                entity = articleRepository.save(entity);
	                 
	                return entity;
	            }
	        }
	    } 
	     
}
