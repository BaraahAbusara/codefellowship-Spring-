package com.example.codefellowship.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
public class Post {

    @Setter(value= AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id ;

    private String body;
    private String createdAt;

    public Post(String body, String createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public Post() {
    }

    @ManyToOne
    ApplicationUser author ;

}
