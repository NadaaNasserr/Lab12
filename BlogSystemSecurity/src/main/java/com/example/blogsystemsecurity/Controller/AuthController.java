package com.example.blogsystemsecurity.Controller;


import com.example.blogsystemsecurity.Model.User;
import com.example.blogsystemsecurity.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body("user register");

    }

    @PostMapping("/login")
    public ResponseEntity login(){

        return ResponseEntity.status(200).body("login");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){

        return ResponseEntity.status(200).body("logout");
    }


    // Admin
    @GetMapping("/all-users")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(authService.getAllUser());
    }



    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(authService.getUser(id));
    }


    @GetMapping("/my-user")
    public ResponseEntity getMyUser(@AuthenticationPrincipal User auth){
        return ResponseEntity.status(200).body(authService.getUser(auth.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody @Valid User newUser, @AuthenticationPrincipal User auth){
        authService.updateUser(newUser , auth.getId());
        return ResponseEntity.status(200).body("User Updated");
    }

    // Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        authService.deleteUser(id);
        return ResponseEntity.status(200).body("User Deleted");
    }

}