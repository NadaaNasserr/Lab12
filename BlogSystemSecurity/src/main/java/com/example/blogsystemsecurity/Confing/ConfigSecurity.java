package com.example.blogsystemsecurity.Confing;


import com.example.blogsystemsecurity.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/my-user","/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/auth/user/**" , "/api/v1/auth/delete/**" ,
                        "/api/v1/blog/getAllBlog", "/api/v1/auth/all-users","/api/v1/blog/getBlogByTitle/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/delete/**" , "/api/v1/blog/get-blogs" ,
                        "/api/v1/blog/**"  , "/api/v1/blog/update/**" , "/api/v1/blog/delete/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}

