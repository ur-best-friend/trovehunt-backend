package com.example.system.entity;

import com.example.system.exceptions.badrequest.WrongUserException;
import com.example.system.jsonviews.TreasureFindingView;
import com.example.system.jsonviews.TreasureView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class TreasureFinding {
    public TreasureFinding(UserImage image, User user, Treasure foundTreasure){
        if(image.getAuthor().getId() != user.getId()) throw new WrongUserException(user.getUsername()+" didn't upload image "+image.getId());
        this.userImage = image;
        this.user = user;
        this.treasure = foundTreasure;
    }

    @Id
    @Getter
    @GeneratedValue
    private int id;

    @CreationTimestamp
    @Column(updatable = false)
    private Date date;
    public Long getDate(){ return date.getTime(); }

    @Getter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private User user;

    @Getter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private UserImage userImage;

    @Getter
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "userFindings"})
    @JoinColumn(name = "treasure_id", nullable = false)
    @JsonView({TreasureFindingView.CompleteView.class, TreasureView.HiddenView.class})
    private Treasure treasure;

    @Getter
    @JsonProperty("isVerified")
    @Column(columnDefinition = "boolean default false")
    private boolean verified = false;

    @Getter
    @JsonProperty("isDeclined")
    @Column(columnDefinition = "boolean default false")
    private boolean declined = false;

    public void verify(){ if(!this.declined) this.verified = true; }
    public void decline(){ if(!this.verified) this.declined = true; }
}
