package com.example.blogsystemsecurity.Service;


import com.example.blogsystemsecurity.API.ApiException;
import com.example.blogsystemsecurity.Model.Blog;
import com.example.blogsystemsecurity.Model.User;
import com.example.blogsystemsecurity.Repository.AuthRepository;
import com.example.blogsystemsecurity.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;


    public List getAllBlog(){

        return blogRepository.findAll();
    }

    public List getMyBlog(Integer auth){
        User user = authRepository.findUserById(auth);
        List<Blog> blogs = blogRepository.findAllByUser(user);
        return blogs;
    }

    public void addToMyBlog(Blog blog, Integer auth){
        User user = authRepository.findUserById(auth);
        blog.setUser(user);
        blogRepository.save(blog);
    }
    public void updateBlog(Integer id , Blog newBlog , Integer auth){
        Blog oldBlog=blogRepository.findBlogById(id);
        User user=authRepository.findUserById(auth);
        if (oldBlog==null){
            throw new ApiException("Blog not found");
        }else if(oldBlog.getUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to update this Todo!");
        }

        newBlog.setId(id);
        newBlog.setUser(user);


        blogRepository.save(newBlog);
    }

    public void deleteBlog(Integer id, Integer auth){
        Blog blog=blogRepository.findBlogById(id);
        if (blog==null){
            throw new ApiException("blog not found");
        }else if(blog.getUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to delete this Todo!");
        }

        blogRepository.delete(blog);
    }

    public List<Blog> getBlog(String title){
        List<Blog> blogs = blogRepository.findAllByTitle(title);
        if (blogs.isEmpty()){
            throw new ApiException("blog not found");
        }

        return blogs;

    }







}
