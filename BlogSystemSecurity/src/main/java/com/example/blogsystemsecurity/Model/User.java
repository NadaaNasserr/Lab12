package com.example.blogsystemsecurity.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(200) not null unique")
    @NotEmpty(message = "username must be not empty")
    private String username;



//    @Column(columnDefinition = "varchar(200) not null")
//    @NotEmpty(message = "password must be not empty")
    @Pattern(regexp =  "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$" , message = "password must contain at least one number and one uppercase, lowercase letter and special character, and at least 8 or more characters")
private String password;



    public String role;




    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    public Set<Blog> blogs;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
