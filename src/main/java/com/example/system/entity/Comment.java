package com.example.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment implements SocialLikeEntity {
    public Comment(String text, User author){
        this.text = text;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="comment_seq")
    @SequenceGenerator(name="comment_seq", sequenceName="SEQ_COMM", allocationSize=10)
    private int id;

    private String text;

    @CreationTimestamp
    private Date created;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private User author;

    @Getter
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Integer> likedIds = new HashSet<>();
}
