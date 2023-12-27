package com.example.blogsystemsecurity.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(200) not null")
    @NotEmpty(message = "title must be not empty")
    private String title;


    @NotEmpty(message = "body must be not empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String body;


    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    @JsonIgnore
    private User user;

}
