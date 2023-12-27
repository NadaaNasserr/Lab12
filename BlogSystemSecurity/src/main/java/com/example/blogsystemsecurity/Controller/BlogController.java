package com.example.blogsystemsecurity.Controller;


import com.example.blogsystemsecurity.Model.Blog;
import com.example.blogsystemsecurity.Model.User;
import com.example.blogsystemsecurity.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {


    private final BlogService blogService;


    @GetMapping("/getAllBlog")
    public ResponseEntity getAllBlog() {

        return ResponseEntity.status(200).body(blogService.getAllBlog());
    }


    @GetMapping("/get-blogs")
    public ResponseEntity getMyBlog(@AuthenticationPrincipal User user) {

        return ResponseEntity.status(200).body(blogService.getMyBlog(user.getId()));


    }

    @PostMapping("/add")
    public ResponseEntity addBlog(@RequestBody @Valid Blog blog, @AuthenticationPrincipal User user){

        blogService.addToMyBlog(blog, user.getId());
        return ResponseEntity.status(200).body("Blog added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBlog(@RequestBody @Valid Blog blog, @PathVariable Integer id, @AuthenticationPrincipal User user){
        blogService.updateBlog(id,blog,user.getId());
        return ResponseEntity.status(200).body("Todo Updated");
    }

    // User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBlog(@PathVariable Integer id, @AuthenticationPrincipal User user){
        blogService.deleteBlog(id,user.getId());
        return ResponseEntity.status(200).body("Todo deleted");
    }


    @GetMapping("/getBlogByTitle/{title}")
    public ResponseEntity getBlog(@PathVariable String title){
     //   blogService.getBlog(title,user.getId());
        return ResponseEntity.status(200).body(blogService.getBlog(title));
    }


}
