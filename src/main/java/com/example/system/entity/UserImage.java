package com.example.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class UserImage {

    public UserImage(String url, User author){
        this.url = url;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="img_seq")
    @SequenceGenerator(name="img_seq", sequenceName="SEQ_IMG", allocationSize=10)
    private int id;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created;

    private String url;

    @JsonIgnore
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private User author;
}
